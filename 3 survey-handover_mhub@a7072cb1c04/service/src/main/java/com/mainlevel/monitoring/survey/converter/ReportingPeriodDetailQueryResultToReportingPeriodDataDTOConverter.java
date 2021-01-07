package com.mainlevel.monitoring.survey.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodDataDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyQuestionAnswerDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyQuestionGroupDTO;
import com.mainlevel.monitoring.survey.database.node.Answer;
import com.mainlevel.monitoring.survey.database.node.ReportingPeriod;
import com.mainlevel.monitoring.survey.database.node.Survey;
import com.mainlevel.monitoring.survey.database.queryresult.ReportingPeriodDetailQueryResult;

/**
 * Converter for converting ReportingPeriodOverview to SurveyOverviewDTO.
 */
@Component
public class ReportingPeriodDetailQueryResultToReportingPeriodDataDTOConverter
    extends AbstractApplicationAwareConverter<ReportingPeriodDetailQueryResult, ReportingPeriodDataDTO> {

    @Override
    public ReportingPeriodDataDTO convert(ReportingPeriodDetailQueryResult source) {

        ReportingPeriod period = source.getReportingPeriod();
        Survey survey = period.getSurvey();

        String unitName = period.getUnit() != null ? period.getUnit().getName() : null;
        String author = period.getUser() != null ? period.getUser().getName() : null;

        List<SurveyQuestionGroupDTO> groups = super.getCollectionConversionService().convert(survey.getGroups(), SurveyQuestionGroupDTO.class);

        ReportingPeriodDataDTO result = new ReportingPeriodDataDTO();
        result.setReportPeriodGid(period.getGid());
        result.setSurveyName(survey.getName());
        result.setSurveyType(survey.getType());
        result.setSurveyVisibilityType(survey.getVisibility());
        result.setIntroductionText(survey.getIntroductionText());
        result.setClosingText(survey.getClosingText());
        result.setStatus(period.getStatus());

        result.setTemplateId(survey.getTemplateRefId());
        result.setTemplateTitle(survey.getTemplateTitle());
        result.setTemplateVersion(survey.getTemplateVersion());

        result.setStart(period.getStart());
        result.setEnd(period.getEnd());

        result.setAuthor(author);
        result.setCreationTime(period.getCreated());
        result.setUnitName(unitName);
        result.setQuestionGroups(groups);

        // Missing Participant Session
        result.setLastEditUser(null);
        result.setLastEditTime(null);

        if (source.getAnswers() != null) {

            result.getQuestionGroups().forEach(group -> {
                group.getQuestions().forEach(question -> {

                    List<Answer> answers = findAnswersForQuestion(source.getAnswers(), question.getGraphId());

                    List<SurveyQuestionAnswerDTO> questionAnswers =
                        super.getCollectionConversionService().convert(answers, SurveyQuestionAnswerDTO.class);

                    question.setAnswers(questionAnswers);

                });
            });

        }

        return result;
    }

    private List<Answer> findAnswersForQuestion(List<Answer> allAnswers, Long questionGid) {

        List<Answer> answers = new ArrayList<>();

        for (Answer answer : allAnswers) {
            if (answer.getQuestion() != null && answer.getQuestion().getQuestion().getGid().equals(questionGid)) {
                answers.add(answer);
            }
        }

        return answers;
    }

}
