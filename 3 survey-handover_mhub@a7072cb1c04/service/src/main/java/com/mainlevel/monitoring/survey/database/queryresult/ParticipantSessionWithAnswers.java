/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.database.queryresult;

import java.util.List;

import org.neo4j.ogm.annotation.Property;
import org.springframework.data.neo4j.annotation.QueryResult;

import com.mainlevel.monitoring.survey.database.node.Answer;
import com.mainlevel.monitoring.survey.database.node.ParticipantSession;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@QueryResult
public class ParticipantSessionWithAnswers {

    @Property(name = "ps")
    private ParticipantSession participantSession;

    @Property(name = "a")
    private List<Answer> answers;

    @Property(name = "nodes(p)")
    private Object nodesA;

    @Property(name = "rels(p)")
    private Object relsA;

    @Property(name = "nodes(op)")
    private Object nodesB;

    @Property(name = "rels(po)")
    private Object relsB;

}
