/** Copyright(c)2017 PRODYNA AG.All rights reserved. */

package com.mainlevel.monitoring.survey.database.repository.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.response.model.QueryResultModel;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mainlevel.monitoring.common.constant.ProfileConstant;
import com.mainlevel.monitoring.survey.api.exception.EvaluationQueryNotFoundException;
import com.mainlevel.monitoring.survey.database.queryresult.evaluation.EvaluationResult;
import com.mainlevel.monitoring.survey.database.repository.EvaluationRepository;

@ActiveProfiles(ProfileConstant.WITHOUT_SECURITY)
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SuppressWarnings("javadoc")
@ContextConfiguration(classes = {EvaluationRepositoryImplTest.TestConfiguration.class}, loader = AnnotationConfigWebContextLoader.class)
public class EvaluationRepositoryImplTest {

    public static class TestConfiguration {

        @Bean
        public EvaluationRepository evaluationRepository() {
            return new EvaluationRepositoryImpl();
        }

        @Bean
        public Session session() {
            return mock(Session.class);
        }

    }

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private Session neo4jSession;

    @Test
    public void findEvaluationResults() {

        Map<String, Object> resultParams = new HashMap<>();
        resultParams.put("title", "Test");
        resultParams.put("xAxisValue", "Country");
        resultParams.put("yAxisValue", 100d);
        resultParams.put("xAxisDesc", "Country Name");
        resultParams.put("yAxisDesc", "Amount of Institutes");

        Result queryResult = new QueryResultModel(Arrays.asList(resultParams), null);

        when(neo4jSession.query("abc", Collections.emptyMap())).thenReturn(queryResult);

        List<EvaluationResult> results = evaluationRepository.findEvaluationResults("test", null);
        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());

        EvaluationResult result = results.get(0);
        Assert.assertNotNull(result);
        Assert.assertEquals("Test", result.getTitle());
        Assert.assertEquals("Country", result.getXAxisValue());
        Assert.assertEquals(100d, result.getYAxisValue().doubleValue(), 0.0);
        Assert.assertEquals("Country Name", result.getXAxisDesc());
        Assert.assertEquals("Amount of Institutes", result.getYAxisDesc());
    }

    @Test(expected = EvaluationQueryNotFoundException.class)
    public void findEvaluationResultsWithInvalidQuery() {
        evaluationRepository.findEvaluationResults("blub", null);
    }

}
