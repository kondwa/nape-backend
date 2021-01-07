package com.mainlevel.monitoring.common.constant;

/**
 * ErrorCodes used in the Application.
 * Schema: SERVICE.RESOURCE.ERROR-NUMBER
 * SERVICES:
 * 0 = GENERAL
 * 1 = AUTHENTICATION
 * 2 = SURVEY
 * 3 = SURVEY-ADMIN
 */
public interface ErrorCodes {

    // FIXME: Remove!

    // GENERAL
    String UNKNOWN_ERROR = "0.0.0";

    String METHODARGUMENT_NOT_VALID = "0.0.1";

    String HTTP_MESSAGE_NOT_READABLE = "0.0.2";

    String HTTP_MEDIA_TYPE_NOT_SUPPORTED = "0.0.3";

    String RESOURCE_NOT_FOUND = "0.0.4";

    String NONCE_EXPIRED = "0.0.5";

    String METHODARGUMENT_INVALID_TYPE = "0.0.6";

    String ACCESS_DENIED = "0.0.7";

    // AUTHENTICATION - UserResource
    String USER_ALREADY_EXISTS = "1.0.0";

    String USER_NOT_FOUND = "1.0.1";

    String PASSWORD_GENERATION_FAILED = "1.0.2";

    // SURVEY
    String SURVEY_RESULT_ILLEGAL_MODIFICATION = "2.0.0";

    String TARGET_ENTITY_NOT_FOUND = "2.0.1";

    String WRONG_WORKFLOW_STATUS = "2.0.2";

    String PARTICIPANT_SESSION_IS_DUPLICATED = "2.0.3";

    String MISSING_SAP_PARAMETERS = "2.0.4";

    String PROJECT_NOT_FOUND = "2.0.5";

    String UNABLE_TO_EXTEND_LOCK_EXCEPTION = "2.0.6";

    String ENTITY_LOCK_EXCEPTION = "2.0.7";

    String CSV_GENERATION_EXCEPTION = "2.0.8";

    // ADMIN
    String SURVEY_NOT_FOUND = "3.0.0";

    String UNIT_NOT_FOUND = "3.0.1";

    String INDICATOR_NOT_FOUND = "3.0.2";

}
