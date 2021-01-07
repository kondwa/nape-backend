package com.mainlevel.monitoring.survey.api.dto;

import org.springframework.hateoas.ResourceSupport;

import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodStatus;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO representing a survey workflow transition.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class SurveyStatusTransitionDTO extends ResourceSupport {

    @ApiModelProperty(value = "The next transition status.")
    private ReportingPeriodStatus nextStatus;

}
