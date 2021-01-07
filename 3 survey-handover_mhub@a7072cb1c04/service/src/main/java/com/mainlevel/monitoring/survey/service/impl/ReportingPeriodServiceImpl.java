/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.service.impl;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainlevel.monitoring.common.service.AuthenticationAccessService;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodStatus;
import com.mainlevel.monitoring.survey.api.exception.ReportingPeriodNotExistException;
import com.mainlevel.monitoring.survey.database.node.Answer;
import com.mainlevel.monitoring.survey.database.node.Client;
import com.mainlevel.monitoring.survey.database.node.ReportingPeriod;
import com.mainlevel.monitoring.survey.database.node.User;
import com.mainlevel.monitoring.survey.database.queryresult.ReportingPeriodDetailQueryResult;
import com.mainlevel.monitoring.survey.database.queryresult.ReportingPeriodOverview;
import com.mainlevel.monitoring.survey.database.repository.AnswerRepository;
import com.mainlevel.monitoring.survey.database.repository.ClientRepository;
import com.mainlevel.monitoring.survey.database.repository.ReportingPeriodRepository;
import com.mainlevel.monitoring.survey.database.repository.UserRepository;
import com.mainlevel.monitoring.survey.service.ReportingPeriodService;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link ReportingPeriodService}.
 */
@Slf4j
@Service
public class ReportingPeriodServiceImpl implements ReportingPeriodService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private ReportingPeriodRepository reportingPeriodRepository;

    @Autowired
    private AuthenticationAccessService authenticationAccessService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(propagation = REQUIRED, readOnly = true)
    public ReportingPeriod loadReportingPeriod(final Long reportingPeriodId) {
        return this.loadReportingPeriod(reportingPeriodId, 1);
    }

    @Override
    @Transactional(propagation = REQUIRED, readOnly = true)
    public ReportingPeriod loadReportingPeriod(final Long reportingPeriodId, Integer depth) {

        log.debug("Loading reporting period with id {}.", reportingPeriodId);

        if (depth == null) {
            depth = 1;
        }

        ReportingPeriod reportingPeriod = reportingPeriodRepository.findOne(reportingPeriodId, depth);

        if (reportingPeriod == null) {
            throw new ReportingPeriodNotExistException("The target reportingPeriod (gid=" + reportingPeriodId + ") doesn't exists.");
        }

        return reportingPeriod;
    }
    
    @Override
    @Transactional(propagation = REQUIRED, readOnly = true)
    public List<ReportingPeriod> loadReportingPeriods(final String foreignSurveyId) {

        log.info("Loading reporting period with foreign survey Id {}.", foreignSurveyId);

        return reportingPeriodRepository.findByForeignSurveyId(foreignSurveyId);

    }


    @Override
    @Transactional(propagation = REQUIRED, readOnly = false)
    public ReportingPeriod saveReportingPeriod(final ReportingPeriod reportingPeriod) {

        log.debug("Saving reporting period with id {}.", reportingPeriod.getGid());

        if (reportingPeriod.getGid() == null) {
            reportingPeriod.setCreated(new Date());

            if (reportingPeriod.getStatus() == null) {
                reportingPeriod.setStatus(ReportingPeriodStatus.NEW);
            }

            String username = authenticationAccessService.getCurrentUsername();
            User user = this.userRepository.findByUsername(username);
            reportingPeriod.setUser(user);

            if (reportingPeriod.getClient() != null) {
                Client client = clientRepository.findByAttributes(reportingPeriod.getClient().getAddress(), reportingPeriod.getClient().getBrowser(),
                    reportingPeriod.getClient().getOs(), reportingPeriod.getClient().getType());
                if (client != null) {
                    reportingPeriod.setClient(client);
                }
            }

        } else {
            if (reportingPeriod.getStatus() == ReportingPeriodStatus.NEW) {
                reportingPeriod.setStatus(ReportingPeriodStatus.IN_PROGRESS);
            }
        }

        int depth = reportingPeriod.getSurvey() != null ? 1 : 0;
        return reportingPeriodRepository.save(reportingPeriod, depth);
    }

    @Override
    @Transactional(propagation = REQUIRED, readOnly = true)
    public List<ReportingPeriodOverview> loadReportingPeriodOverviews(String unitId) {

        String username = authenticationAccessService.getCurrentUsername();

        List<ReportingPeriodOverview> overviewList = reportingPeriodRepository.findOverviews(unitId, username);

        log.info("Found {} visible reporting periods for user {} by unit {}.", overviewList.size(), username, unitId);

        return overviewList;
    }

    @Override
    @Transactional(propagation = REQUIRED, readOnly = true)
    public List<ReportingPeriodOverview> loadReportingPeriodOverviewsForSurvey(Long surveyGid) {

        String username = authenticationAccessService.getCurrentUsername();

        List<ReportingPeriodOverview> overviewList = reportingPeriodRepository.findOverviews(surveyGid, username);

        log.info("Found {} visible reporting periods for user {} by survey {}.", overviewList.size(), username, surveyGid);

        return overviewList;
    }

    @Override
    @Transactional(propagation = REQUIRED, readOnly = true)
    public ReportingPeriodDetailQueryResult loadReportingPeriodData(Long reportingPeriodId) {
        ReportingPeriod reportingPeriod = reportingPeriodRepository.findReportingPeriodWithSurvey(reportingPeriodId);
        // ParticipantSession participantSession = participantSessionRepository.findByPeriodIdWithAnswers(reportingPeriodId);

        List<Answer> answers = answerRepository.findAnswersForPeriod(reportingPeriodId);

        return ReportingPeriodDetailQueryResult.builder().reportingPeriod(reportingPeriod).answers(answers).build();
    }

}
