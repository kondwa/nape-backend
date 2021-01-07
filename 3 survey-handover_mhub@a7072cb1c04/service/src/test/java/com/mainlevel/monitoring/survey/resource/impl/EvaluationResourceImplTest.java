/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.resource.impl;

import static com.mainlevel.monitoring.common.constant.ProfileConstant.WITHOUT_SECURITY;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mainlevel.monitoring.common.service.CollectionConversionService;
import com.mainlevel.monitoring.survey.api.dto.evaluation.EvaluationResultEntryDTO;
import com.mainlevel.monitoring.survey.configuration.ResourceTestConfiguration;
import com.mainlevel.monitoring.survey.database.queryresult.evaluation.EvaluationResult;
import com.mainlevel.monitoring.survey.resource.EvaluationResourceImpl;
import com.mainlevel.monitoring.survey.resource.links.EvaluationResourceLinkProvider;
import com.mainlevel.monitoring.survey.service.EvaluationService;
import com.mainlevel.monitoring.survey.service.model.facet.FacetParams;

@ActiveProfiles(WITHOUT_SECURITY)
@WebAppConfiguration
@SuppressWarnings("javadoc")
@SpringBootTest(classes = EvaluationResourceImplTest.TestConfiguration.class)
@TestPropertySource(properties = {"spring.cloud.config.failFast : false"})
@RunWith(SpringJUnit4ClassRunner.class)
public class EvaluationResourceImplTest {

    @Import(ResourceTestConfiguration.class)
    public static class TestConfiguration {

        @Bean
        private EvaluationResourceImpl evaluationResource() {
            return new EvaluationResourceImpl();
        }

        @Bean
        private EvaluationService evaluationService() {
            return mock(EvaluationService.class);
        }

        @Bean
        private CollectionConversionService collectionConversionService() {
            return mock(CollectionConversionService.class);
        }

    }

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Autowired
    private WebApplicationContext webapplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webapplicationContext).build();
    }

    @Test
    public void loadEvaluationResults() throws Exception {

        List<EvaluationResult> results = Arrays.asList(EvaluationResult.builder().title("Sample").recordName("Male").xAxisValue("2016")
            .xAxisDesc("Time").yAxisValue(100.0d).yAxisDesc("Value").build());
        List<EvaluationResultEntryDTO> resultDTOs =
            Arrays.asList(EvaluationResultEntryDTO.builder().name("Male").xAxisValue("2016").yAxisValue(100L).build());

        String url = EvaluationResourceLinkProvider.createLinkToLoadEvaluationResults("Sample").toString();

        when(evaluationService.loadEvaluationResults(anyString(), any(FacetParams.class))).thenReturn(results);
        when(collectionConversionService.convert(results, EvaluationResultEntryDTO.class)).thenReturn(resultDTOs);

        mockMvc.perform(get(url).accept(APPLICATION_JSON)).andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.title").value("Sample"))
            .andExpect(jsonPath("$.xaxisDesc").value("Time")).andExpect(jsonPath("$.yaxisDesc").value("Value"))
            .andExpect(jsonPath("$.data[0].name").value("Male")).andExpect(jsonPath("$.data[0].xaxisValue").value("2016"))
            .andExpect(jsonPath("$.data[0].yaxisValue").value(100L));
    }

}
