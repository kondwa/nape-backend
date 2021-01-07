package com.mainlevel.monitoring.common.security.model;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Deserializable granted authority.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class SimpleDeserializableGrantedAuthority implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    private String authority;

}
