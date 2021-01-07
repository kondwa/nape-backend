/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.impl;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.admin.api.dto.template.SurveyTemplateDTO;
import com.mainlevel.monitoring.admin.api.resource.TemplateResource;
import com.mainlevel.monitoring.business.custom.resource.CustomSurveyResourceV2;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyActivationDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyStatsDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyStatsListDTO;
import com.mainlevel.monitoring.business.custom.service.CustomLinkService;
import com.mainlevel.monitoring.business.custom.service.CustomSurveyService;
import com.mainlevel.monitoring.common.service.CollectionConversionService;
import com.mainlevel.monitoring.survey.api.dto.OrganizationalUnitDTO;
import com.mainlevel.monitoring.survey.api.dto.SurveyLinkDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyStatsListDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyStatus;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyVisibilityType;

import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link CustomSurveyResourceV2}.
 */
@Slf4j
@RestController
@Secured(ROLE_USER)
public class CustomSurveyResourceV2Impl implements CustomSurveyResourceV2 {

    @Autowired
    private CustomSurveyService customSurveyService;

    @Autowired
    private CustomLinkService customLinkService;

    @Autowired
    private TemplateResource templateClient;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Override
    public ResponseEntity<CustomSurveyStatsListDTO> getSurveyStats(
        @ApiParam(value = "Reference ID of organizational unit") @RequestParam(name = "unitKey", required = false) String unitKey) {

        SurveyStatsListDTO stats = customSurveyService.loadSurveyStats(unitKey);

        List<CustomSurveyStatsDTO> customStats = collectionConversionService.convert(stats.getSurveys(), CustomSurveyStatsDTO.class);

        log.info("Loaded {} survey stats from survey microservice.", customStats.size());

        CustomSurveyStatsListDTO dto = CustomSurveyStatsListDTO.builder().surveys(customStats).build();

        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<CustomSurveyDTO> getSurvey(@PathVariable("identifier") Long surveyGid, @RequestHeader("x-forwarded-proto") String protocol,
        @RequestHeader("x-forwarded-host") String host) {

        SurveyDTO survey = customSurveyService.loadSurvey(surveyGid, false);
        CustomSurveyDTO customSurvey = conversionService.convert(survey, CustomSurveyDTO.class);

        if (survey.getVisibility() == SurveyVisibilityType.ANONYMOUS) {
            SurveyLinkDTO link = customSurveyService.loadSurveyLink(survey.getGraphId());
            String hyperlink = customLinkService.createHyperlinkFromLink(protocol, host, link);
            customSurvey.setLink(hyperlink);
        }

        return ResponseEntity.ok(customSurvey);
    }

    @Override
    public ResponseEntity<CustomSurveyDTO> activateSurvey(@RequestBody CustomSurveyActivationDTO body) {

        String templateId = body.getTemplateIdentifier();
        long templateVersion = body.getTemplateVersion();

        log.info("Activate new survey for template {} and version {}.", templateId, templateVersion);

        SurveyTemplateDTO template = this.loadTemplate(templateId, templateVersion);
        SurveyDTO surveyDTO = customSurveyService.createSurveyForTemplate(template);

        surveyDTO.setSurveyName(body.getSurveyName());
        surveyDTO.setType(body.getType());
        surveyDTO.setStatus(SurveyStatus.ACTIVE);
        surveyDTO.setTargetInstances(body.getTargetInstances());

        OrganizationalUnitDTO unit = customSurveyService.findUnitByForeignId(body.getUnitForeignId());
        surveyDTO.setUnit(unit);

        if (Boolean.TRUE.equals(body.getAnonymous())) {
            surveyDTO.setVisibility(SurveyVisibilityType.ANONYMOUS);
        }

        SurveyDTO survey = customSurveyService.saveSurvey(surveyDTO);

        CustomSurveyDTO customSurvey = conversionService.convert(survey, CustomSurveyDTO.class);

        return ResponseEntity.ok(customSurvey);
    }

    /**
     * Loads the survey template from surveyadmin microservice.
     *
     * @param templateId id of the template
     * @param templateVersion version of the template
     * @return the loaded template
     */
    private SurveyTemplateDTO loadTemplate(String templateId, long templateVersion) {
        ResponseEntity<SurveyTemplateDTO> templateResponse = templateClient.getSurveyByVersion(templateId, templateVersion);

        if (templateResponse.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Error loading survey template '" + templateId + "' in version '" + templateVersion + "'. HTTP Status Code: "
                + templateResponse.getStatusCode());
        }

        if (templateResponse.getBody() == null) {
            throw new RuntimeException("Error loading survey template '" + templateId + "' in version '" + templateVersion + "'.");
        }

        return templateResponse.getBody();
    }

}
