package com.mainlevel.monitoring.authentication.service.impl;

import static java.util.stream.Collectors.toList;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.mainlevel.monitoring.authentication.api.dto.AuthenticationType;
import com.mainlevel.monitoring.authentication.api.dto.UserDTO;
import com.mainlevel.monitoring.authentication.api.exception.UserAlreadyExistsException;
import com.mainlevel.monitoring.authentication.api.exception.UserNotFoundException;
import com.mainlevel.monitoring.authentication.repository.UserRepository;
import com.mainlevel.monitoring.authentication.repository.entity.RoleEntity;
import com.mainlevel.monitoring.authentication.repository.entity.UserEntity;
import com.mainlevel.monitoring.authentication.service.PasswordService;
import com.mainlevel.monitoring.authentication.service.RoleService;
import com.mainlevel.monitoring.authentication.service.UserService;
import com.mainlevel.monitoring.common.service.CollectionConversionService;

import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for user handling.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Autowired
    private PasswordService passwordService;

    @Override
    @Transactional(propagation = REQUIRED)
    public List<UserDTO> getUsers() {

        List<UserEntity> users = userRepository.findAll();
        List<UserDTO> result = collectionConversionService.convert(users, UserDTO.class);

        return result;
    }

    @Override
    @Transactional(propagation = REQUIRED)
    public UserDTO createUser(final UserDTO user) {

        if (user.getAuthentication() == AuthenticationType.LOGIN) {
            Assert.hasLength(user.getPassword(), "Password must be provided for new users with login.");
        }

        if (userRepository.findByUsername(user.getUsername()) != null) {
            log.warn("Not created a user for {}. User ist alreading existing.", user.getUsername());
            throw new UserAlreadyExistsException("User already exists");
        }

        final UserEntity userEntity = conversionService.convert(user, UserEntity.class);

        final List<RoleEntity> roleEntities =
            userEntity.getRoles().stream().map(r -> roleService.findOrCreateRoleEntity(r.getName())).collect(toList());

        userEntity.setId(null);
        if (user.getAuthentication() == AuthenticationType.LOGIN) {
            userEntity.setSalt(passwordService.generateSalt());
            userEntity.setPassword(passwordService.hashPassword(userEntity.getSalt(), userEntity.getPassword()));
        }
        userEntity.setRoles(roleEntities);

        final UserEntity userResultEntity = userRepository.save(userEntity);
        return conversionService.convert(userResultEntity, UserDTO.class);
    }

    @Override
    @Transactional(propagation = SUPPORTS, readOnly = true)
    public UserDTO getUser(final Long id) {

        final UserEntity userEntity = userRepository.findOne(id);

        if (userEntity == null) {
            throw new UserNotFoundException("No user found for id: " + id);
        }

        return conversionService.convert(userEntity, UserDTO.class);
    }

    @Override
    @Transactional(propagation = SUPPORTS, readOnly = true)
    public UserDTO getUser(final String username) {
        final UserEntity userEntity = userRepository.findByUsername(username);

        return conversionService.convert(userEntity, UserDTO.class);
    }

    @Override
    @Transactional(propagation = REQUIRED)
    public void deleteUser(final long userid) {
        log.info("Delete User with id {} in Database.", userid);
        userRepository.delete(userid);
    }

    @Override
    @Transactional(propagation = REQUIRED)
    public void deleteUser(final UserEntity user) {
        log.info("Delete User in Database: {}", user);

        if (user != null) {
            userRepository.delete(user);
        }
    }

    @Override
    @Transactional(propagation = REQUIRED)
    public UserDTO updateUser(final long userid, final UserDTO user) {

        final UserEntity oldUser = userRepository.findOne(userid);

        if (oldUser == null) {
            throw new UserNotFoundException("No user found for id: " + userid);
        }

        final UserEntity convertedIncomingUser = conversionService.convert(user, UserEntity.class);

        convertedIncomingUser.setId(oldUser.getId());
        convertedIncomingUser.setSalt(oldUser.getSalt());

        if ((user.getPassword() != null) && (user.getPassword().length() > 0)) {
            convertedIncomingUser.setPassword(passwordService.hashPassword(oldUser.getSalt(), user.getPassword()));
        } else {
            convertedIncomingUser.setPassword(oldUser.getPassword());
        }

        final UserEntity savedUserEntity = userRepository.save(convertedIncomingUser);
        final UserDTO result = conversionService.convert(savedUserEntity, UserDTO.class);

        return result;
    }

}