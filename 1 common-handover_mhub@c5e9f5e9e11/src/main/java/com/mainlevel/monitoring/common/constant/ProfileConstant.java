package com.mainlevel.monitoring.common.constant;

/**
 * Spring profiles
 */
public interface ProfileConstant {

    /**
     * Inverse.
     */
    String NOT = "!";

    /**
     * Do not use security, if set
     */
    String WITHOUT_SECURITY = "com.mainlevel.monitoring.security";

    /**
     * Use security, if set (default activated)
     */
    String WITH_SECURITY = NOT + WITHOUT_SECURITY;
}
