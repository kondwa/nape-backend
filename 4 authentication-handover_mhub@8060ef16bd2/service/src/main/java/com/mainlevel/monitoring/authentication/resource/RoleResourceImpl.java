package com.mainlevel.monitoring.authentication.resource;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.authentication.api.dto.RoleDTO;
import com.mainlevel.monitoring.authentication.api.dto.RoleListDTO;
import com.mainlevel.monitoring.authentication.api.resource.RoleResource;
import com.mainlevel.monitoring.authentication.service.RoleService;
import com.mainlevel.monitoring.common.monitoring.annotation.PerformanceMonitor;

/**
 * Default implementation of {@link RoleResource}.
 */
@RestController
@Secured(ROLE_USER)
public class RoleResourceImpl implements RoleResource {

    @Autowired
    private RoleService roleService;

    @Override
    @PerformanceMonitor
    public ResponseEntity<RoleListDTO> getRoles() {

        List<RoleDTO> roles = roleService.getRoles();
        RoleListDTO result = RoleListDTO.builder().roles(roles).build();

        return ResponseEntity.ok(result);
    }

}
