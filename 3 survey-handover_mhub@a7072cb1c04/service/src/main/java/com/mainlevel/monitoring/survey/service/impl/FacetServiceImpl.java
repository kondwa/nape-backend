/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.service.impl;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainlevel.monitoring.survey.database.repository.ReportingPeriodRepository;
import com.mainlevel.monitoring.survey.service.FacetService;
import com.mainlevel.monitoring.survey.service.model.facet.TimeFacet;
import com.mainlevel.monitoring.survey.service.model.facet.TimeFacetGroup;
import com.mainlevel.monitoring.survey.service.model.facet.TimeFacetIntervalType;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link FacetService}.
 */
@Slf4j
@Service
public class FacetServiceImpl implements FacetService {

    @Autowired
    private ReportingPeriodRepository reportingPeriodRepository;

    @Override
    public List<TimeFacetGroup> loadTimeFacets() {

        log.debug("Loading time facets.");

        List<Long> startTimes = reportingPeriodRepository.findStartTimes();
        Set<TimeFacet> years = getYears(startTimes);

        List<TimeFacetGroup> facetGroups = new ArrayList<>();
        facetGroups.add(TimeFacetGroup.builder().facets(years).type(TimeFacetIntervalType.YEARS).build());

        return facetGroups;
    }

    /**
     * Load the list of distinct reporting period years.
     *
     * @param startTimes the starting times
     * @return the list of years
     */
    private Set<TimeFacet> getYears(List<Long> startTimes) {
        Set<TimeFacet> facets = new LinkedHashSet<>();

        for (Long startTime : startTimes) {
            ZonedDateTime time = Instant.ofEpochMilli(startTime).atZone(ZoneId.systemDefault());

            LocalDate date = LocalDate.from(time);
            LocalDate firstDay = date.with(firstDayOfYear());
            LocalDate lastDay = date.with(lastDayOfYear());

            long firstDayTimestamp = firstDay.toEpochDay() * 24 * 60 * 60 * 1000;
            long lastDayTimestamp = lastDay.toEpochDay() * 24 * 60 * 60 * 1000;

            TimeFacet facet =
                TimeFacet.builder().startTime(firstDayTimestamp).endTime(lastDayTimestamp).label(String.valueOf(time.getYear())).build();

            facets.add(facet);
        }

        return facets;
    }

}
