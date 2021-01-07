/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */
package com.mainlevel.monitoring.survey.service.impl;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainlevel.monitoring.common.service.AuthenticationAccessService;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserType;
import com.mainlevel.monitoring.survey.database.node.User;
import com.mainlevel.monitoring.survey.database.repository.UserRepository;
import com.mainlevel.monitoring.survey.service.UserService;

/**
 * Default implementation of {@link UserService}.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final String ANONYMOUS_MAIL = "anonymous@mainlevel.de";

    private static final String ANONYMOUS_NAME = "Anonymous User";

    @Autowired
    private AuthenticationAccessService authenticationAccessService;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(propagation = REQUIRED, readOnly = true)
    public User getCurrentUser() {

        String username = authenticationAccessService.getCurrentUsername();

        if (username == null || username.isEmpty()) {
            throw new IllegalStateException("Username of current user must not be null!");
        }

        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional(propagation = REQUIRED, readOnly = false)
    public User getAnonymousUser() {
        List<User> anonymousUsers = userRepository.findByType(SurveyUserType.ANONYMOUS);

        User anonymousUser;
        if (anonymousUsers.isEmpty()) {
            anonymousUser = User.builder().name(ANONYMOUS_NAME).username(ANONYMOUS_MAIL).type(SurveyUserType.ANONYMOUS).build();
            anonymousUser = this.saveUser(anonymousUser);
        } else {
            anonymousUser = anonymousUsers.get(0);
        }

        return anonymousUser;
    }

    @Override
    @Transactional(propagation = REQUIRED, readOnly = false)
    public User saveUser(User user) {
        return userRepository.save(user, 1);
    }

    @Override
    @Transactional(propagation = REQUIRED, readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional(propagation = REQUIRED, readOnly = true)
    public List<User> getUsersForUnit(String unitForeignId) {
        return userRepository.findByUnitForeignId(unitForeignId);
    }

    @Override
    @Transactional(propagation = REQUIRED, readOnly = false)
    public List<User> setUsersForUnit(String unitForeignId, List<User> users) {
        return userRepository.createUserUnitRelation(unitForeignId, users);
    }

    @Override
    @Transactional(propagation = REQUIRED, readOnly = false)
    public void deleteUserUnitParticipation(String foreignId, Long graphId) {
        userRepository.deleteUserUnitParticipation(foreignId, graphId);
    }
}
