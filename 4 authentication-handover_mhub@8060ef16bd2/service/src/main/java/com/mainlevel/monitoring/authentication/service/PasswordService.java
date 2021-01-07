package com.mainlevel.monitoring.authentication.service;

/**
 * Service for creating passwords and salts.
 */
public interface PasswordService {

    /**
     * Generate a random salt.
     *
     * @return the salt
     */
    String generateSalt();

    /**
     * Hash a password with the given salt.
     *
     * @param salt the salt
     * @param password the password
     * @return the hashed value
     */
    String hashPassword(String salt, String password);

    /**
     * Verify whether a password applies to all policies.
     *
     * @param passwordToVerify the password
     * @param salt the salt
     * @param hashedPassword the hashed password
     * @return true if valid, false if not
     */
    boolean verifyPassword(String passwordToVerify, String salt, String hashedPassword);

}
