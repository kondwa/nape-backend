/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.dto.user;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding participations of a user to different entities.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({@JsonSubTypes.Type(value = SurveyUserReportingPeriodParticipationDTO.class, name = "PERIOD"),
    @JsonSubTypes.Type(value = SurveyUserSurveyParticipationDTO.class, name = "SURVEY"),
    @JsonSubTypes.Type(value = SurveyUserUnitParticipationDTO.class, name = "UNIT")})
public abstract class SurveyUserParticipationDTO {

    private Long graphId;

    private SurveyUserParticipationRole role;
}
