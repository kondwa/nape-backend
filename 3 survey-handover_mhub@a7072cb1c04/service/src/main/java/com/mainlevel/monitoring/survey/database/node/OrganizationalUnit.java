/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.database.node;

import static org.neo4j.ogm.annotation.Relationship.OUTGOING;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Abstract base class for all organizational units.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {"gid", "parent"})
@ToString(callSuper = false)
@NodeEntity
public class OrganizationalUnit {

    @GraphId
    private Long gid;

    @Property
    private String name;

    @Property
    @Index(unique = true)
    private String foreignId;

    @Property
    private String refId;

    @Property
    private String type;

    @Relationship(type = "inUnit", direction = OUTGOING)
    private OrganizationalUnit parent;

}
