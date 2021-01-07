/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mainlevel.monitoring.business.custom.resource.dto.answer.CustomQuestionAnswerDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomQuestionDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomTriggerDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyInstanceDTO;
import com.mainlevel.monitoring.business.custom.service.CustomSurveyTriggerService;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link CustomSurveyTriggerService}.
 */
@Slf4j
@Service
public class CustomSurveyTriggerServiceImpl implements CustomSurveyTriggerService {

    @Override
    public void applyAllTriggers(CustomSurveyInstanceDTO surveyInstance) {

        Map<Integer, CustomQuestionDTO> questions = new HashMap<>();
        List<String> activatedQuestions = new ArrayList<String>();

        surveyInstance.getPages().forEach(page -> {
            page.getQuestions().forEach(question -> {
                questions.put(question.getIndex(), question);
            });
        });

        questions.values().forEach(question -> {
            question.getTriggers().forEach(trigger -> {

                runTrigger(trigger, question, questions, activatedQuestions);

            });
        });
    }

    /**
     * Run the given trigger against the list of questions.
     *
     * @param trigger the trigger to run
     * @param question the quetion holding the trigger
     * @param allQuestions the list of all questions
     */
    private void runTrigger(CustomTriggerDTO trigger, CustomQuestionDTO question, Map<Integer, CustomQuestionDTO> allQuestions, List<String> activatedQuestions) {

        switch (trigger.getActionType()) {

            case ACTIVATE: {

                CustomQuestionDTO targetQuestion = allQuestions.get(trigger.getTargetQuestionIndex());

                if (targetQuestion != null) {

                    Optional<CustomQuestionAnswerDTO> option = question.getAnswers().stream()
                        .filter(a -> a.getAnswer().getOption() != null && a.getAnswer().getOption().getIndex().equals(trigger.getOptionIndex()))
                        .findFirst();

                    if (option.isPresent()) {
                        log.info("Activating question {} by trigger {}.", targetQuestion.getName(), trigger.getGraphId());
                        targetQuestion.setVisible(true);
                        activatedQuestions.add(targetQuestion.getName());
                    } else if (!activatedQuestions.contains(targetQuestion.getName())) {
                        log.info("Deactivating question {} by trigger {}.", targetQuestion.getName(), trigger.getGraphId());
                        targetQuestion.setVisible(false);
                    }
                }

                break;
            }

            default:
                throw new IllegalStateException("Trigger action type [" + trigger.getActionType() + "] is not supported.");
        }
    }

}
