/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.database.node;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserParticipationRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Neo4j relationship between person and organizational unit.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false, exclude = {"user", "survey"})
@ToString(callSuper = false, exclude = {"user", "survey"})
@RelationshipEntity(type = "participates")
public class UserSurveyParticipation {

    @GraphId
    private Long gid;

    @Property
    private SurveyUserParticipationRole role;

    @StartNode
    private User user;

    @EndNode
    private Survey survey;

}
