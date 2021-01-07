package com.mainlevel.monitoring.common.constant;

/**
 * Name of the security roles.
 */
@SuppressWarnings("javadoc")
public interface RoleConstant {

    /** Prefix of all roles. */
    String ROLE_PREFIX = "ROLE_";

    /**
     * User may enter the application.
     */
    String ROLE_USER = "ROLE_USER";
    String USER = ROLE_USER.substring(ROLE_PREFIX.length());

    /**
     * Anonymous may enter the application.
     */
    String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
    String ANONYMOUS = ROLE_ANONYMOUS.substring(ROLE_PREFIX.length());

    /**
     * Temporary user may enter the application.
     */
    String ROLE_TEMPORARY = "ROLE_TEMPORARY";
    String TEMPORARY = ROLE_TEMPORARY.substring(ROLE_PREFIX.length());

    /**
     * May view the Templates navigation entry
     */
    String ROLE_TEMPLATE_VIEWER = "ROLE_TEMPLATE_VIEWER";
    String TEMPLATE_VIEWER = ROLE_TEMPLATE_VIEWER.substring(ROLE_PREFIX.length());

    /**
     * May create new survey templates
     */
    String ROLE_TEMPLATE_CREATOR = "ROLE_TEMPLATE_CREATOR";
    String TEMPLATE_CREATOR = ROLE_TEMPLATE_CREATOR.substring(ROLE_PREFIX.length());

    /**
     * May view the Surveys navigation entry
     */
    String ROLE_SURVEY_VIEWER = "ROLE_SURVEY_VIEWER";
    String SURVEY_VIEWER = ROLE_SURVEY_VIEWER.substring(ROLE_PREFIX.length());

    /**
     * May view the Evaluation navigation entry.
     */
    String ROLE_EVALUATION_VIEWER = "ROLE_EVALUATION_VIEWER";
    String EVALUATION_VIEWER = ROLE_EVALUATION_VIEWER.substring(ROLE_PREFIX.length());

    /**
     * May view the Evaluation indicator tab.
     */
    String ROLE_EVALUATION_IND_VIEWER = "ROLE_EVALUATION_IND_VIEWER";
    String EVALUATION_IND_VIEWER = ROLE_EVALUATION_IND_VIEWER.substring(ROLE_PREFIX.length());

    /**
     * May view the Evaluation program tab.
     */
    String ROLE_EVALUATION_PRG_VIEWER = "ROLE_EVALUATION_PRG_VIEWER";
    String EVALUATION_PRG_VIEWER = ROLE_EVALUATION_PRG_VIEWER.substring(ROLE_PREFIX.length());

    /**
     * May view the Evaluation projects tab.
     */
    String ROLE_EVALUATION_PRJ_VIEWER = "ROLE_EVALUATION_PRJ_VIEWER";
    String EVALUATION_PRJ_VIEWER = ROLE_EVALUATION_PRJ_VIEWER.substring(ROLE_PREFIX.length());

    /**
     * May view the Evaluation objectives tab.
     */
    String ROLE_EVALUATION_OBJ_VIEWER = "ROLE_EVALUATION_OBJ_VIEWER";
    String EVALUATION_OBJ_VIEWER = ROLE_EVALUATION_OBJ_VIEWER.substring(ROLE_PREFIX.length());

    /**
     * May view the Indicators navigation entry.
     */
    String ROLE_INDICATOR_VIEWER = "ROLE_INDICATOR_VIEWER";
    String INDICATOR_VIEWER = ROLE_INDICATOR_VIEWER.substring(ROLE_PREFIX.length());

    /**
     * May create new indicators, update and delete existing indicators.
     */
    String ROLE_INDICATOR_EDITOR = "ROLE_INDICATOR_EDITOR";
    String INDICATOR_EDITOR = ROLE_INDICATOR_EDITOR.substring(ROLE_PREFIX.length());

    /**
     * May view the Users navigation entry.
     */
    String ROLE_USER_VIEWER = "ROLE_USER_VIEWER";
    String USER_VIEWER = ROLE_USER_VIEWER.substring(ROLE_PREFIX.length());

    /**
     * May create new users, update and delete existing users.
     */
    String ROLE_USER_EDITOR = "ROLE_USER_EDITOR";
    String USER_EDITOR = ROLE_USER_EDITOR.substring(ROLE_PREFIX.length());

}
