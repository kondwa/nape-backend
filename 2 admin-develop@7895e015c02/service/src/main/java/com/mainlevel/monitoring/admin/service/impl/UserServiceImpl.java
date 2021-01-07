/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */
package com.mainlevel.monitoring.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainlevel.monitoring.admin.repository.UserRepository;
import com.mainlevel.monitoring.admin.repository.entity.UserEntity;
import com.mainlevel.monitoring.admin.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link UserService}.
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity save(UserEntity user) {

        UserEntity entity = userRepository.findByUserName(user.getUserName());
        if (entity == null) {
            entity = user;

            log.info("Create new user {} in database.", entity.getUserName());

        } else {
            entity.setName(user.getName());
            entity.setUserName(user.getUserName());

            log.info("Update existing user {} in database.", entity.getUserName());
        }

        return userRepository.save(user);
    }

    @Override
    public UserEntity findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

}
