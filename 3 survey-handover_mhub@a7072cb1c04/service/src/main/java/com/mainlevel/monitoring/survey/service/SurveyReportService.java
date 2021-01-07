/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.service;

import java.util.List;

import com.mainlevel.monitoring.survey.database.queryresult.SurveySummaryResult;

/**
 * Service for loading survey reports.
 */
public interface SurveyReportService {

    /**
     * Load the survey summary report entries.
     * 
     * @param surveyGid the survey gid
     * @return the list of entries
     */
    List<SurveySummaryResult> loadSurveySummary(Long surveyGid);

}
