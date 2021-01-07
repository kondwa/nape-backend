/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.api.resource;

import static com.mainlevel.monitoring.admin.api.Admin.SERVICE_NAME;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import javax.validation.Valid;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mainlevel.monitoring.admin.api.dto.template.SurveyHistoryListDTO;
import com.mainlevel.monitoring.admin.api.dto.template.SurveySettingsDTO;
import com.mainlevel.monitoring.admin.api.dto.template.SurveyTemplateDTO;
import com.mainlevel.monitoring.admin.api.dto.template.SurveyTemplateListDTO;

/**
 * Resource for maintaining survey templates.
 */
@FeignClient(SERVICE_NAME)
public interface TemplateResource {

    /** Resource Mapping URI */
    public static final String URI = "/templates";

    /**
     * Find survey templates for the given filter criteria.
     *
     * @param unitId id of the related organizational unit
     * @return list of survey templates
     */
    @RequestMapping(value = URI + "/", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<SurveyTemplateListDTO> getSurveyTemplates(@RequestParam(name = "unitId", required = false) String unitId);

    /**
     * Return composite survey(with survey template) with the given identifier and version.
     *
     * @param identifier survey identifier
     * @param version the template version
     * @return survey with current version of template.
     */
    @RequestMapping(value = URI + "/{identifier}/version/{version}", method = GET)
    ResponseEntity<SurveyTemplateDTO> getSurveyByVersion(@PathVariable("identifier") String identifier, @PathVariable("version") Long version);

    /**
     * Return composite survey(with survey template) for the given identifier.
     *
     * @param identifier survey identifier
     * @return survey with current version of template.
     */
    @RequestMapping(value = URI + "/{identifier}", method = GET)
    ResponseEntity<SurveyTemplateDTO> getSurveyTemplate(@PathVariable("identifier") String identifier);

    /**
     * Create a new survey for the given data.
     *
     * @param surveyDTO the survey data
     * @return the created survey
     */
    @RequestMapping(value = URI, method = POST)
    ResponseEntity<SurveyTemplateDTO> createSurveyTemplate(@RequestBody @Valid SurveyTemplateDTO surveyDTO);

    /**
     * Get the survey history for the given template id.
     *
     * @param identifier the template id
     * @return the history dto
     */
    @RequestMapping(value = URI + "/{identifier}/history", method = GET)
    ResponseEntity<SurveyHistoryListDTO> getHistory(@PathVariable("identifier") String identifier);

    /**
     * Updates surveys settings, and create new version of target survey.
     *
     * @param identifier the survey identifier
     * @param settingsDTO the settings dto
     * @return the updated survey
     */
    @RequestMapping(value = URI + "/{identifier}/settings", method = PUT)
    ResponseEntity<SurveyTemplateDTO> updateSurveySettings(@PathVariable("identifier") String identifier, @RequestBody SurveySettingsDTO settingsDTO);

    /**
     * Update existing survey, if survey with given identifier doesn't exists it will throw exception
     *
     * @param identifier the survey identifier
     * @param surveyDTO the survey dto
     * @return updated survey
     */
    @RequestMapping(value = URI + "/{identifier}", method = PUT)
    ResponseEntity<SurveyTemplateDTO> updateSurvey(@PathVariable("identifier") String identifier, @RequestBody SurveyTemplateDTO surveyDTO);

    /**
     * Deletes a survey.
     *
     * @param identifier id of survey
     * @return RespondEntity with no content.
     */
    @RequestMapping(value = URI + "/{identifier}", method = DELETE)
    ResponseEntity<Void> deleteSurvey(@PathVariable("identifier") String identifier);

    /**
     * Deletes a concrete version of a survey.
     *
     * @param identifier the survey id
     * @param version the version
     * @return empty response
     */
    @RequestMapping(value = URI + "/{identifier}/version/{version}", method = DELETE)
    ResponseEntity<Void> deleteSurveyVersion(@PathVariable("identifier") String identifier, @PathVariable("version") Long version);

}
