/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.api.dto.template;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding a single value.
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ValueItemDTO {

    private Object value;

    private String text;

    private String name;

    private String infoText;

    private String type;

    private List<ChoiceDTO> choices;

    private String suffix;

    private Boolean hasOther;

    private String choiceExclusive;

    private String predefinedListType;

    private String predefinedList;

}
