/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.resource.links;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import com.mainlevel.monitoring.admin.api.dto.template.SurveyTemplateDTO;
import com.mainlevel.monitoring.admin.resource.TemplateResourceImpl;

import lombok.NoArgsConstructor;

/**
 * Link provider for template resource.
 */
@NoArgsConstructor(access = PRIVATE)
public class SurveyTemplateResourceLinkProvider {

    /**
     * Create link for POST on /survey resource.
     *
     * @return the link builder
     */
    public static ControllerLinkBuilder createLinkToCreateSurvey() {
        return linkTo(methodOn(TemplateResourceImpl.class).createSurveyTemplate(null));
    }

    /**
     * Create link for GET on /admin/surveys resource.
     *
     * @return the link builder
     */
    public static ControllerLinkBuilder createLinkToGetSurveys() {
        return linkTo(methodOn(TemplateResourceImpl.class).getSurveyTemplates(null));
    }

    /**
     * Create link for GET on /admin/surveys/{id} resource.
     *
     * @param identifier the survey template id
     * @return the link builder
     */
    public static ControllerLinkBuilder createLinkToGetSurvey(String identifier) {
        return linkTo(methodOn(TemplateResourceImpl.class).getSurveyTemplate(identifier));
    }

    /**
     * Create link for PUT on /admin/surveys/{id} resource.
     *
     * @param identifier the survey id
     * @return the link builder
     */
    public static ControllerLinkBuilder createLinkToUpdateSurvey(String identifier) {
        return linkTo(methodOn(TemplateResourceImpl.class).updateSurvey(identifier, new SurveyTemplateDTO()));
    }

    /**
     * Create link for DELETE on /admin/surveys/{id} resource.
     *
     * @param identifier the survey template id
     * @return the link builder
     */
    public static ControllerLinkBuilder createLinkToDeleteSurvey(String identifier) {
        return linkTo(methodOn(TemplateResourceImpl.class).deleteSurvey(identifier));
    }

    /**
     * Create link for GET on /admin/surveys/history/{id} resource.
     *
     * @param identifier the survey history id
     * @return the link builder
     */
    public static ControllerLinkBuilder createLinkToGetHistory(String identifier) {
        return linkTo(methodOn(TemplateResourceImpl.class).getHistory(identifier));
    }

    /**
     * Create link for PUT on /admin/surveys/{id}/settings
     *
     * @param identifier the survey template id
     * @return the link builder
     */
    public static ControllerLinkBuilder createLinkToUpdateSurveySettings(String identifier) {
        return linkTo(methodOn(TemplateResourceImpl.class).updateSurveySettings(identifier, null));
    }

    /**
     * Create link for GET on /admin/surveys/{id}/version/{version} resource.
     *
     * @param identifier the survey template id
     * @param version the survey template version
     * @return the link builder
     */
    public static ControllerLinkBuilder createLinkToGetByVersion(String identifier, Long version) {
        return linkTo(methodOn(TemplateResourceImpl.class).getSurveyByVersion(identifier, version));
    }

    /**
     * Create link for DELETE on /admin/surveys/{id}/version/{version} resource.
     *
     * @param identifier the survey template id
     * @param version the survey template version
     * @return the link builder
     */
    public static ControllerLinkBuilder createLinkToDeleteSurveyVersion(String identifier, Long version) {
        return linkTo(methodOn(TemplateResourceImpl.class).deleteSurveyVersion(identifier, version));
    }
}
