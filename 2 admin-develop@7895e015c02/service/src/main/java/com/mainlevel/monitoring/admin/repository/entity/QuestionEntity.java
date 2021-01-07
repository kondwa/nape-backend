package com.mainlevel.monitoring.admin.repository.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * An object representation of the SurveyEditor.QuestionEntity JSON.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class QuestionEntity {

    private Integer indent;

    private String name;

    private String title;

    private List<ValueItemEntity> choices;

    private String description;

    private Boolean isRequired;

    private List<ValidatorEntity> validators;

    private String suffix;

    private Boolean hasOther;

    private String choiceExclusive;

    private List<TriggerEntity> triggers;

    private List<ValueItemEntity> rows;

    private List<ValueItemEntity> columns;

    private Boolean visible;

    private String predefinedListType;

    private String type;
    private String view;

}
