/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.service.impl;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainlevel.monitoring.common.service.AuthenticationAccessService;
import com.mainlevel.monitoring.survey.database.queryresult.SurveySummaryResult;
import com.mainlevel.monitoring.survey.database.repository.SurveyReportRepository;
import com.mainlevel.monitoring.survey.service.SurveyReportService;

/**
 * Default implementation of {@link SurveyReportService}.
 */
@Service
public class SurveyReportServiceImpl implements SurveyReportService {

    @Autowired
    private SurveyReportRepository reportRepository;

    @Autowired
    private AuthenticationAccessService accessService;

    @Override
    @Transactional(propagation = REQUIRED, readOnly = true)
    public List<SurveySummaryResult> loadSurveySummary(Long surveyGid) {

        String username = accessService.getCurrentUsername();

        return reportRepository.loadSurveySummaryReport(surveyGid, username);
    }

}
