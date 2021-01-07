/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mainlevel.monitoring.survey.api.dto.evaluation.TimeFacetIntervalType;
import com.mainlevel.monitoring.survey.api.dto.evaluation.TimeFacetValueDTO;
import com.mainlevel.monitoring.survey.api.dto.evaluation.TimeFacetsDTO;
import com.mainlevel.monitoring.survey.service.model.facet.TimeFacet;
import com.mainlevel.monitoring.survey.service.model.facet.TimeFacetGroup;

@WebAppConfiguration
@SuppressWarnings("javadoc")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TimeFacetGroupToTimeFacetsDTOConverterTest.TestConfiguration.class}, loader = AnnotationConfigWebContextLoader.class)
public class TimeFacetGroupToTimeFacetsDTOConverterTest {

    public static class TestConfiguration {
        @Bean
        public TimeFacetGroupToTimeFacetsDTOConverter converter() {
            return new TimeFacetGroupToTimeFacetsDTOConverter();
        }
    }

    @Autowired
    private TimeFacetGroupToTimeFacetsDTOConverter converter;

    @Test
    public void convert() {

        TimeFacet facet = TimeFacet.builder().label("2016").startTime(0L).endTime(Long.MAX_VALUE).build();
        Set<TimeFacet> facets = new HashSet<>();
        facets.add(facet);
        TimeFacetGroup source =
            TimeFacetGroup.builder().type(com.mainlevel.monitoring.survey.service.model.facet.TimeFacetIntervalType.YEARS).facets(facets).build();

        TimeFacetsDTO target = converter.convert(source);

        Assert.assertNotNull(target);
        Assert.assertSame(TimeFacetIntervalType.YEARS, target.getIntervalType());

        Assert.assertEquals(1, target.getFacets().size());

        TimeFacetValueDTO facetValueDTO = target.getFacets().get(0);
        Assert.assertNotNull(facetValueDTO);
        Assert.assertEquals("2016", facetValueDTO.getLabel());
        Assert.assertEquals(0, facetValueDTO.getStartTime().longValue());
        Assert.assertEquals(Long.MAX_VALUE, facetValueDTO.getEndTime().longValue());

    }
}
