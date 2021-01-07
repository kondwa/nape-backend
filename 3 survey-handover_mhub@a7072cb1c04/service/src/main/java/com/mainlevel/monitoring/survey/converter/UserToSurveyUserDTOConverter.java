/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.OrganizationalUnitDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyDTO;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserDTO;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserParticipationDTO;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserReportingPeriodParticipationDTO;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserSurveyParticipationDTO;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserUnitParticipationDTO;
import com.mainlevel.monitoring.survey.database.node.User;

/**
 * Converter converting {@link SurveyUserDTO} to {@link User}.
 */
@Component
public class UserToSurveyUserDTOConverter extends AbstractApplicationAwareConverter<User, SurveyUserDTO> {

    @Override
    public SurveyUserDTO convert(User source) {

        List<SurveyUserParticipationDTO> participations = new ArrayList<>();

        if (source.getReportingPeriodParticipations() != null) {
            source.getReportingPeriodParticipations().forEach(participation -> {

                ReportingPeriodDTO period = super.getConversionService().convert(participation.getPeriod(), ReportingPeriodDTO.class);

                SurveyUserReportingPeriodParticipationDTO participationDTO = new SurveyUserReportingPeriodParticipationDTO();
                participationDTO.setGraphId(participation.getGid());
                participationDTO.setRole(participation.getRole());
                participationDTO.setPeriod(period);
                participations.add(participationDTO);
            });
        }

        if (source.getSurveyParticipations() != null) {
            source.getSurveyParticipations().forEach(participation -> {

                SurveyDTO survey = super.getConversionService().convert(participation.getSurvey(), SurveyDTO.class);

                SurveyUserSurveyParticipationDTO participationDTO = new SurveyUserSurveyParticipationDTO();
                participationDTO.setGraphId(participation.getGid());
                participationDTO.setRole(participation.getRole());
                participationDTO.setSurvey(survey);
                participations.add(participationDTO);
            });
        }

        if (source.getUnitParticipations() != null) {
            source.getUnitParticipations().forEach(participation -> {

                OrganizationalUnitDTO unit = super.getConversionService().convert(participation.getUnit(), OrganizationalUnitDTO.class);

                SurveyUserUnitParticipationDTO participationDTO = new SurveyUserUnitParticipationDTO();
                participationDTO.setGraphId(participation.getGid());
                participationDTO.setRole(participation.getRole());
                participationDTO.setUnit(unit);
                participations.add(participationDTO);
            });
        }

        SurveyUserDTO result = SurveyUserDTO.builder().graphId(source.getGid()).username(source.getUsername()).name(source.getName())
            .userType(source.getType()).participations(participations).build();

        return result;
    }

}
