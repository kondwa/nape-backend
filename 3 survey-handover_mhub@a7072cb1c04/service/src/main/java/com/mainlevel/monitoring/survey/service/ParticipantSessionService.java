/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */
package com.mainlevel.monitoring.survey.service;

import java.util.List;

import com.mainlevel.monitoring.survey.database.node.Answer;
import com.mainlevel.monitoring.survey.database.node.ParticipantSession;
import com.mainlevel.monitoring.survey.database.node.ReportingPeriod;

/**
 * Service for handling participant sessions and related answers.
 */
public interface ParticipantSessionService {

    /**
     * Retrieve the latest participant session for a reporting period, or null if none was found.
     *
     * @param reportingPeriodGid the graph id of the reporting period
     * @return the found participant session or null
     */
    ParticipantSession getLatestParticipantSession(Long reportingPeriodGid);

    /**
     * Creates a new participant session object and connects it to the previous participant session if existent. This operation will not save the
     * participant session in database.
     *
     * @param reportingPeriod the reporting period to create this participant session for
     * @param answers list of answers
     * @return the newly created participant session
     */
    ParticipantSession createParticipantSession(ReportingPeriod reportingPeriod, List<Answer> answers);

    /**
     * Saves the participant session to database, creates relations between the participant session and answers.
     * If a previous participant session exists, its relation to reporting period will be deleted.
     *
     * @param participantSession the participant session to save
     * @return the persistet participant session node
     */
    ParticipantSession saveParticipantSession(ParticipantSession participantSession);

}
