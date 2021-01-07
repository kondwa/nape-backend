package com.mainlevel.monitoring.admin.repository.entity;

import java.util.List;

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
public class ValueItemEntity {

    private Object value;

    private String text;

    private String name;

    private String infoText;

    private String type;

    private List<ChoiceEntity> choices;

    private String suffix;

    private Boolean hasOther;

    private String choiceExclusive;

    private String predefinedListType;

    private String predefinedList;

}
