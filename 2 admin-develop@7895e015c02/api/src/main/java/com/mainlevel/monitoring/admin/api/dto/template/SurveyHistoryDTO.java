package com.mainlevel.monitoring.admin.api.dto.template;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * DTO holding information about a survey histrory entry.
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@AllArgsConstructor
public class SurveyHistoryDTO {

    @ApiModelProperty("The unique id of survey")
    private String identifier;

    @ApiModelProperty("The version of survey")
    private Long version;

    @ApiModelProperty("The survey title")
    private String title;

    @ApiModelProperty("Date of last update")
    private Date updated;

    @ApiModelProperty("Full name of user who made last modification.")
    private String updatedBy;

    @ApiModelProperty("The status of survey")
    private String status;

    @ApiModelProperty("Description of survey.")
    private String description;

}
