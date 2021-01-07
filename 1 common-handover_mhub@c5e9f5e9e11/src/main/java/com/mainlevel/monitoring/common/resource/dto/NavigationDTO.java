package com.mainlevel.monitoring.common.resource.dto;

import org.springframework.hateoas.ResourceSupport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * DTO holding the top level navigation links.
 */
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class NavigationDTO extends ResourceSupport {

}
