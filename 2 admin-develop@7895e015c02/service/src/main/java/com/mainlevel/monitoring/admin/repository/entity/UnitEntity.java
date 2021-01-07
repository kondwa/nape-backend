/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.repository.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Abstract base class for organizational units.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"parent", "children"})
@ToString(exclude = {"parent", "children"})
@Document(collection = "unit")
public class UnitEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String foreignId;

    private boolean root;

    private boolean visible;

    private String name;

    private String type;

    private List<String> dashboards;

    private List<UnitLinkEntity> links;

    @DBRef
    private UnitEntity parent;

    @Transient
    private List<UnitEntity> children;

    /**
     * Accept a visitor for traversing.
     *
     * @param visitor the visitor to accept
     */
    public void accept(UnitVisitor visitor) {

        visitor.visit(this);

        if (children != null) {
            children.forEach(child -> child.accept(visitor));
        }
    }

}
