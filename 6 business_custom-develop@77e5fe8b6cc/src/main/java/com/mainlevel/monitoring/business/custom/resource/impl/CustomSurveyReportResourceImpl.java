/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.impl;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.business.custom.resource.CustomSurveyReportResource;
import com.mainlevel.monitoring.business.custom.resource.dto.CSVExportDelimiter;
import com.mainlevel.monitoring.business.custom.resource.impl.csv.CSVSurveyInstance;
import com.mainlevel.monitoring.business.custom.resource.impl.csv.CSVSurveyQuestion;
import com.mainlevel.monitoring.survey.api.dto.QuestionType;
import com.mainlevel.monitoring.survey.api.dto.SurveySummaryResultDTO;
import com.mainlevel.monitoring.survey.api.dto.SurveySummaryResultListDTO;
import com.mainlevel.monitoring.survey.api.exception.CsvGenerationException;
import com.mainlevel.monitoring.survey.api.resource.SurveyReportResource;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link CustomSurveyReportResource}.
 */
@Slf4j
@RestController
@Secured(ROLE_USER)
public class CustomSurveyReportResourceImpl implements CustomSurveyReportResource {

    private static final String SELECTIONS = " Selections";

    private static final String DELIMITER = "DELIMITER";

    @Autowired
    private SurveyReportResource reportResource;

