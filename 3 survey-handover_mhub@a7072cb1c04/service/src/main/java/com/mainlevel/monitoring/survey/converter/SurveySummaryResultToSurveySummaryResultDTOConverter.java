/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.SurveySummaryResultDTO;
import com.mainlevel.monitoring.survey.database.queryresult.SurveySummaryResult;

/**
 * Converter converting {@link SurveySummaryResult} to {@link SurveySummaryResultDTO}.
 */
@Component
public class SurveySummaryResultToSurveySummaryResultDTOConverter
    extends AbstractApplicationAwareConverter<SurveySummaryResult, SurveySummaryResultDTO> {

    @Override
    public SurveySummaryResultDTO convert(SurveySummaryResult source) {

        String answer = source.getAnswer() != null ? String.valueOf(source.getAnswer()) : null;

        SurveySummaryResultDTO result =
            SurveySummaryResultDTO.builder().answer(answer).creator(source.getCreator()).questionGroup(source.getQuestionGroup())
                .questionGroupId(source.getQuestionGroupId()).questionId(source.getQuestionId()).questionIndex(source.getQuestionIndex())
                .questionTitle(source.getQuestionTitle()).questionType(source.getQuestionType()).reportingPeriodId(source.getReportingPeriodId())
                .status(source.getStatus()).surveyName(source.getSurveyName()).unitName(source.getUnitName()).build();

        return result;
    }

}
