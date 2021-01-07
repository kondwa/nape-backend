package com.mainlevel.monitoring.admin.repository.entity;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * An object representation of the SurveyEditor.Question JSON property.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class ValidatorEntity {

    private String text;

    private String columnName;

    private String validatorType;

    private Map<String, Object> props;

}
