/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.resource.impl;

import static com.mainlevel.monitoring.common.constant.ProfileConstant.WITHOUT_SECURITY;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mainlevel.monitoring.common.service.CollectionConversionService;
import com.mainlevel.monitoring.survey.api.dto.evaluation.TimeFacetsDTO;
import com.mainlevel.monitoring.survey.api.dto.evaluation.TimeFacetsListDTO;
import com.mainlevel.monitoring.survey.api.resource.EvaluationFacetsResource;
import com.mainlevel.monitoring.survey.configuration.ResourceTestConfiguration;
import com.mainlevel.monitoring.survey.resource.EvaluationFacetsResourceImpl;
import com.mainlevel.monitoring.survey.service.FacetService;
import com.mainlevel.monitoring.survey.service.model.facet.TimeFacetGroup;

@ActiveProfiles(WITHOUT_SECURITY)
@WebAppConfiguration
@SuppressWarnings("javadoc")
@SpringBootTest(classes = EvaluationFacetResourceImplTest.TestConfiguration.class)
@TestPropertySource(properties = {"spring.cloud.config.failFast : false"})
@RunWith(SpringJUnit4ClassRunner.class)
public class EvaluationFacetResourceImplTest {

    @Import(ResourceTestConfiguration.class)
    public static class TestConfiguration {

        @Bean
        private EvaluationFacetsResource EvaluationResource() {
            return new EvaluationFacetsResourceImpl();
        }

        @Bean
        private FacetService evaluationService() {
            return mock(FacetService.class);
        }

        @Bean
        private CollectionConversionService collectionConversionService() {
            return mock(CollectionConversionService.class);
        }
    }

    @Autowired
    private EvaluationFacetsResource facetsResource;

    @Autowired
    private FacetService facetService;

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Test
    public void getFacets() {

        List<TimeFacetGroup> groups = Arrays.asList(TimeFacetGroup.builder().build());
        when(facetService.loadTimeFacets()).thenReturn(groups);
        when(collectionConversionService.convert(groups, TimeFacetsDTO.class)).thenReturn(Arrays.asList(TimeFacetsDTO.builder().build()));

        ResponseEntity<TimeFacetsListDTO> rs = facetsResource.getTimeFacets();

        Assert.assertNotNull(rs);
        Assert.assertNotNull(rs.getBody());
        Assert.assertEquals(1, rs.getBody().getTimeFacets().size());
    }

}
