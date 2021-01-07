package com.mainlevel.monitoring.admin.api.dto.template;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding about a single survey page.
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO {

    @ApiModelProperty("Page name, should be unique by survey.")
    @NotNull(message = "notnull")
    private String name;

    @ApiModelProperty("Title of page")
    private String title;

    @ApiModelProperty("Flag that determinates, is page visible or not.")
    private Boolean visible;

    @ApiModelProperty("Description of the page")
    private String description;

    @ApiModelProperty("List of question in page.")
    private List<QuestionDTO> questions;

}
