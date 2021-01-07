package com.mainlevel.monitoring.survey.api.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding a list of possible next transitions.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class SurveyStatusTransitionListDTO {

    @ApiModelProperty(value = "The list of available transition.")
    private List<SurveyStatusTransitionDTO> transitions;

}
