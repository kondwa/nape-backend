/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.database.node.question;

import static org.neo4j.ogm.annotation.Relationship.OUTGOING;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;

import com.mainlevel.monitoring.survey.api.dto.survey.trigger.SurveyTriggerCompareType;
import com.mainlevel.monitoring.survey.api.dto.survey.trigger.SurveyTriggerType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Neo4j node for a question trigger.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {"gid", "targetQuestion"})
@ToString(callSuper = false)
@NodeEntity
public class QuestionTrigger {

    @GraphId
    private Long gid;

    @Property
    private Integer index;

    @Property
    private Integer optionIndex;

    @Transient
    private Integer targetQuestionIndex;

    @Property
    private SurveyTriggerType actionType;

    @Property
    private SurveyTriggerCompareType compareType;

    @Relationship(type = "triggersQuestion", direction = OUTGOING)
    private Question targetQuestion;
}
