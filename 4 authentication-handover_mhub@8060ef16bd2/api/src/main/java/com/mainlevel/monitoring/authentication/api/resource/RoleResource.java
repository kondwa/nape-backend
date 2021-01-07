package com.mainlevel.monitoring.authentication.api.resource;

import static com.mainlevel.monitoring.authentication.api.Authentication.SERVICE_NAME;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mainlevel.monitoring.authentication.api.dto.RoleListDTO;

/**
 * Endpoint for roles management.
 */
@FeignClient(SERVICE_NAME)
public interface RoleResource {

    /** Resource Mapping URI */
    final String URI = "/roles";

    /**
     * Load paginaged roles.
     *
     * @return the paged resource
     */
    @RequestMapping(value = URI, method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleListDTO> getRoles();

}
