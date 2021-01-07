/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.evaluation.EvaluationResultEntryDTO;
import com.mainlevel.monitoring.survey.database.queryresult.evaluation.EvaluationResult;

/**
 * Converter for converting EvaluationResult to EvaluationResultEntryDTO.
 */
@Component
public class EvaluationResultToEvaluationResultEntryDTOConverter
    extends AbstractApplicationAwareConverter<EvaluationResult, EvaluationResultEntryDTO> {

    @Override
    public EvaluationResultEntryDTO convert(EvaluationResult source) {

        Double yAxisValue = null;
        if (source.getYAxisValue() != null) {
            yAxisValue = source.getYAxisValue().doubleValue();
        }

        EvaluationResultEntryDTO target =
            EvaluationResultEntryDTO.builder().name(source.getRecordName()).xAxisValue(source.getXAxisValue()).yAxisValue(yAxisValue).build();

        return target;
    }

}
