package com.mainlevel.monitoring.admin.api.dto.template;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * DTO holding a list of survey template DTOs.
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@AllArgsConstructor
public class SurveyTemplateListDTO {

    @ApiModelProperty("Collection of surveys")
    private List<SurveyTemplateDTO> surveys;
}
