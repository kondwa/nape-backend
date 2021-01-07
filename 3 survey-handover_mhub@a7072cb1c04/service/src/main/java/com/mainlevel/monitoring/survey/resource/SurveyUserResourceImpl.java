/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.resource;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserDTO;
import com.mainlevel.monitoring.survey.api.resource.SurveyUserResource;
import com.mainlevel.monitoring.survey.database.node.User;
import com.mainlevel.monitoring.survey.service.UserService;

/**
 * Default implementation of {@link SurveyUserResource}.
 */
@RestController
@Secured(ROLE_USER)
public class SurveyUserResourceImpl implements SurveyUserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private ConversionService conversionService;

    @Override
    public ResponseEntity<SurveyUserDTO> findUserByUsername(@RequestParam("username") String username) {

        User user = userService.getUserByUsername(username);
        SurveyUserDTO result = conversionService.convert(user, SurveyUserDTO.class);

        return ResponseEntity.ok(result);

    }

    @Override
    public ResponseEntity<SurveyUserDTO> createUser(@RequestBody SurveyUserDTO userDTO) {

        User user = conversionService.convert(userDTO, User.class);
        user = userService.saveUser(user);

        SurveyUserDTO result = conversionService.convert(user, SurveyUserDTO.class);

        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<SurveyUserDTO> updateUser(@PathVariable("identifier") Long userGid, @RequestBody SurveyUserDTO userDTO) {

        User user = conversionService.convert(userDTO, User.class);
        user = userService.saveUser(user);

        SurveyUserDTO result = conversionService.convert(user, SurveyUserDTO.class);

        return ResponseEntity.ok(result);
    }

}
