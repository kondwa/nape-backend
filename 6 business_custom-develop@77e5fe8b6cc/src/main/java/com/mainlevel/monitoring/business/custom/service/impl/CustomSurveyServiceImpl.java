/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mainlevel.monitoring.admin.api.dto.template.PageDTO;
import com.mainlevel.monitoring.admin.api.dto.template.SurveyTemplateDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.v1.CustomAnswerDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.v1.CustomQuestionDTO;
import com.mainlevel.monitoring.business.custom.service.CustomSurveyService;
import com.mainlevel.monitoring.common.service.CollectionConversionService;
import com.mainlevel.monitoring.survey.api.dto.ClientDTO;
import com.mainlevel.monitoring.survey.api.dto.ClientType;
import com.mainlevel.monitoring.survey.api.dto.OrganizationalUnitDTO;
import com.mainlevel.monitoring.survey.api.dto.QuestionType;
import com.mainlevel.monitoring.survey.api.dto.SurveyLinkDTO;
import com.mainlevel.monitoring.survey.api.dto.participantSession.ParticipantSessionAnswerDTO;
import com.mainlevel.monitoring.survey.api.dto.participantSession.ParticipantSessionQuestionAnswerDTO;
import com.mainlevel.monitoring.survey.api.dto.participantSession.ParticipantSessionQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ParticipantSessionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyListDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyStatsListDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyStatus;
import com.mainlevel.monitoring.survey.api.dto.survey.answer.SurveyAnswerType;
import com.mainlevel.monitoring.survey.api.dto.survey.question.AnswerOptionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyMatrixQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyQuestionGroupDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveySelectionQuestionDTO;
import com.mainlevel.monitoring.survey.api.resource.OrganizationalUnitResource;
import com.mainlevel.monitoring.survey.api.resource.ParticipantSessionResource;
import com.mainlevel.monitoring.survey.api.resource.ReportingPeriodResource;
import com.mainlevel.monitoring.survey.api.resource.SurveyResource;
import com.mainlevel.monitoring.survey.api.resource.SurveyStatsResource;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link CustomSurveyService}.
 */
@Slf4j
@Service
public class CustomSurveyServiceImpl implements CustomSurveyService {

    @Autowired
    private ReportingPeriodResource reportingPeriodResource;

    @Autowired
    private OrganizationalUnitResource organizationalUnitResource;

    @Autowired
    private ParticipantSessionResource participantSessionResource;

    @Autowired
    private SurveyResource surveyResource;

    @Autowired
    private SurveyStatsResource surveyStatsResource;

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Override
    @Deprecated
    public SurveyDTO loadSurvey(String templateId, long templateVersion) {

        ResponseEntity<SurveyListDTO> surveyResponse = surveyResource.getSurveysForTemplate(templateId, templateVersion);

        if (surveyResponse.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Error loading survey for template '" + templateId + "' in version '" + templateVersion
                + "'. HTTP Status Code: " + surveyResponse.getStatusCode());
        }

        if (surveyResponse.getBody() == null) {
            throw new RuntimeException("Error loading survey for template '" + templateId + "' in version '" + templateVersion + "'.");
        }

        for (SurveyDTO survey : surveyResponse.getBody().getSurveys()) {
            return this.loadSurvey(survey.getGraphId(), true);
        }

