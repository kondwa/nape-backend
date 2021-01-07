/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.resource;

import static com.mainlevel.monitoring.survey.api.Survey.SERVICE_NAME;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodDataDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodOverviewListDTO;

/**
 * Resource for loading and saving reporting periods.
 */
@FeignClient(SERVICE_NAME)
public interface ReportingPeriodResource {

    /** Resource Mapping URI */
    final String URI = "/reportingPeriods";

    /**
     * Load the reporting period with the given id including the survey structuce.
     *
     * @param reportingPeriodId the reporting period data
     * @return the reporting period data
     */
    @RequestMapping(value = URI + "/{identifier}/data", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<ReportingPeriodDataDTO> getReportingPeriodData(@PathVariable("identifier") Long reportingPeriodId);

    /**
     * Load the reporting period with the given id.
     *
     * @param reportingPeriodGid the reporting period graph id
     * @return the reporting period
     */
    @RequestMapping(value = URI + "/{identifier}", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<ReportingPeriodDTO> getReportingPeriod(@PathVariable("identifier") Long reportingPeriodGid);

    /**
     * Load the list of reporting periods.
     *
     * @param unitId (optional) id of the organizational unit
     * @param surveyGid (optional) graph id of the survey to load reporting periods for
     * @return the list of reporting periods
     */
    @RequestMapping(value = URI, method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<ReportingPeriodOverviewListDTO> getReportingPeriods(@RequestParam(name = "unitId", required = false) final String unitId,
        @RequestParam(name = "surveyGid", required = false) final Long surveyGid);

    /**
     * Creates a new reporting period.
     *
     * @param period the reporting period to create
     * @return the saved reporting period
     */
    @RequestMapping(value = URI, method = POST, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<ReportingPeriodDTO> createReportingPeriod(@RequestBody ReportingPeriodDTO period);

    /**
     * Updates an existing reporting period.
     *
     * @param reportingPeriodGid graph id of the reporting period to update
     * @param period the reporting period to update
     * @return the saved reporting period
     */
    @RequestMapping(value = URI + "{identifier}", method = PUT, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<ReportingPeriodDTO> updateReportingPeriod(@PathVariable("identifier") Long reportingPeriodGid,
        @RequestBody ReportingPeriodDTO period);

    /**
     * Delete the reporting period with the given identifier.
     *
     * @param reportingPeriodId graph id of the reporting period
     * @return empty response
     */
    @RequestMapping(value = URI + "/{identifier}", method = DELETE, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteReportingPeriod(@PathVariable("identifier") Long reportingPeriodId);
    
    /**
     * Updating the reporting period with the given identifier.
     *
     * @param reportingPeriodId graph id of the reporting period
     * @param status the new status for the reporting period
     * @return empty response
     */
    @RequestMapping(value = URI + "/{identifier}/{status}", method = PUT, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Void> updateReportingPeriod(@PathVariable("identifier") Long reportingPeriodId, @PathVariable("status") String status);

    /**
     * Load reporting periods with the given foreign survey Id.
     *
     * @param foreignSurveyId the foreign survey Id
     * @return the reporting periods
     */
    @RequestMapping(value = URI + "/foreignId/{foreignSurveyId}", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<List<ReportingPeriodDTO>> getReportingPeriods(@PathVariable("foreignSurveyId") String foreignSurveyId);

}
