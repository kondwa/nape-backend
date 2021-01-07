/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.service.impl;

import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mainlevel.monitoring.common.constant.ProfileConstant;
import com.mainlevel.monitoring.survey.database.queryresult.evaluation.EvaluationResult;
import com.mainlevel.monitoring.survey.database.repository.EvaluationRepository;
import com.mainlevel.monitoring.survey.service.EvaluationService;

@ActiveProfiles(ProfileConstant.WITHOUT_SECURITY)
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SuppressWarnings("javadoc")
@ContextConfiguration(classes = {EvaluationServiceImplTest.TestConfiguration.class}, loader = AnnotationConfigWebContextLoader.class)
public class EvaluationServiceImplTest {

    public static class TestConfiguration {

        @Bean
        public EvaluationServiceImpl evaluationServiceImpl() {
            return new EvaluationServiceImpl();
        }

        @Bean
        public EvaluationRepository evaluationRepository() {
            return mock(EvaluationRepository.class);
        }
    }

    @Autowired
    private EvaluationService evaluationService;

    @Test
    public void loadEvaluationResults() {
        List<EvaluationResult> results = evaluationService.loadEvaluationResults("Sample", null);
        Assert.assertNotNull(results);
    }

}
