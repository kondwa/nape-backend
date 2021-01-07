/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mainlevel.monitoring.common.service.AuthenticationAccessService;
import com.mainlevel.monitoring.survey.database.node.ReportingPeriod;
import com.mainlevel.monitoring.survey.database.repository.AnswerRepository;
import com.mainlevel.monitoring.survey.database.repository.ClientRepository;
import com.mainlevel.monitoring.survey.database.repository.ParticipantSessionRepository;
import com.mainlevel.monitoring.survey.database.repository.ReportingPeriodRepository;
import com.mainlevel.monitoring.survey.database.repository.UserRepository;
import com.mainlevel.monitoring.survey.service.ReportingPeriodService;

@SuppressWarnings("javadoc")
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ReportingPeriodServiceImplTest.TestConfiguration.class}, loader = AnnotationConfigWebContextLoader.class)
public class ReportingPeriodServiceImplTest {

    public static class TestConfiguration {

        @Bean
        public ReportingPeriodService reportingPeriodService() {
            return new ReportingPeriodServiceImpl();
        }

        @Bean
        public ReportingPeriodRepository reportingPeriodRepository() {
            return mock(ReportingPeriodRepository.class);
        }

        @Bean
        public AnswerRepository answerRepository() {
            return mock(AnswerRepository.class);
        }

        @Bean
        public AuthenticationAccessService authenticationAccessService() {
            return mock(AuthenticationAccessService.class);
        }

        @Bean
        public ParticipantSessionRepository participantSessionRepository() {
            return mock(ParticipantSessionRepository.class);
        }

        @Bean
        public UserRepository userRepository() {
            return mock(UserRepository.class);
        }

        @Bean
        public ClientRepository clientRepository() {
            return mock(ClientRepository.class);
        }

    }

    @Autowired
    private ReportingPeriodService reportingPeriodService;

    @Autowired
    private ReportingPeriodRepository reportingPeriodRepository;

    @Test
    public void loadReportingPeriod() {

        ReportingPeriod rp = ReportingPeriod.builder().gid(7L).build();
        when(reportingPeriodRepository.findOne(7L, 1)).thenReturn(rp);

        ReportingPeriod result = reportingPeriodService.loadReportingPeriod(7L);
        assertNotNull(result);
        assertEquals(7L, result.getGid().longValue());
    }

    @Test
    public void saveReportingPeriod() {

        ReportingPeriod reportingPeriod = ReportingPeriod.builder().gid(7L).build();
        when(reportingPeriodRepository.save(reportingPeriod, 0)).thenReturn(reportingPeriod);

        final ReportingPeriod result = reportingPeriodService.saveReportingPeriod(reportingPeriod);

        assertNotNull(result);
        assertEquals(7L, (long) result.getGid());
    }
}
