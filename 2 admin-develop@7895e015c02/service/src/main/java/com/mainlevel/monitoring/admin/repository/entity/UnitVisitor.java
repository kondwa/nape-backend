/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.repository.entity;

/**
 * Visitor for organizational unit structure.
 */
public interface UnitVisitor {

    /**
     * Visit the generic unit.
     *
     * @param unit the project
     */
    void visit(UnitEntity unit);

}
