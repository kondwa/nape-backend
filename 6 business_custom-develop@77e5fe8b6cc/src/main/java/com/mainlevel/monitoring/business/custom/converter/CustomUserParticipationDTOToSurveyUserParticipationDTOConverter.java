/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.business.custom.resource.dto.user.CustomUserParticipationDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodDTO;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserParticipationDTO;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserReportingPeriodParticipationDTO;

/**
 * Converter converting {@link CustomUserParticipationDTO} to {@link SurveyUserParticipationDTO}.
 */
@Component
public class CustomUserParticipationDTOToSurveyUserParticipationDTOConverter
    extends AbstractApplicationAwareConverter<CustomUserParticipationDTO, SurveyUserParticipationDTO> {

    @Override
    public SurveyUserParticipationDTO convert(CustomUserParticipationDTO source) {

        SurveyUserParticipationDTO result;

        switch (source.getType()) {
            case SURVEY_INSTANCE: {
                SurveyUserReportingPeriodParticipationDTO participationDTO = new SurveyUserReportingPeriodParticipationDTO();
                participationDTO.setGraphId(source.getId());
                participationDTO.setRole(source.getRole());
                participationDTO.setPeriod(ReportingPeriodDTO.builder().graphId(source.getTargetId()).build());

                result = participationDTO;
                break;
            }

            default:
                throw new IllegalArgumentException("User participation of type '" + source.getType() + "' is not supported yet.");

        }

        return result;
    }

}
