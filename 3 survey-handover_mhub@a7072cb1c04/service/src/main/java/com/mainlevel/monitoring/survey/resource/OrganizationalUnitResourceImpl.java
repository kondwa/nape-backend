/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.resource;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.common.service.CollectionConversionService;
import com.mainlevel.monitoring.survey.api.dto.OrganizationalUnitDTO;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserDTO;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserListDTO;
import com.mainlevel.monitoring.survey.api.resource.OrganizationalUnitResource;
import com.mainlevel.monitoring.survey.database.node.OrganizationalUnit;
import com.mainlevel.monitoring.survey.database.node.User;
import com.mainlevel.monitoring.survey.service.OrganizationalUnitService;
import com.mainlevel.monitoring.survey.service.UserService;

/**
 * Default implementation of {@link OrganizationalUnitResource}.
 */
@RestController
@Secured(ROLE_USER)
public class OrganizationalUnitResourceImpl implements OrganizationalUnitResource {

    @Autowired
    private OrganizationalUnitService organizationalUnitService;

    @Autowired
    private UserService userService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Override
    public ResponseEntity<OrganizationalUnitDTO> getUnit(@RequestParam(name = "name", required = false) String name,
        @RequestParam(name = "foreignId", required = false) String foreignId) {

        OrganizationalUnit unit;
        if (foreignId != null && !foreignId.isEmpty()) {
            unit = organizationalUnitService.loadUnitByForeignId(foreignId);
        } else {
            unit = organizationalUnitService.loadUnitByName(name);
        }

        OrganizationalUnitDTO unitDTO = conversionService.convert(unit, OrganizationalUnitDTO.class);

        return ResponseEntity.ok(unitDTO);
    }

    @Override
    public ResponseEntity<SurveyUserListDTO> getUsersForUnit(@PathVariable("foreignId") String foreignId) {

        List<User> users = userService.getUsersForUnit(foreignId);

        List<SurveyUserDTO> userDTOs = collectionConversionService.convert(users, SurveyUserDTO.class);
        SurveyUserListDTO result = SurveyUserListDTO.builder().users(userDTOs).build();

        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<OrganizationalUnitDTO> createUnit(@RequestBody OrganizationalUnitDTO body) {

        OrganizationalUnit unit = conversionService.convert(body, OrganizationalUnit.class);

        unit = organizationalUnitService.save(unit);

        OrganizationalUnitDTO unitDTO = conversionService.convert(unit, OrganizationalUnitDTO.class);

        return ResponseEntity.ok(unitDTO);
    }

    @Override
    public ResponseEntity<OrganizationalUnitDTO> updateUnit(@PathVariable(name = "foreignId") String foreignId,
        @RequestBody OrganizationalUnitDTO body) {

        OrganizationalUnit unit = conversionService.convert(body, OrganizationalUnit.class);

        unit = organizationalUnitService.save(unit);

        OrganizationalUnitDTO unitDTO = conversionService.convert(unit, OrganizationalUnitDTO.class);

        return ResponseEntity.ok(unitDTO);
    }

    @Override
    public ResponseEntity<OrganizationalUnitDTO> deleteUnit(@PathVariable(name = "foreignId") String foreignId) {
        OrganizationalUnit unit = organizationalUnitService.deleteByForeignId(foreignId);

        OrganizationalUnitDTO unitDTO = conversionService.convert(unit, OrganizationalUnitDTO.class);

        return ResponseEntity.ok(unitDTO);
    }

    @Override
    public ResponseEntity<SurveyUserListDTO> setUsersForUnit(@RequestBody SurveyUserListDTO body,
        @PathVariable(name = "foreignId") String foreignId) {

        List<User> users = collectionConversionService.convert(body.getUsers(), User.class);

        users = userService.setUsersForUnit(foreignId, users);

        List<SurveyUserDTO> userDTOs = collectionConversionService.convert(users, SurveyUserDTO.class);
        SurveyUserListDTO result = SurveyUserListDTO.builder().users(userDTOs).build();

        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Void> deleteUserParticipation(@PathVariable(name = "foreignId") String foreignId, @PathVariable("gid") Long graphId) {

        userService.deleteUserUnitParticipation(foreignId, graphId);

        return ResponseEntity.ok().build();
    }

}