        return null;
    }

    @Override
    public SurveyDTO loadSurvey(Long surveyGid, Boolean includingStructure) {

        ResponseEntity<SurveyDTO> surveyResponse = surveyResource.getSurvey(surveyGid, includingStructure);

        if (surveyResponse.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Error loading survey for graph ID '" + surveyGid + "'. HTTP Status Code: " + surveyResponse.getStatusCode());
        }

        if (surveyResponse.getBody() == null) {
            throw new RuntimeException("Error loading survey for graph ID '" + surveyGid + "'.");
        }

        return surveyResponse.getBody();
    }

    @Override
    public SurveyStatsListDTO loadSurveyStats(String unitKey) {
        ResponseEntity<SurveyStatsListDTO> surveyStatsResponse = surveyStatsResource.getSurveyStats(unitKey);

        if (surveyStatsResponse.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException(
                "Error loading survey stats for unit '" + unitKey + "'. HTTP Status Code: " + surveyStatsResponse.getStatusCode());
        }

        if (surveyStatsResponse.getBody() == null) {
            throw new RuntimeException("Error loading survey stats for unit '" + unitKey + "'.");
        }

        return surveyStatsResponse.getBody();
    }

    @Override
    public SurveyDTO createSurveyForTemplate(SurveyTemplateDTO template) {

        List<SurveyQuestionGroupDTO> groups = new ArrayList<>();

        int questionIndex = 0;

        Map<String, SurveyQuestionDTO> questionMap = new HashMap<>();

        for (int pageIndex = 0; pageIndex < template.getSurveyTemplate().getPages().size(); pageIndex++) {

            PageDTO page = template.getSurveyTemplate().getPages().get(pageIndex);

            List<SurveyQuestionDTO> questions = collectionConversionService.convert(page.getQuestions(), SurveyQuestionDTO.class);
            for (SurveyQuestionDTO question : questions) {
                question.setIndex(questionIndex++);
                questionMap.put(question.getName(), question);
            }

            SurveyQuestionGroupDTO group = SurveyQuestionGroupDTO.builder().index(pageIndex).name(page.getName()).description(page.getDescription())
                .questions(questions).build();

            groups.add(group);
        }

        correctIndexes(questionMap);

        SurveyDTO survey =
            SurveyDTO.builder().surveyName(template.getSurveyName()).templateRefId(template.getIdentifier()).templateVersion(template.getVersion())
                .templateTitle(template.getSurveyName()).status(SurveyStatus.ACTIVE).questionGroups(groups).build();

        return survey;
    }

    /**
     * Set correct indexes for triggers.
     *
     * @param questionMap map of all questions per
     */
    private void correctIndexes(Map<String, SurveyQuestionDTO> questionMap) {

        questionMap.values().forEach(question -> {
            question.getTriggers().forEach(trigger -> {
                SurveyQuestionDTO targetQuestion = questionMap.get(trigger.getTargetQuestionName());
                Integer questionIndex = targetQuestion != null ? targetQuestion.getIndex() : null;
                trigger.setTargetQuestionIndex(questionIndex);

                if (question instanceof SurveySelectionQuestionDTO) {
                    Optional<AnswerOptionDTO> option = ((SurveySelectionQuestionDTO) question).getOptions().stream()
                        .filter(o -> o.getValue().equals(trigger.getOptionValue())).findFirst();
                    if (option.isPresent()) {
                        trigger.setOptionIndex(option.get().getIndex());
                    }
                }
            });
        });
    }

    @Override
    public SurveyDTO saveSurvey(SurveyDTO survey) {

        ResponseEntity<SurveyDTO> surveyResponse = surveyResource.createSurvey(survey);

        if (surveyResponse.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Error saving survey '" + survey.getSurveyName() + "'. HTTP Status Code: " + surveyResponse.getStatusCode());
        }

        if (surveyResponse.getBody() == null) {
            throw new RuntimeException("Error saving survey '" + survey.getSurveyName() + "'.");
        }

        return null;
    }

    @Override
    public ReportingPeriodDTO createReportingPeriod(ReportingPeriodDTO period) {

        ResponseEntity<ReportingPeriodDTO> response = reportingPeriodResource.createReportingPeriod(period);

        ReportingPeriodDTO result = response.getBody();
        if (result == null) {
            throw new RuntimeException("Error creating reporting period for survey '" + period.getSurvey().getSurveyName() + "'.");
        }

        result.setSurvey(period.getSurvey());

        return result;
    }

    @Override
    public ReportingPeriodDTO updateReportingPeriod(ReportingPeriodDTO period) {

        ResponseEntity<ReportingPeriodDTO> response = reportingPeriodResource.updateReportingPeriod(period.getGraphId(), period);

        ReportingPeriodDTO result = response.getBody();
        if (result == null) {
            throw new RuntimeException("Error creating reporting period for survey '" + period.getSurvey().getSurveyName() + "'.");
        }

        result.setSurvey(period.getSurvey());

        return result;
    }

    @Override
    public ReportingPeriodDTO loadReportingPeriod(Long reportingPeriodGid) {

        ResponseEntity<ReportingPeriodDTO> response = reportingPeriodResource.getReportingPeriod(reportingPeriodGid);

        ReportingPeriodDTO result = response.getBody();
        if (result == null) {
            throw new RuntimeException("Error loading reporting period for id '" + reportingPeriodGid + "'.");
        }

        return result;
    }

    @Override
    public ParticipantSessionDTO saveParticipantSession(ParticipantSessionDTO session, String ipAddress) {

        ResponseEntity<ParticipantSessionDTO> sessionResponse = participantSessionResource.saveSession(session);

        if (sessionResponse.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException(
                "Error creating participant session for IP '" + ipAddress + "'. HTTP Status Code: " + sessionResponse.getStatusCode());
        }

        if (sessionResponse.getBody() == null) {
            throw new RuntimeException("Error creating participant session for IP '" + ipAddress + "'.");
        }

        return sessionResponse.getBody();
    }

    @Override
    public SurveyLinkDTO loadSurveyLink(Long graphId) {

        ResponseEntity<SurveyLinkDTO> response = surveyResource.getSurveyLink(graphId);

        if (response == null || response.getBody() == null) {
            throw new RuntimeException("Error loading link for survey with id " + graphId + ".");
        }

        return response.getBody();
    }

    @Override
    public ParticipantSessionDTO createParticipantSession(ReportingPeriodDTO period, List<CustomQuestionDTO> customQuestions, String ipAddress) {

        List<ParticipantSessionAnswerDTO> answers = new ArrayList<>();

        Map<String, ParticipantSessionQuestionDTO> questions = getQuestions(period.getSurvey());

        customQuestions.forEach(customQuestion -> {

            ParticipantSessionQuestionDTO question = questions.get(customQuestion.getName());

            if (question == null) {
                log.warn("Question with name {} not found in survey {}.", customQuestion.getName(), period.getSurvey().getSurveyName());
            } else {
                customQuestion.getAnswers().forEach(customAnswer -> {
                    // TODO workaround to omit empty entries
                    if (StringUtils.isNotEmpty(customAnswer.getValue())) {

                        AnswerOptionDTO answerOption = getOption(question, customAnswer);
                        SurveyAnswerType answerType = answerOption != null ? SurveyAnswerType.OPTION : SurveyAnswerType.VALUE;

                        Integer order = this.convertLabelToOrder(customAnswer.getLabel());
                        ParticipantSessionQuestionAnswerDTO questionAnswer =
                            ParticipantSessionQuestionAnswerDTO.builder().question(question).order(order).title(customAnswer.getLabel()).build();

                        ParticipantSessionAnswerDTO answer = ParticipantSessionAnswerDTO.builder().value(customAnswer.getValue()).option(answerOption)
                            .question(questionAnswer).type(answerType).build();
                        answers.add(answer);
                    }
                });
            }
        });

        ClientDTO client = ClientDTO.builder().address(ipAddress).type(ClientType.MOBILE).build();
        ParticipantSessionDTO session = ParticipantSessionDTO.builder().reportingPeriod(period).answers(answers).client(client).build();

        return this.saveParticipantSession(session, ipAddress);
    }

    @Override
    public OrganizationalUnitDTO findUnitByForeignId(String foreignId) {

        ResponseEntity<OrganizationalUnitDTO> unitResponse = organizationalUnitResource.getUnit(null, foreignId);

        if (unitResponse.getBody() == null) {
            throw new RuntimeException("Error loading organizational unit '" + foreignId + "'.");
        }

        return unitResponse.getBody();
    }

    /**
     * Convert the custom label to a survey priority.
     *
     * @param label the label to parse
     * @return the order
     */
    private Integer convertLabelToOrder(String label) {
        if (label == null || label.isEmpty()) {
            return null;
        }

        if (label.contains("Zeilen-")) {
            String order = label.replace("Zeilen-", "");

            try {
                return Integer.parseInt(order);
            } catch (NumberFormatException e) {
                log.warn("Error parsing answer label order [{}].", label, e);
            }
        }

        return null;
    }

    /**
     * Retrieve the answer option for the given answer.
     *
     * @param question the question
     * @param customAnswer the option name
     * @return the option or null
     */
    private AnswerOptionDTO getOption(ParticipantSessionQuestionDTO question, CustomAnswerDTO answer) {
        if (question.getType().isSelectable()) {

            if (question.getOptions() != null) {
                for (AnswerOptionDTO option : question.getOptions()) {
                    if (option.getName().equalsIgnoreCase(answer.getValue())) {
                        return option;
                    }
                    if (option.getName().equalsIgnoreCase(answer.getName())) {
                        return option;
                    }
                    if (option.getValue() != null && option.getValue().equals(answer.getValue())) {
                        return option;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Retrieve the map of questions by name.
     *
     * @param survey survey containing the questions
     * @return map of questions by question name
     */
    private Map<String, ParticipantSessionQuestionDTO> getQuestions(SurveyDTO survey) {

        Map<String, ParticipantSessionQuestionDTO> questions = new HashMap<>();

        survey.getQuestionGroups().forEach(group -> {
            group.getQuestions().forEach(question -> {

                ParticipantSessionQuestionDTO psQuestion = ParticipantSessionQuestionDTO.builder().title(question.getTitle())
                    .graphId(question.getGraphId()).index(question.getIndex()).name(question.getName()).type(question.getType())
                    .description(question.getDescription()).mandatory(question.getMandatory()).visible(question.getVisible()).build();

                if (question.getType() == QuestionType.CHECKBOX || question.getType() == QuestionType.DROPDOWN
                    || question.getType() == QuestionType.RADIO) {
                    psQuestion.setOptions(((SurveySelectionQuestionDTO) question).getOptions());
                } else if (question.getType() == QuestionType.MATRIX_SINGLE_CHOICE || question.getType() == QuestionType.MATRIX_MULTIPLE_CHOICE) {
                    psQuestion.setOptions(((SurveyMatrixQuestionDTO) question).getOptions());
                }

                questions.put(question.getName(), psQuestion);
            });
        });

        return questions;
    }

    @Override
    public boolean findReportingPeriods(String foreignSurveyId) {
        ResponseEntity<List<ReportingPeriodDTO>> response = reportingPeriodResource.getReportingPeriods(foreignSurveyId);
        
        List<ReportingPeriodDTO> reportingPeriods = response.getBody();
        
        return reportingPeriods != null && !reportingPeriods.isEmpty();
    }

}
