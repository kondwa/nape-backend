/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.service.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mainlevel.monitoring.survey.database.repository.ReportingPeriodRepository;
import com.mainlevel.monitoring.survey.service.FacetService;
import com.mainlevel.monitoring.survey.service.model.facet.TimeFacet;
import com.mainlevel.monitoring.survey.service.model.facet.TimeFacetGroup;
import com.mainlevel.monitoring.survey.service.model.facet.TimeFacetIntervalType;

@SuppressWarnings("javadoc")
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {FacetServiceImplTest.TestConfiguration.class}, loader = AnnotationConfigWebContextLoader.class)
public class FacetServiceImplTest {

    public static class TestConfiguration {

        @Bean
        public FacetService facetService() {
            return new FacetServiceImpl();
        }

        @Bean
        public ReportingPeriodRepository reportingPeriodRepository() {
            return mock(ReportingPeriodRepository.class);
        }

    }

    @Autowired
    private FacetService facetService;

    @Autowired
    private ReportingPeriodRepository reportingPeriodRepository;

    @Test
    public void loadTimeFacets() {

        when(reportingPeriodRepository.findStartTimes()).thenReturn(Arrays.asList(0L));

        List<TimeFacetGroup> facets = facetService.loadTimeFacets();

        Assert.assertNotNull(facets);
        Assert.assertEquals(1, facets.size());

        TimeFacetGroup group = facets.get(0);
        Assert.assertNotNull(group);
        Assert.assertSame(TimeFacetIntervalType.YEARS, group.getType());

        Assert.assertEquals(1, group.getFacets().size());
        TimeFacet facet = group.getFacets().stream().findFirst().get();
        Assert.assertNotNull(facet);
        Assert.assertEquals("1970", facet.getLabel());
        Assert.assertEquals(0L, facet.getStartTime().longValue());
        Assert.assertEquals(31449600000L, facet.getEndTime().longValue());
    }

}
