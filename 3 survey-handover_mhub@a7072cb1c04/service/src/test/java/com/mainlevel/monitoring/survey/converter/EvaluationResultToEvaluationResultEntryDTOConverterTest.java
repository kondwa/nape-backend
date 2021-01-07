/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mainlevel.monitoring.survey.api.dto.evaluation.EvaluationResultEntryDTO;
import com.mainlevel.monitoring.survey.database.queryresult.evaluation.EvaluationResult;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SuppressWarnings("javadoc")
@ContextConfiguration(classes = {
    EvaluationResultToEvaluationResultEntryDTOConverterTest.TestConfiguration.class}, loader = AnnotationConfigWebContextLoader.class)
public class EvaluationResultToEvaluationResultEntryDTOConverterTest {

    public static class TestConfiguration {
        @Bean
        private EvaluationResultToEvaluationResultEntryDTOConverter converter() {
            return new EvaluationResultToEvaluationResultEntryDTOConverter();
        }
    }

    @Autowired
    private EvaluationResultToEvaluationResultEntryDTOConverter converter;

    @Test
    public void convert() throws Exception {

        EvaluationResult result = EvaluationResult.builder().title("Sample").recordName("Male").xAxisValue("2016").xAxisDesc("Name")
            .yAxisValue(100.0d).yAxisDesc("Value").build();
        EvaluationResultEntryDTO dto = converter.convert(result);

        Assert.assertNotNull(dto);
        Assert.assertEquals("Male", dto.getName());
        Assert.assertEquals("2016", dto.getXAxisValue());
        Assert.assertEquals(100l, dto.getYAxisValue().longValue(), 0.0d);
    }

    @Test
    public void convertNull() throws Exception {

        EvaluationResult result = EvaluationResult.builder().build();
        EvaluationResultEntryDTO dto = converter.convert(result);

        Assert.assertNotNull(dto);
        Assert.assertNull(dto.getXAxisValue());
        Assert.assertNull(dto.getYAxisValue());
    }
}
