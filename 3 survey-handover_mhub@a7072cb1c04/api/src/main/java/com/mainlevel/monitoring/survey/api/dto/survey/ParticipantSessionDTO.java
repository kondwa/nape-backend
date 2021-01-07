/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.dto.survey;

import java.util.Date;
import java.util.List;

import com.mainlevel.monitoring.survey.api.dto.ClientDTO;
import com.mainlevel.monitoring.survey.api.dto.participantSession.ParticipantSessionAnswerDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding information about a single participant session.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class ParticipantSessionDTO {

    private Long graphId;

    private Date time;

    private String changeByUser;

    private ReportingPeriodDTO reportingPeriod;

    private ClientDTO client;

    private List<ParticipantSessionAnswerDTO> answers;

}
