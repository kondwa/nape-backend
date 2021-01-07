/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */
package com.mainlevel.monitoring.survey.database.node;

import static org.neo4j.ogm.annotation.Relationship.OUTGOING;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.DateLong;

import com.mainlevel.monitoring.survey.api.dto.survey.SurveyVisibilityType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Neo4j node that represents a web link for access to a survey or reporting period.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false, exclude = {"gid", "user", "survey", "reportingPeriod"})
@ToString(callSuper = false, exclude = {"gid", "user", "survey", "reportingPeriod"})
@NodeEntity
public class Link {

    @GraphId
    private Long gid;

    @Property
    @NotNull
    @Index(unique = true)
    private String token;

    @Property
    private String owner;

    @Property
    @NotNull
    private String suffix;

    @Property
    private boolean active;

    @Property
    @NotNull
    @DateLong
    private Date expiry;

    @Property
    @NotNull
    private SurveyVisibilityType visibility;

    @Relationship(type = "forUser", direction = OUTGOING)
    private User user;

    @Relationship(type = "forSurvey", direction = OUTGOING)
    private Survey survey;

    @Relationship(type = "forPeriod", direction = OUTGOING)
    private ReportingPeriod reportingPeriod;

}
