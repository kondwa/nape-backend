package com.mainlevel.monitoring.authentication.service;

import java.util.List;

import com.mainlevel.monitoring.authentication.api.dto.UserDTO;
import com.mainlevel.monitoring.authentication.repository.entity.UserEntity;

/**
 * Interface for maintaining users..
 */
public interface UserService {

    /**
     * Create a new user in the database.
     *
     * @param user the user to save
     * @return the saved user
     */
    UserDTO createUser(UserDTO user);

    /**
     * Get all the users.
     *
     * @return the user list
     */
    List<UserDTO> getUsers();

    /**
     * Find a user by his id.
     *
     * @param id the userid
     * @return User if available, null otherwise.
     */
    UserDTO getUser(Long id);

    /**
     * Find a user by his username.
     *
     * @param username the username
     * @return User if available, null otherwise.
     */
    UserDTO getUser(String username);

    /**
     * Deletes a user.
     *
     * @param userid of the user to be deleted.
     */
    void deleteUser(long userid);

    /**
     * Deletes a user.
     *
     * @param user User to be deleted.
     */
    void deleteUser(UserEntity user);

    /**
     * Update the given user.
     *
     * @param userid the userid
     * @param user User to be updated.
     * @return the updated user
     */
    UserDTO updateUser(long userid, UserDTO user);
}
