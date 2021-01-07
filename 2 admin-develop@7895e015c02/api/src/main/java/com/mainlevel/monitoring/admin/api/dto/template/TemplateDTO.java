package com.mainlevel.monitoring.admin.api.dto.template;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding information about a survey template.
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TemplateDTO {

    @ApiModelProperty("Unique id of template")
    private String identifier;

    @ApiModelProperty("Template version.")
    private Long version;

    @ApiModelProperty("Title of survey template.")
    private String title;

    @ApiModelProperty("List of pages")
    private List<PageDTO> pages;

    @ApiModelProperty("The current template updated.")
    private Date updated;

    @ApiModelProperty("The current template updated by user(his/her full name)")
    private String updatedBy;

    @ApiModelProperty("Survey template description.")
    private String description;

}
