/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */
package com.mainlevel.monitoring.survey.service;

import java.util.List;

import com.mainlevel.monitoring.survey.database.node.ReportingPeriod;
import com.mainlevel.monitoring.survey.database.queryresult.ReportingPeriodDetailQueryResult;
import com.mainlevel.monitoring.survey.database.queryresult.ReportingPeriodOverview;

/**
 * Service for maintaining reporting periods.
 */
public interface ReportingPeriodService {

    /**
     * Load reporting period that match by graph id.
     *
     * @param reportingPeriodId target project graph id.
     * @return reporting period with given id, or <code>null</code> if it doesn't exists.
     */
    ReportingPeriod loadReportingPeriod(Long reportingPeriodId);

    /**
     * Load reporting period that match the given foreign survey Id.
     *
     * @param foreignSurveyId foreign survey Id (from app).
     * @return reporting period with given foreignSurveyId, or <code>null</code> if it doesn't exists.
     */
    List<ReportingPeriod> loadReportingPeriods(String foreignSurveyId);
    
    /**
     * Load reporting period that match by graph id.
     *
     * @param reportingPeriodId target project graph id.
     * @param depth the amount of loaded relations
     * @return reporting period with given id, or <code>null</code> if it doesn't exists.
     */
    ReportingPeriod loadReportingPeriod(Long reportingPeriodId, Integer depth);

    /**
     * It saves the reportingPeriod in db.
     *
     * @param reportingPeriod a period to store in db.
     * @return stored ReportingPeriod
     */
    ReportingPeriod saveReportingPeriod(ReportingPeriod reportingPeriod);

    /**
     * Load the list of survey overviews for the given criteria.
     *
     * @param unitRefId the organizational unit reference id (survey admin)
     * @return the list of found survey overviews
     */
    List<ReportingPeriodOverview> loadReportingPeriodOverviews(String unitRefId);

    /**
     * Load the list of survey overviews for the given criteria.
     *
     * @param surveyGid the survey gid
     * @return the list of found survey overviews
     */
    List<ReportingPeriodOverview> loadReportingPeriodOverviewsForSurvey(Long surveyGid);

    /**
     * Load the reporting period data for the given id.
     *
     * @param reportingPeriodId the reporting period graph id
     * @return the loaded reporting period data
     */
    ReportingPeriodDetailQueryResult loadReportingPeriodData(Long reportingPeriodId);

}
