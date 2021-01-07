/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.dto.user;

import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserParticipationRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding a participation of a user.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class CustomUserParticipationDTO {

    private Long id;

    private SurveyUserParticipationRole role;

    private CustomUserParticipationType type;

    private Long targetId;
}
