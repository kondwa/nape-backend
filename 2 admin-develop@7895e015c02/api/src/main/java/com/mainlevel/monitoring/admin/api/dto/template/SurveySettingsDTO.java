package com.mainlevel.monitoring.admin.api.dto.template;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.ResourceSupport;

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
 * DTO holding settings of a survey.
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SurveySettingsDTO extends ResourceSupport {

    @ApiModelProperty("Survey name.")
    @NotNull(message = "notnull")
    private String surveyName;

    @ApiModelProperty("Related organizational unit.")
    private UnitDTO unit;

    @ApiModelProperty("Survey in the language.")
    private LanguageDTO language;

}
