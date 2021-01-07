/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.service;

import java.util.List;

import com.mainlevel.monitoring.admin.api.dto.template.SurveyTemplateDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.v1.CustomQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.OrganizationalUnitDTO;
import com.mainlevel.monitoring.survey.api.dto.SurveyLinkDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ParticipantSessionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyStatsListDTO;

/**
 * Service for survey interaction.
 */
public interface CustomSurveyService {

    /**
     * Loads the survey with the given id from survey microservice.
     *
     * @param surveyGid the survey graph id
     * @param includingStructure include the survey structure
     * @return the loaded survey
     */
    SurveyDTO loadSurvey(Long surveyGid, Boolean includingStructure);

    /**
     * Loads the survey stats from survey microservice.
     *
     * @param unitKey id of the organizational unit
     * @return the loaded survey stats
     */
    SurveyStatsListDTO loadSurveyStats(String unitKey);

    /**
     * Creates a new survey for the given template
     *
     * @param template the template
     * @return the newly created survey
     */
    SurveyDTO createSurveyForTemplate(SurveyTemplateDTO template);

    /**
     * Persists the given survey to its microservice.
     *
     * @param survey the survey to save
     * @return the persisted survey
     */
    SurveyDTO saveSurvey(SurveyDTO survey);

    /**
     * Load the link for an anonymous survey.
     *
     * @param graphId survey graph id
     * @return survey link
     */
    SurveyLinkDTO loadSurveyLink(Long graphId);

    /**
     * Load the org unit with the foreign id.
     *
     * @param foreignId the foreign id
     * @return the org unit
     */
    OrganizationalUnitDTO findUnitByForeignId(String foreignId);

    /**
     * Load the reporting period with the given graph id.
     *
     * @param reportingPeriodGid graph id of the reporting period
     * @return the loaded reporting period
     */
    ReportingPeriodDTO loadReportingPeriod(Long reportingPeriodGid);
    
    /**
     * find reporting periods for foreign survey Ids
     *
     * @param foreignSurveyId
     * @return 
     */
    boolean findReportingPeriods(String foreignSurveyId);


    /**
     * Create a new reporting period for the given survey.
     *
     * @param reportingPeriod the reporting period to save
     * @return the created reporting period
     */
    ReportingPeriodDTO createReportingPeriod(ReportingPeriodDTO reportingPeriod);

    /**
     * Updates an existing reporting period.
     *
     * @param reportingPeriod the reporting period to save
     * @return the updated reporting period
     */
    ReportingPeriodDTO updateReportingPeriod(ReportingPeriodDTO reportingPeriod);

    /**
     * Saves a new participant session to survey microservice.
     *
     * @param session the participant session to save
     * @param ipAddress the ip address
     * @return the created participant session
     */
    ParticipantSessionDTO saveParticipantSession(ParticipantSessionDTO session, String ipAddress);

    /**
     * Loads the survey from survey microservice.
     *
     * @param templateId id of the template
     * @param templateVersion version of the template
     * @return the loaded survey
     */
    @Deprecated
    SurveyDTO loadSurvey(String templateId, long templateVersion);

    /**
     * Creates a new participant session for the given reporting period
     *
     * @param period the reporting period
     * @param customQuestions the list of questions
     * @param ipAddress ip address of the client
     * @return the created participant session
     */
    @Deprecated
    ParticipantSessionDTO createParticipantSession(ReportingPeriodDTO period, List<CustomQuestionDTO> customQuestions, String ipAddress);

}
