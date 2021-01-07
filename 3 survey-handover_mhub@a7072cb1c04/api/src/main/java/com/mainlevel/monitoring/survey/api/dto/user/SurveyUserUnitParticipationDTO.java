/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.dto.user;

import com.mainlevel.monitoring.survey.api.dto.OrganizationalUnitDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Participation to an organizational unit.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class SurveyUserUnitParticipationDTO extends SurveyUserParticipationDTO {

    private OrganizationalUnitDTO unit;

}
