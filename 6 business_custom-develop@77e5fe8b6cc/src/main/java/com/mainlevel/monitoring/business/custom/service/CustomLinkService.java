/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.service;

import com.mainlevel.monitoring.survey.api.dto.SurveyLinkDTO;

/**
 * Service for working with survey links.
 */
public interface CustomLinkService {

    /**
     * Crate a fully qualified link.
     *
     * @param protocol the protocol
     * @param host the domain
     * @param link the link object
     * @return the complete link
     */
    String createHyperlinkFromLink(String protocol, String host, SurveyLinkDTO link);

    /**
     * Save a link to survey.
     *
     * @param link the link to save
     * @return the saved link
     */
    SurveyLinkDTO saveLink(SurveyLinkDTO link);

}
