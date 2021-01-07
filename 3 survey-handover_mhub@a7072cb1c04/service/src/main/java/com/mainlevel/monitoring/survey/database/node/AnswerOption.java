/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.database.node;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import com.mainlevel.monitoring.survey.api.dto.survey.question.AnswerOptionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Selectable anwser option of a question.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false, exclude = {"gid"})
@ToString(callSuper = false)
@NodeEntity
public class AnswerOption {

    @GraphId
    private Long gid;

    @Property
    private Integer index;

    @Property
    private String name;

    @Property
    private String description;

    @Property
    private AnswerOptionType type;

    @Property
    private Object value;

}
