package com.mainlevel.monitoring.survey.service;

import java.util.List;

import com.mainlevel.monitoring.survey.database.node.Survey;
import com.mainlevel.monitoring.survey.database.queryresult.SurveyStats;

/**
 * Logic for manipulating with survey data.
 */
public interface SurveyService {

    /**
     * Stores the given survey in the database. All related nodes will also be saved.
     *
     * @param survey the survey to save
     * @return the saved survey
     */
    Survey saveSurvey(Survey survey);

    /**
     * Load the survey with the given graph id.
     *
     * @param surveyGid the survey graph id
     * @return the loaded survey
     */
    Survey loadSurveyById(Long surveyGid);

    /**
     * Load the survey including questions and questions groups with the given graph id.
     *
     * @param surveyGid the survey graph id
     * @return the loaded survey
     */
    Survey loadSurveyWithStructureById(Long surveyGid);

    /**
     * Load the list of survey statistics.
     * 
     * @param projectKey project key
     * @return the list of stats
     */
    List<SurveyStats> loadSurveyStatistics(String projectKey);

    /**
     * Loads the survey for the given template id and version.
     *
     * @param templateId id of the template
     * @param version version of the template
     * @return the survey
     */
    List<Survey> loadSurveysForTemplate(String templateId, long version);

}
