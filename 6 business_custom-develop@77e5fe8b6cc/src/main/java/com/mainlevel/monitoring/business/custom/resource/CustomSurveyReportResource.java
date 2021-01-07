/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mainlevel.monitoring.common.resource.dto.ErrorDTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * REST resource for loading reports of a specific survey.
 */
@RequestMapping(value = CustomSurveyReportResource.URI, produces = APPLICATION_JSON_VALUE)
public interface CustomSurveyReportResource {

    /** Resource Mapping URI */
    final String URI = "/surveys/{identifier}/report";

    /**
     * Load the survey for the given name and return it as CSV.
     *
     * @param surveyGid the survey graph id
     * @param reportName name of the report
     * @param parameters list of report parameters
     * @param response http response
     */
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return the report as csv."),
        @ApiResponse(code = 401, message = "Unauthorized access", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)})
    @ApiOperation(value = "Load the survey for the given name and return it as CSV.")
    @RequestMapping(value = "/{reportName}", method = GET, produces = {"text/csv"})
    void loadSurveyReportAsCSV(@PathVariable("identifier") final Long surveyGid, @PathVariable("reportName") final String reportName,
        @RequestParam final Map<String, String> parameters, final HttpServletResponse response);

}
