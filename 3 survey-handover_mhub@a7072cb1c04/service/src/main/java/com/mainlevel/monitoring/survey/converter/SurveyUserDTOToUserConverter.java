/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserDTO;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserReportingPeriodParticipationDTO;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserSurveyParticipationDTO;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserUnitParticipationDTO;
import com.mainlevel.monitoring.survey.database.node.OrganizationalUnit;
import com.mainlevel.monitoring.survey.database.node.ReportingPeriod;
import com.mainlevel.monitoring.survey.database.node.Survey;
import com.mainlevel.monitoring.survey.database.node.User;
import com.mainlevel.monitoring.survey.database.node.UserReportingPeriodParticipation;
import com.mainlevel.monitoring.survey.database.node.UserSurveyParticipation;
import com.mainlevel.monitoring.survey.database.node.UserUnitParticipation;

/**
 * Converter converting {@link SurveyUserDTO} to {@link User}.
 */
@Component
public class SurveyUserDTOToUserConverter extends AbstractApplicationAwareConverter<SurveyUserDTO, User> {

    @Override
    public User convert(SurveyUserDTO source) {

        List<UserUnitParticipation> unitParticipations = new ArrayList<>();
        List<UserSurveyParticipation> surveyParticipations = new ArrayList<>();
        List<UserReportingPeriodParticipation> periodParticipations = new ArrayList<>();

        User result = User.builder().gid(source.getGraphId()).username(source.getUsername()).name(source.getName()).type(source.getUserType())
            .unitParticipations(unitParticipations).surveyParticipations(surveyParticipations).reportingPeriodParticipations(periodParticipations)
            .build();

        if (source.getParticipations() != null) {
            source.getParticipations().forEach(participation -> {

                if (participation instanceof SurveyUserUnitParticipationDTO) {

                    OrganizationalUnit unit =
                        super.getConversionService().convert(((SurveyUserUnitParticipationDTO) participation).getUnit(), OrganizationalUnit.class);

                    UserUnitParticipation unitParticipation =
                        UserUnitParticipation.builder().gid(participation.getGraphId()).user(result).role(participation.getRole()).unit(unit).build();

                    unitParticipations.add(unitParticipation);

                } else if (participation instanceof SurveyUserSurveyParticipationDTO) {

                    Survey survey =
                        super.getConversionService().convert(((SurveyUserSurveyParticipationDTO) participation).getSurvey(), Survey.class);

                    UserSurveyParticipation surveyParticipation = UserSurveyParticipation.builder().gid(participation.getGraphId()).user(result)
                        .role(participation.getRole()).survey(survey).build();

                    surveyParticipations.add(surveyParticipation);

                } else if (participation instanceof SurveyUserReportingPeriodParticipationDTO) {

                    ReportingPeriod reportingPeriod = super.getConversionService()
                        .convert(((SurveyUserReportingPeriodParticipationDTO) participation).getPeriod(), ReportingPeriod.class);

                    UserReportingPeriodParticipation periodParticipation = UserReportingPeriodParticipation.builder().gid(participation.getGraphId())
                        .user(result).role(participation.getRole()).period(reportingPeriod).build();

                    periodParticipations.add(periodParticipation);
                }
            });
        }

        return result;
    }

}