    @Override
    public void loadSurveyReportAsCSV(@PathVariable("identifier") final Long surveyGid, @PathVariable("reportName") final String reportName,
        @RequestParam final Map<String, String> parameters, HttpServletResponse response) {

        ResponseEntity<SurveySummaryResultListDTO> surveyResponse = reportResource.loadSurveyReportAsCSV(surveyGid, reportName, parameters);
        List<SurveySummaryResultDTO> results = surveyResponse.getBody().getResultList();
        SurveySummaryResultDTO defaultValues = SurveySummaryResultDTO.builder().surveyName("undefined").build();
        SurveySummaryResultDTO firstResult = results.stream().findFirst().orElse(defaultValues);

        try {
            Set<String> questionNames = results.stream()
                .sorted((SurveySummaryResultDTO r1, SurveySummaryResultDTO r2) -> r1.getQuestionIndex().compareTo(r2.getQuestionIndex()))
                .map(SurveySummaryResultDTO::getQuestionTitle).filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.toCollection(LinkedHashSet::new));

            Map<String, Set<String>> multipleChoiceQuestions =
                results.stream().filter(q -> q.getQuestionType() == QuestionType.CHECKBOX || q.getQuestionType() == QuestionType.CHECKBOX)
                    .filter(q -> q.getAnswer() != null && !q.getAnswer().trim().isEmpty())
                    .collect(Collectors.groupingBy(SurveySummaryResultDTO::getQuestionTitle,
                        Collectors.mapping(SurveySummaryResultDTO::getAnswer, Collectors.toCollection(LinkedHashSet::new))));

            List<String> headerNames = prepareHeaders(firstResult, questionNames, multipleChoiceQuestions);

            response.setContentType("text/csv;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"export.csv\"");
            CSVExportDelimiter delimiter = CSVExportDelimiter.getValue(parameters.get(DELIMITER));
            final CSVFormat csvExportFormat = CSVFormat.RFC4180.withDelimiter(delimiter.getDelimiter());

            final OutputStreamWriter oswt = new OutputStreamWriter(response.getOutputStream());

            StringBuilder headerLine = new StringBuilder();

            for (Iterator<String> iterator = headerNames.iterator(); iterator.hasNext();) {
                String header = iterator.next();
                headerLine.append(header != null ? header : "");
                if (iterator.hasNext()) {
                    headerLine.append(delimiter.getDelimiter());
                }
            }
            headerLine.append("\r\n");
            oswt.write(headerLine.toString());

            final CSVPrinter csvPrinter = new CSVPrinter(oswt, csvExportFormat);

            Map<CSVSurveyInstance, Map<String, CSVSurveyQuestion>> csvRows = groupResults(results);

            for (Entry<CSVSurveyInstance, Map<String, CSVSurveyQuestion>> entry : csvRows.entrySet()) {

                CSVSurveyInstance project = entry.getKey();

                final List<String> dataRecord = new ArrayList<>();
                dataRecord.add(project.getSurveyName());
                dataRecord.add(project.getUnit());
                dataRecord.add(project.getOwner());
                dataRecord.add(project.getStatus());
                dataRecord.add(String.valueOf(project.getInstanceId()));

                for (String questionName : questionNames) {

                    CSVSurveyQuestion question = entry.getValue().get(questionName);
                    Set<String> options = multipleChoiceQuestions.get(questionName);

                    if (question != null) {
                        if (options == null) {
                            dataRecord.add(question.getSingleAnswer());
                        } else {
                            dataRecord.add(question.getAnswers().size() + SELECTIONS);
                            options.forEach(o -> dataRecord.add(question.getAnswers().get(o)));
                        }
                    } else {
                        if (options == null) {
                            dataRecord.add(null);
                        } else {
                            dataRecord.add(0 + SELECTIONS);
                            options.forEach(o -> dataRecord.add(null));
                        }
                    }
                }

                csvPrinter.printRecord(dataRecord);
            }

            csvPrinter.close();

        } catch (Exception e) {
            throw new CsvGenerationException("Unable to generate CSV for report: " + reportName, e);
        }
    }

    /**
     * Group the results by survey instance and question.
     *
     * @param resultList the list of all entries
     * @return the results grouped by instance and question
     */
    private Map<CSVSurveyInstance, Map<String, CSVSurveyQuestion>> groupResults(List<SurveySummaryResultDTO> resultList) {

        Map<CSVSurveyInstance, Map<String, CSVSurveyQuestion>> projects = new HashMap<>();

        resultList.forEach(result -> {

            String status = result.getStatus() != null ? result.getStatus().name() : null;

            CSVSurveyInstance project = CSVSurveyInstance.builder().surveyName(result.getSurveyName()).instanceId(result.getReportingPeriodId())
                .owner(result.getCreator()).unit(result.getUnitName()).status(status).build();

            Map<String, CSVSurveyQuestion> questions = projects.get(project);

            if (questions == null) {
                questions = new HashMap<>();
                projects.put(project, questions);
            }

            String key = result.getQuestionTitle();
            String value = trim(result.getAnswer());

            if (result.getQuestionType() == QuestionType.DATE) {
                value = this.prepareDate(value);
            }

            CSVSurveyQuestion question = questions.get(key);

            //this parameter could be a good starting point to optimise the CSV in terms of omitting empty columns
            //we need to find a solution to have only the path District->Zone->School
            boolean isSingleValue =
                result.getQuestionType() != QuestionType.CHECKBOX && result.getQuestionType() != QuestionType.MATRIX_MULTIPLE_CHOICE;

            if (question == null) {
                question = CSVSurveyQuestion.builder().singleValue(isSingleValue).name(key).build();
                questions.put(key, question);
            }

            if (isSingleValue) {
                question.setSingleAnswer(value);
            } else {
                if (value != null && !value.trim().isEmpty()) {
                    question.getAnswers().put(value, value);
                }
            }
        });

        return projects;
    }

    /**
     * Prepare the given text date to a common format.
     *
     * @param originalValue the original date
     * @return the date in the new format
     */
    private String prepareDate(String originalValue) {
        if (originalValue == null || originalValue.isEmpty()) {
            return originalValue;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[yyyy-MM-dd'T'HH:mm:ss.SSS'Z'][M/d/yyyy][yyyy-MM-dd][01/MM/yyyy]");

        try {
            LocalDate date = LocalDate.parse(originalValue, formatter);

            return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }catch (Exception e) {
            log.warn("could not parse date question for CSV export: {}", originalValue);
            return originalValue;
        }
        
    }

    /**
     * Prepare the CSV header names.
     *
     * @param firstResult the first result within the list
     * @param questionNames name of the questions
     * @param multipleChoiceQuestions multiple choice questions including options
     * @return the list of header names
     */
    private List<String> prepareHeaders(SurveySummaryResultDTO firstResult, Set<String> questionNames,
        Map<String, Set<String>> multipleChoiceQuestions) {

        List<String> names = new ArrayList<>();
        names.add("Survey");
        names.add("Project");
        names.add("Owner");
        names.add("Status");
        names.add("ReportingID");
        for (String questionName : questionNames) {

            names.add(questionName);

            Set<String> options = multipleChoiceQuestions.get(questionName);
            if (options != null) {
                for (String option : options) {
                    if (option != null && !option.trim().isEmpty()) {
                        names.add("");
                    }
                }
            }
        }

        return names;
    }

    /**
     * Trim the specified string and removes all line wrapping characters.
     *
     * @param value the value to trim
     * @return the trimmed result
     */
    private String trim(String value) {
        if (value == null) {
            return null;
        }

        String result = value.trim().replace("\r\n", " ").replace("\n", " ");

        return result;
    }

}
