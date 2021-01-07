/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.api.resource;

import static com.mainlevel.monitoring.admin.api.Admin.SERVICE_NAME;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mainlevel.monitoring.admin.api.dto.predefined.PredefinedListDTO;
import com.mainlevel.monitoring.admin.api.dto.predefined.PredefinedListItemListDTO;

/**
 * Resource for loading predefined lists.
 */
@FeignClient(SERVICE_NAME)
public interface PredefinedListResource {

    /** Resource Mapping URI */
    public static final String URI = "/predefined";

    /**
     * Loads all available predefined list names.
     *
     * @return name of available lists
     */
    @RequestMapping(method = GET, value = URI, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<PredefinedListDTO> getAvailabeLists();

    /**
     * Loads entries for the predefined list with the given name.
     *
     * @param listName name of the list
     * @return the list items
     */
    @RequestMapping(method = GET, value = URI + "/{listName}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<PredefinedListItemListDTO> getListsItems(String listName);

}
