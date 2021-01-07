/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.service;

import java.util.List;

import com.mainlevel.monitoring.survey.database.node.Link;

/**
 * Service for maintaining survey links.
 */
public interface SurveyLinkService {

    /**
     * Create and persist a new link.
     *
     * @param link the link to save
     * @return the saved link
     */
    Link createLink(Link link);

    /**
     * Load all links for the current user.
     *
     * @return the list of links
     */
    List<Link> loadUserLinks();

    /**
     * Load the link for the given token.
     *
     * @param token the token
     * @return the link, or null if not found
     */
    Link loadLinkByToken(String token);

    /**
     * Load the link for an anonymous survey.
     *
     * @param surveyGid the survey id
     * @return the link
     */
    Link loadLinkForAnonymousSurvey(Long surveyGid);

}
