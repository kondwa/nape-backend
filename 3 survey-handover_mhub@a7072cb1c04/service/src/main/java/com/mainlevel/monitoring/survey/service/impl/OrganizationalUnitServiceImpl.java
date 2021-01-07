/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainlevel.monitoring.survey.database.node.OrganizationalUnit;
import com.mainlevel.monitoring.survey.database.repository.OrganizationalUnitRepository;
import com.mainlevel.monitoring.survey.service.OrganizationalUnitService;

/**
 * Default implementation of {@link OrganizationalUnitService}.
 */
@Service
public class OrganizationalUnitServiceImpl implements OrganizationalUnitService {

    @Autowired
    private OrganizationalUnitRepository organizationalUnitRepository;

    @Override
    public OrganizationalUnit loadUnitByName(String name) {
        return organizationalUnitRepository.findByName(name);
    }

    @Override
    public OrganizationalUnit loadUnitByForeignId(String foreignId) {
        return organizationalUnitRepository.findByForeignId(foreignId);
    }

    @Override
    public OrganizationalUnit save(OrganizationalUnit unit) {
        OrganizationalUnit dbUnit = organizationalUnitRepository.findByForeignId(unit.getForeignId());

        if (dbUnit == null) {
            dbUnit = unit;
        } else {
            dbUnit.setName(unit.getName());
            dbUnit.setRefId(unit.getRefId());
            dbUnit.setType(unit.getType());

            if (unit.getParent() != null) {
                dbUnit.setParent(unit.getParent());
            }
        }

        if (dbUnit.getParent() != null) {
            return organizationalUnitRepository.save(dbUnit);
        }

        return organizationalUnitRepository.save(dbUnit, 0);
    }

    @Override
    public OrganizationalUnit deleteByForeignId(String foreignId) {
        OrganizationalUnit unit = organizationalUnitRepository.findByForeignId(foreignId);
        organizationalUnitRepository.delete(unit);
        return unit;
    }

}
