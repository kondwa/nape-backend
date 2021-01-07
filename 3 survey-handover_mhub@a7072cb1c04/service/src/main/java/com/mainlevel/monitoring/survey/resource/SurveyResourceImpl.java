/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.resource;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.common.service.CollectionConversionService;
import com.mainlevel.monitoring.survey.api.dto.SurveyLinkDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyListDTO;
import com.mainlevel.monitoring.survey.api.resource.SurveyResource;
import com.mainlevel.monitoring.survey.database.node.Link;
import com.mainlevel.monitoring.survey.database.node.Survey;
import com.mainlevel.monitoring.survey.database.node.User;
import com.mainlevel.monitoring.survey.database.node.question.Question;
import com.mainlevel.monitoring.survey.service.SurveyLinkService;
import com.mainlevel.monitoring.survey.service.SurveyService;
import com.mainlevel.monitoring.survey.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link SurveyResource}.
 */
@Slf4j
@RestController
@Secured(ROLE_USER)
public class SurveyResourceImpl implements SurveyResource {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private UserService userService;

    @Autowired
    private SurveyLinkService linkService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Override
    public ResponseEntity<SurveyDTO> createSurvey(@RequestBody SurveyDTO surveyDTO) {

        log.info("Creating new survey {}.", surveyDTO.getTemplateTitle());

        Survey survey = conversionService.convert(surveyDTO, Survey.class);

        User currentUser = userService.getCurrentUser();
        survey.setUser(currentUser);
        survey.setCreationTime(new Date());

        Map<Integer, Question> questionMap = new HashMap<>();
        survey.getGroups().forEach(group -> {
            group.getQuestions().forEach(question -> {
                questionMap.put(question.getIndex(), question);
            });
        });

        addTriggerTargets(questionMap);

        survey = surveyService.saveSurvey(survey);

        SurveyDTO result = conversionService.convert(survey, SurveyDTO.class);
        return ResponseEntity.ok(result);
    }

    /**
     * Adds the concrete target questions to the trigger nodes.
     *
     * @param questionMap the map of questions per index
     */
    private void addTriggerTargets(Map<Integer, Question> questionMap) {
        questionMap.values().forEach(question -> {
            if (question.getTriggers() != null) {
                question.getTriggers().forEach(trigger -> {
                    trigger.setTargetQuestion(questionMap.get(trigger.getTargetQuestionIndex()));
                });
            }
        });
    }

    @Override
    public ResponseEntity<SurveyListDTO> getSurveysForTemplate(@RequestParam("templateId") String templateId,
        @RequestParam("templateVersion") Long version) {

        List<Survey> surveys = surveyService.loadSurveysForTemplate(templateId, version);

        List<SurveyDTO> surveyDTOs = collectionConversionService.convert(surveys, SurveyDTO.class);

        return ResponseEntity.ok(SurveyListDTO.builder().surveys(surveyDTOs).build());
    }

    @Override
    public ResponseEntity<SurveyDTO> getSurvey(@PathVariable(name = "identifier", required = true) Long surveyGid,
        @RequestParam(name = "includingStructure", required = false) Boolean includingStructure) {

        Survey survey;
        if (Boolean.TRUE.equals(includingStructure)) {
            survey = surveyService.loadSurveyWithStructureById(surveyGid);
        } else {
            survey = surveyService.loadSurveyById(surveyGid);
        }

        SurveyDTO surveyDTO = conversionService.convert(survey, SurveyDTO.class);

        return ResponseEntity.ok(surveyDTO);
    }

    @Override
    public ResponseEntity<SurveyLinkDTO> getSurveyLink(@RequestParam(name = "identifier", required = true) Long surveyGid) {

        Link link = linkService.loadLinkForAnonymousSurvey(surveyGid);
        SurveyLinkDTO linkDTO = conversionService.convert(link, SurveyLinkDTO.class);

        return ResponseEntity.ok(linkDTO);
    }

}
