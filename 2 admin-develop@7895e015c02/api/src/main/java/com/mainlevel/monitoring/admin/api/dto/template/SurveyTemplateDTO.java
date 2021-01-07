package com.mainlevel.monitoring.admin.api.dto.template;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.mainlevel.monitoring.admin.api.dto.language.LanguageDTO;
import com.mainlevel.monitoring.admin.api.dto.unit.UnitDTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding information about a survey.
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SurveyTemplateDTO {

    @ApiModelProperty("The unique identifier of survey.")
    private String identifier;

    @ApiModelProperty("Name of survey.")
    @NotNull(message = "notnull")
    private String surveyName;

    @ApiModelProperty("The survey description.")
    private String description;

    @ApiModelProperty("Target version of survey template.")
    @NotNull(message = "notnull")
    private TemplateDTO surveyTemplate;

    @ApiModelProperty("The version of survey.")
    private Long version;

    @ApiModelProperty("Creatation date.")
    private Date createdDate;

    @ApiModelProperty("Lasta modification made at the this date.")
    private Date lastModifiedDate;

    @ApiModelProperty("The identifier of user who made the last update.")
    private Long updatedById;

    @ApiModelProperty("Full name of user who made the last update.")
    private String updatedBy;

    @ApiModelProperty("Related organizational unit.")
    private UnitDTO unit;

    @ApiModelProperty("Language of survey.")
    private LanguageDTO language;

    @ApiModelProperty("Current status of survey.")
    private String status;

}
