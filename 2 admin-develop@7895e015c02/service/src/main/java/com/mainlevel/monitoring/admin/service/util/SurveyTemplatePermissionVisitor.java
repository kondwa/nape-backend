/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.service.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mainlevel.monitoring.admin.repository.entity.UnitEntity;
import com.mainlevel.monitoring.admin.repository.entity.UnitVisitor;
import com.mainlevel.monitoring.admin.repository.entity.SurveyTemplateEntity;

/**
 * Visitor for checking a template against the organizational structure.
 */
public class SurveyTemplatePermissionVisitor implements UnitVisitor {

    private List<SurveyTemplateEntity> allTemplates;

    private final Set<SurveyTemplateEntity> permittedTemplates;

    /**
     * Constructor for SurveyTemplatePermissionVisitor.
     *
     * @param surveyTemplates the list of template to check
     */
    public SurveyTemplatePermissionVisitor(List<SurveyTemplateEntity> surveyTemplates) {
        this.allTemplates = surveyTemplates;
        this.permittedTemplates = new HashSet<>();
    }

    @Override
    public void visit(UnitEntity unit) {

        allTemplates.forEach(t -> {
            if (t.getUnit() == null || unit.equals(t.getUnit())) {
                permittedTemplates.add(t);
            }
        });

        allTemplates.removeAll(permittedTemplates);
    }

    /**
     * Retrieves the list of permitted templates.
     *
     * @return the permitted templates
     */
    public Set<SurveyTemplateEntity> getPermittedTemplates() {
        return permittedTemplates;
    }
}
