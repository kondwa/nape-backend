/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.database.node;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import com.mainlevel.monitoring.survey.api.dto.ClientType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Neo4j node that represents an application client.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {"gid"})
@ToString(callSuper = false)
@NodeEntity
public class Client {

    @GraphId
    private Long gid;

    @Property
    private String address;

    @Property
    private String browser;

    @Property
    private String os;

    @Property
    private ClientType type;
}
