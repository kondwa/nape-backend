/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.database.node;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Neo4j entity that represents a person in the application.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false, exclude = {"gid"})
@ToString(callSuper = false)
@NodeEntity
public class User {

    @GraphId
    private Long gid;

    @Property
    @NotNull
    @Index(unique = true)
    private String username;

    @Property
    private String name;

    @Property
    private SurveyUserType type;

    @Relationship(type = "participates")
    private List<UserUnitParticipation> unitParticipations;

    @Relationship(type = "participates")
    private List<UserSurveyParticipation> surveyParticipations;

    @Relationship(type = "participates")
    private List<UserReportingPeriodParticipation> reportingPeriodParticipations;
}
