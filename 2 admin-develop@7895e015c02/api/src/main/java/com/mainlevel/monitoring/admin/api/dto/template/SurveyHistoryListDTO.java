package com.mainlevel.monitoring.admin.api.dto.template;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * DTO holding a list of survey history entries.
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@AllArgsConstructor
public class SurveyHistoryListDTO {

    @ApiModelProperty("List of history entities.")
    private List<SurveyHistoryDTO> historyList;
}
