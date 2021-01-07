/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.resource;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.survey.api.dto.survey.SurveyStatsDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyStatsListDTO;
import com.mainlevel.monitoring.survey.api.resource.SurveyStatsResource;
import com.mainlevel.monitoring.survey.database.queryresult.SurveyStats;
import com.mainlevel.monitoring.survey.service.SurveyService;

/**
 * Default implementation of {@link SurveyStatsResource}.
 */
@RestController
@Secured(ROLE_USER)
public class SurveyStatsResourceImpl implements SurveyStatsResource {

    @Autowired
    private SurveyService surveyService;

    @Override
    public ResponseEntity<SurveyStatsListDTO> getSurveyStats(@RequestParam(name = "projectKey", required = false) String projectKey) {

        List<SurveyStats> surveyStatistics = surveyService.loadSurveyStatistics(projectKey);
        List<SurveyStatsDTO> surveyStatsDTOs = new ArrayList<>();

        Map<Long, List<SurveyStats>> surveyMap = surveyStatistics.stream().collect(Collectors.groupingBy(SurveyStats::getGid));

        surveyMap.values().forEach(surveyStatList -> {

            Optional<SurveyStats> first = surveyStatList.stream().findFirst();
            if (first.isPresent()) {
                SurveyStatsDTO surveyStatsDTO = convertToDTO(first.get(), surveyStatList);
                surveyStatsDTOs.add(surveyStatsDTO);
            }

        });

        surveyStatsDTOs.sort((s1, s2) -> s1.getCreationDate().compareTo(s2.getCreationDate()));

        SurveyStatsListDTO result = SurveyStatsListDTO.builder().surveys(surveyStatsDTOs).build();

        return ResponseEntity.ok(result);
    }

    /**
     * Convert the statistics entity to DTO and calculate percentage values. This is done outside of a converter class because of the N:1 mapping.
     *
     * @param firstStat first statistics entity
     * @param surveyStatList the list of statistics per template id and version
     * @return the created stats DTO
     */
    private SurveyStatsDTO convertToDTO(SurveyStats firstStat, List<SurveyStats> surveyStatList) {

        SurveyStatsDTO surveyStatsDTO = SurveyStatsDTO.builder().graphId(firstStat.getGid()).surveyName(firstStat.getSurveyName())
            .templateRefId(firstStat.getTemplateRefId()).templateName(firstStat.getTemplateName()).templateVersion(firstStat.getTemplateVersion())
            .unit(firstStat.getUnitName()).createdBy(firstStat.getCreatedBy()).creationDate(firstStat.getCreationTime()).type(firstStat.getType())
            .visibility(firstStat.getVisibility()).questionGroups(firstStat.getNumberOfGroups()).questions(firstStat.getNumberOfQuestions())
            .targetInstances(firstStat.getTargetInstances()).build();

        int instances = 0;

        for (SurveyStats surveyStat : surveyStatList) {

            instances += surveyStat.getNumberOfInstances();

            if (surveyStat.getStatus() != null) {

                switch (surveyStat.getStatus()) {

                    case NEW: {
                        surveyStatsDTO.setNewInstances(surveyStat.getNumberOfInstances());
                        break;
                    }

                    case IN_PROGRESS: {
                        surveyStatsDTO.setRunningInstances(surveyStat.getNumberOfInstances());
                        break;
                    }

                    case SENT: {
                        surveyStatsDTO.setSubmittedInstances(surveyStat.getNumberOfInstances());
                        break;
                    }

                    default: {
                        break;
                    }
                }
            }
        }

        if (surveyStatsDTO.getTargetInstances() == null) {
            surveyStatsDTO.setTargetInstances(instances);
        }

        surveyStatsDTO.setInstances(instances);
        if (instances > 0) {

            BigDecimal targetPercentage =
                BigDecimal.valueOf(100f * surveyStatsDTO.getSubmittedInstances() / surveyStatsDTO.getTargetInstances()).round(new MathContext(2));
            BigDecimal newPercentage = BigDecimal.valueOf(100f * surveyStatsDTO.getNewInstances() / instances).round(new MathContext(2));
            BigDecimal runningPercentage = BigDecimal.valueOf(100f * surveyStatsDTO.getRunningInstances() / instances).round(new MathContext(2));
            BigDecimal submittedPercentage = BigDecimal.valueOf(100f * surveyStatsDTO.getSubmittedInstances() / instances).round(new MathContext(2));

            surveyStatsDTO.setTargetPercentage(targetPercentage.floatValue());
            surveyStatsDTO.setNewPercentage(newPercentage.floatValue());
            surveyStatsDTO.setRunningPercentage(runningPercentage.floatValue());
            surveyStatsDTO.setSubmittedPercentage(submittedPercentage.floatValue());
        }

        return surveyStatsDTO;
    }

}
