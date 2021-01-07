package com.mainlevel.monitoring.admin.repository.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * A node representation of an indicator.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString(callSuper = false)
public class IndicatorEntity {

    private String key;

    private String name;

    private String description;

    private String dataQuery;

    private List<IndicatorVisualizationEntity> visualizations;

    private List<IndicatorEntity> subIndicators;

}
