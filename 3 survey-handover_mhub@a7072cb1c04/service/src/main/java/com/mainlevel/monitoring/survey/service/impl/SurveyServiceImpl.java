/**
 * Storing data service implementation.
 */
package com.mainlevel.monitoring.survey.service.impl;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainlevel.monitoring.common.service.AuthenticationAccessService;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyVisibilityType;
import com.mainlevel.monitoring.survey.database.node.Link;
import com.mainlevel.monitoring.survey.database.node.Survey;
import com.mainlevel.monitoring.survey.database.node.User;
import com.mainlevel.monitoring.survey.database.queryresult.SurveyStats;
import com.mainlevel.monitoring.survey.database.repository.SurveyRepository;
import com.mainlevel.monitoring.survey.service.SurveyLinkService;
import com.mainlevel.monitoring.survey.service.SurveyService;
import com.mainlevel.monitoring.survey.service.UserService;

/**
 * Default implementation of {@link SurveyService}.
 */
@Service
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyLinkService linkService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationAccessService accessService;

    @Override
    @Transactional(propagation = REQUIRED, readOnly = false)
    public Survey saveSurvey(Survey survey) {

        if (survey.getVisibility() == SurveyVisibilityType.ANONYMOUS) {
            User anonymousUser = userService.getAnonymousUser();
            Link link = Link.builder().active(true).survey(survey).visibility(SurveyVisibilityType.ANONYMOUS).user(anonymousUser).build();

            linkService.createLink(link);
        }

        return surveyRepository.save(survey, -1);
    }

    @Override
    @Transactional(propagation = REQUIRED, readOnly = true)
    public Survey loadSurveyById(Long surveyGid) {
        return surveyRepository.findOne(surveyGid, 0);
    }

    @Override
    @Transactional(propagation = REQUIRED, readOnly = true)
    public Survey loadSurveyWithStructureById(Long surveyGid) {
        return surveyRepository.findWithStructureById(surveyGid);
    }

    @Override
    @Transactional(propagation = REQUIRED, readOnly = true)
    public List<Survey> loadSurveysForTemplate(String templateId, long version) {
        List<Survey> surveys = surveyRepository.findSurveyByTemplateIdAndVersion(templateId, version);
        return surveys;
    }

    @Override
    @Transactional(propagation = REQUIRED, readOnly = true)
    public List<SurveyStats> loadSurveyStatistics(String projectKey) {
        String username = accessService.getCurrentUsername();
        return surveyRepository.findSurveyStats(username, projectKey);
    }
}
