/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.database.node.question;

import static org.neo4j.ogm.annotation.Relationship.OUTGOING;

import java.util.List;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.mainlevel.monitoring.survey.database.node.AnswerOption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Neo4j entity for a matrix question.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NodeEntity
public class MatrixQuestion extends Question {

    @Relationship(type = "hasOption", direction = OUTGOING)
    private List<AnswerOption> options;

    @Relationship(type = "hasRow", direction = OUTGOING)
    private List<MatrixQuestionRow> rows;

}
