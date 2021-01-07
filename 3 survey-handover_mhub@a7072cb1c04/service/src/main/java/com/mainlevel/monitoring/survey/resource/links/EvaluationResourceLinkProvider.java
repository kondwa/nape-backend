/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.resource.links;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import com.mainlevel.monitoring.survey.resource.EvaluationResourceImpl;

/**
 * Link Builder for evaluation resource.
 */
public class EvaluationResourceLinkProvider {

    /** Private constructor should not be invoked. */
    private EvaluationResourceLinkProvider() {
    }

    /**
     * Create link on evaluation resource.
     *
     * @param queryName name of the query
     * @return the link builder
     */
    public static ControllerLinkBuilder createLinkToLoadEvaluationResults(final String queryName) {
        return linkTo(methodOn(EvaluationResourceImpl.class).loadEvaluationResults(queryName, null));
    }
}
