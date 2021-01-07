/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.impl;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.admin.api.dto.unit.UnitDTO;
import com.mainlevel.monitoring.admin.api.dto.unit.UnitListDTO;
import com.mainlevel.monitoring.admin.api.dto.user.UserDTO;
import com.mainlevel.monitoring.admin.api.dto.user.UserListDTO;
import com.mainlevel.monitoring.admin.api.resource.UnitResource;
import com.mainlevel.monitoring.business.custom.resource.CustomUnitResource;
import com.mainlevel.monitoring.business.custom.resource.dto.unit.CustomUnitDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.unit.CustomUnitListDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.user.CustomUnitUserDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.user.CustomUnitUserListDTO;
import com.mainlevel.monitoring.common.service.CollectionConversionService;
import com.mainlevel.monitoring.survey.api.dto.OrganizationalUnitDTO;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserDTO;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserListDTO;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserParticipationDTO;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserUnitParticipationDTO;

/**
 * Default implementation of {@link CustomUnitResource}.
 */
@RestController
@Secured(ROLE_USER)
public class CustomUnitResourceImpl implements CustomUnitResource {

    @Autowired
    private UnitResource unitResource;

    @Autowired
    private com.mainlevel.monitoring.survey.api.resource.OrganizationalUnitResource surveyUnitResource;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Override
    public ResponseEntity<CustomUnitListDTO> getUnits(@RequestParam("type") String type) {

        ResponseEntity<UnitListDTO> response = unitResource.getUnits(type);

        List<CustomUnitDTO> units = collectionConversionService.convert(response.getBody().getUnits(), CustomUnitDTO.class);

        CustomUnitListDTO dto = CustomUnitListDTO.builder().units(units).build();

        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<CustomUnitDTO> getRoot() {

        ResponseEntity<UnitDTO> response = unitResource.getRoot();

        CustomUnitDTO dto = conversionService.convert(response.getBody(), CustomUnitDTO.class);

        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<CustomUnitDTO> create(@RequestBody CustomUnitDTO body) {

        UnitDTO adminUnit = conversionService.convert(body, UnitDTO.class);

        adminUnit = unitResource.create(adminUnit).getBody();

        if (adminUnit == null) {
            throw new IllegalStateException("Cannot create survey unit for not existing admin unit.");
        }

        OrganizationalUnitDTO surveyUnit = convert(adminUnit);

        surveyUnit = surveyUnitResource.createUnit(surveyUnit).getBody();

        CustomUnitDTO dto = conversionService.convert(adminUnit, CustomUnitDTO.class);

        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<CustomUnitDTO> update(@PathVariable("key") String key, @RequestBody CustomUnitDTO body) {

        UnitDTO adminUnit = conversionService.convert(body, UnitDTO.class);
        adminUnit = unitResource.update(key, adminUnit).getBody();

        if (adminUnit == null) {
            throw new IllegalStateException("Cannot create survey unit for not existing admin unit.");
        }

        OrganizationalUnitDTO surveyUnit = convert(adminUnit);

        surveyUnit = surveyUnitResource.updateUnit(key, surveyUnit).getBody();

        CustomUnitDTO dto = conversionService.convert(adminUnit, CustomUnitDTO.class);

        return ResponseEntity.ok(dto);
    }

    /**
     * Convert the admin unit to the survey unit, including parent relations.
     *
     * @param adminUnit the admin unit
     * @return the converted survey unit
     */
    private OrganizationalUnitDTO convert(UnitDTO adminUnit) {
        UnitDTO parentAdminUnit = unitResource.getUnitById(adminUnit.getParentId()).getBody();

        OrganizationalUnitDTO surveyUnit = conversionService.convert(adminUnit, OrganizationalUnitDTO.class);
        if (parentAdminUnit != null) {
            OrganizationalUnitDTO parentSurveyUnit = surveyUnitResource.getUnit(null, parentAdminUnit.getForeignId()).getBody();
            if (parentSurveyUnit == null) {
                parentSurveyUnit = conversionService.convert(adminUnit, OrganizationalUnitDTO.class);
            }
            surveyUnit.setParent(parentSurveyUnit);
        }
        return surveyUnit;
    }

    @Override
    public ResponseEntity<CustomUnitDTO> delete(@PathVariable("key") String key) {
        UnitDTO unit = unitResource.delete(key).getBody();

        surveyUnitResource.deleteUnit(key);

        CustomUnitDTO dto = conversionService.convert(unit, CustomUnitDTO.class);

        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<CustomUnitUserListDTO> getUsersForUnit(@PathVariable("key") String key) {

        ResponseEntity<SurveyUserListDTO> response = surveyUnitResource.getUsersForUnit(key);

        List<CustomUnitUserDTO> users = convertUsers(key, response.getBody().getUsers());
        CustomUnitUserListDTO userList = CustomUnitUserListDTO.builder().users(users).build();

        return ResponseEntity.ok(userList);
    }

    @Override
    public ResponseEntity<CustomUnitUserListDTO> setUsersForUnit(@RequestBody CustomUnitUserListDTO body, @PathVariable("key") String key) {

        List<UserDTO> users = collectionConversionService.convert(body.getUsers(), UserDTO.class);
        UserListDTO userList = UserListDTO.builder().users(users).build();
        userList = unitResource.setUsersForUnit(userList, key).getBody();

        List<SurveyUserDTO> surveyUsers = collectionConversionService.convert(body.getUsers(), SurveyUserDTO.class);
        SurveyUserListDTO surveyUserList = SurveyUserListDTO.builder().users(surveyUsers).build();
        surveyUserList = surveyUnitResource.setUsersForUnit(surveyUserList, key).getBody();

        List<CustomUnitUserDTO> customUsers = convertUsers(key, surveyUserList.getUsers());

        CustomUnitUserListDTO customUserList = CustomUnitUserListDTO.builder().users(customUsers).build();

        return ResponseEntity.ok(customUserList);
    }

    /**
     * Convert the list of survey users to custom users.
     *
     * @param body the survey user list
     * @return the converted users
     */
    private List<CustomUnitUserDTO> convertUsers(String key, List<SurveyUserDTO> surveyUsers) {

        List<CustomUnitUserDTO> users = new ArrayList<>();

        for (SurveyUserDTO surveyUser : surveyUsers) {

            CustomUnitUserDTO customUser = conversionService.convert(surveyUser, CustomUnitUserDTO.class);

            for (SurveyUserParticipationDTO participation : surveyUser.getParticipations()) {

                if (participation instanceof SurveyUserUnitParticipationDTO) {

                    if (((SurveyUserUnitParticipationDTO) participation).getUnit().getForeignId().equals(key)) {
                        customUser.getRoles().add(participation.getRole().name());
                    }
                }
            }

            users.add(customUser);
        }

        return users;
    }

    @Override
    @Deprecated
    public ResponseEntity<Void> deleteUserParticipation(@PathVariable("key") String key, @PathVariable("gid") Long graphId) {

        // TODO: Remove Me

        surveyUnitResource.deleteUserParticipation(key, graphId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteUserParticipation(@PathVariable("key") String key, @RequestParam("username") String username,
        @RequestParam("gid") Long graphId) {

        unitResource.deleteUserParticipation(key, username);
        surveyUnitResource.deleteUserParticipation(key, graphId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CustomUnitDTO> getUnit(@PathVariable("key") String key) {
        UnitDTO unit = unitResource.getUnit(key).getBody();
        CustomUnitDTO customUnit = conversionService.convert(unit, CustomUnitDTO.class);
        return ResponseEntity.ok(customUnit);
    }

}
