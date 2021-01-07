/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.resource;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.common.monitoring.annotation.PerformanceMonitor;
import com.mainlevel.monitoring.common.service.CollectionConversionService;
import com.mainlevel.monitoring.survey.api.dto.evaluation.EvaluationChartType;
import com.mainlevel.monitoring.survey.api.dto.evaluation.EvaluationRequestDTO;
import com.mainlevel.monitoring.survey.api.dto.evaluation.EvaluationResponseDTO;
import com.mainlevel.monitoring.survey.api.dto.evaluation.EvaluationResultEntryDTO;
import com.mainlevel.monitoring.survey.api.dto.evaluation.EvaluationResultListDTO;
import com.mainlevel.monitoring.survey.api.dto.evaluation.facet.FacetParamsDTO;
import com.mainlevel.monitoring.survey.api.resource.EvaluationResource;
import com.mainlevel.monitoring.survey.database.queryresult.evaluation.EvaluationResult;
import com.mainlevel.monitoring.survey.service.EvaluationService;
import com.mainlevel.monitoring.survey.service.model.facet.FacetParams;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link EvaluationResource}.
 */
@Slf4j
@RestController
@Secured(ROLE_USER)
public class EvaluationResourceImpl implements EvaluationResource {

    /** Default title for evaluation components. */
    private static final String DEFAULT_TITLE = "undefined";

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Override
    public ResponseEntity<EvaluationResponseDTO> evaluateQuery(@RequestBody EvaluationRequestDTO body) {

        List<Map<String, Object>> result = evaluationService.evaluateQuery(body.getQuery(), body.getParameters());

        EvaluationResponseDTO dto = EvaluationResponseDTO.builder().result(result).build();

        return ResponseEntity.ok(dto);
    }

    @Override
    @PerformanceMonitor
    public ResponseEntity<EvaluationResultListDTO> loadEvaluationResults(@PathVariable("queryName") final String queryName,
        FacetParamsDTO facetsDTO) {

        log.info("Loading results for survey evaluation {}.", queryName);

        FacetParams facetParams = facetsDTO != null ? conversionService.convert(facetsDTO, FacetParams.class) : null;

        final List<EvaluationResult> results = evaluationService.loadEvaluationResults(queryName, facetParams);

        EvaluationResult anyResult = results.stream().findAny()
            .orElse(EvaluationResult.builder().title(DEFAULT_TITLE).xAxisDesc(DEFAULT_TITLE).yAxisDesc(DEFAULT_TITLE).build());

        final List<EvaluationResultEntryDTO> entries = collectionConversionService.convert(results, EvaluationResultEntryDTO.class);

        final EvaluationResultListDTO resultDTO = EvaluationResultListDTO.builder().title(anyResult.getTitle()).chartType(EvaluationChartType.LINE)
            .xAxisDesc(anyResult.getXAxisDesc()).yAxisDesc(anyResult.getYAxisDesc()).data(entries).build();

        return ResponseEntity.ok(resultDTO);
    }

}
