package com.mainlevel.monitoring.authentication.api.dto;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO for transfering user information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class UserDTO extends ResourceSupport {

    private Long identifier;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private Boolean active;

    private UserType type;

    private Long expiryTime;

    private AuthenticationType authentication;

    private String jwt;

    private String theme;

    private List<RoleDTO> roles;
}
