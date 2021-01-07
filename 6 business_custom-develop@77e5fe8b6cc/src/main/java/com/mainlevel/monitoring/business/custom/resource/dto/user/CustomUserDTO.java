/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */
package com.mainlevel.monitoring.business.custom.resource.dto.user;

import java.util.List;

import com.mainlevel.monitoring.authentication.api.dto.AuthenticationType;
import com.mainlevel.monitoring.authentication.api.dto.UserType;
import com.mainlevel.monitoring.business.custom.resource.dto.dashboard.CustomDashboardNameDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO for holding a user instance.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class CustomUserDTO {

    private Long identifier;

    private String username;

    private String firstName;

    private String lastName;

    private Boolean active;

    private UserType type;

    private Long expiryTime;

    private String defaultProject;

    private AuthenticationType authentication;

    private List<CustomRoleDTO> roles;

    private List<CustomDashboardNameDTO> dashboards;

    private CustomThemeDTO theme;

}
