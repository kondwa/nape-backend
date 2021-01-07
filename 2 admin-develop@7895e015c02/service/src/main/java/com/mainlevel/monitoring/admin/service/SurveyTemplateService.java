package com.mainlevel.monitoring.admin.service;

import java.util.List;
import java.util.Set;

import com.mainlevel.monitoring.admin.repository.entity.SurveySettingsEntity;
import com.mainlevel.monitoring.admin.repository.entity.SurveyTemplateEntity;
import com.mainlevel.monitoring.admin.repository.entity.TemplateEntity;

/**
 * Contains business logic for SurveyEntity.
 */
public interface SurveyTemplateService {

    /**
     * Saves a survey template to the database.
     *
     * @param template the survey to save
     * @return created survey
     */
    SurveyTemplateEntity saveSurveyTemplate(SurveyTemplateEntity template);

    /**
     * Update an existing survey with data from the param survey.
     *
     * @param template the survey template
     * @return updated survey.
     */
    SurveyTemplateEntity updateSurveyTemplate(SurveyTemplateEntity template);

    /**
     * Updates settings of existing survey and creates new version of it.
     *
     * @param surveyId targe survey id.
     * @param settings survey with new settings data.
     * @return updated survey.
     */
    SurveyTemplateEntity updateSurveySettings(String surveyId, SurveySettingsEntity settings);

    /**
     * Load a survey template for the given id.
     *
     * @param id primary key of the survey template
     * @return the survey template
     */
    SurveyTemplateEntity loadSurvey(String id);

    /**
     * Deactivates a survey with given id.
     *
     * @param id the survey template id
     */
    void deactivateSurvey(String id);

    /**
     * Returns all active surveys
     *
     * @return the list of active survey templates
     */
    List<SurveyTemplateEntity> getActiveSurveys();

    /**
     * Find survey by given parameters.
     *
     * @param unitId id of the organizational unit
     * @return list of surveys
     */
    Set<SurveyTemplateEntity> loadSurveysByFilter(String unitId);

    /**
     * Return old templates for a survey with the surveyId.
     *
     * @param surveyid survey template id
     * @return a list of old template versions
     */
    List<TemplateEntity> getHistory(String surveyid);

    /**
     * Load a survey for the given id and version
     *
     * @param id primary key
     * @param version eager version of template
     * @return survey template
     */
    SurveyTemplateEntity loadSurveyVersion(String id, Long version);

    /**
     * Deactivates a survey with given id and version
     *
     * @param id the survey template id
     * @param version the survey template version
     */
    void deactivateSurveyVersion(String id, Long version);

    /**
     * Switch workflow status of a survey to active.
     *
     * @param survey target survey
     */
    void doWorkflowActive(SurveyTemplateEntity survey);
}
