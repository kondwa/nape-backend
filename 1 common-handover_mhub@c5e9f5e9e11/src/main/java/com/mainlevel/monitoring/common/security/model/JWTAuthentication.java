package com.mainlevel.monitoring.common.security.model;

import java.util.List;

import org.springframework.security.core.Authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Spring authentication object for JWT.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class JWTAuthentication implements Authentication {

    private static final long serialVersionUID = 1L;

    private String name;

    private List<SimpleDeserializableGrantedAuthority> authorities;

    private String credentials;

    private String details;

    private String principal;

    private boolean authenticated;

}
