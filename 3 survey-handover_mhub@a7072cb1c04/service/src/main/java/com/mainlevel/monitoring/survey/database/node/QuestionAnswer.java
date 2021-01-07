/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.database.node;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import com.mainlevel.monitoring.survey.database.node.question.Question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Node relationship between answer and question.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {"gid", "answer", "question"})
@ToString(callSuper = false, exclude = {"answer", "question"})
@RelationshipEntity(type = "answers")
public class QuestionAnswer {

    @GraphId
    private Long gid;

    @Property
    private String title;

    @Property
    private Integer order;

    @StartNode
    private Answer answer;

    @EndNode
    private Question question;
}
