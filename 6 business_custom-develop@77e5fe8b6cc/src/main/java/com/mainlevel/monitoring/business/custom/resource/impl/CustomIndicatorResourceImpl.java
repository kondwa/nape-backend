/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.impl;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.admin.api.dto.filter.FilterDTO;
import com.mainlevel.monitoring.admin.api.dto.indicator.IndicatorDTO;
import com.mainlevel.monitoring.admin.api.dto.indicator.IndicatorGroupListDTO;
import com.mainlevel.monitoring.admin.api.dto.indicator.IndicatorListDTO;
import com.mainlevel.monitoring.admin.api.resource.IndicatorResource;
import com.mainlevel.monitoring.business.custom.resource.CustomIndicatorResource;
import com.mainlevel.monitoring.business.custom.resource.dto.indicator.CustomIndicatorDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.indicator.CustomIndicatorGroupDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.indicator.CustomIndicatorGroupListDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.indicator.CustomIndicatorListDTO;
import com.mainlevel.monitoring.common.service.CollectionConversionService;
import com.mainlevel.monitoring.survey.api.dto.evaluation.EvaluationRequestDTO;
import com.mainlevel.monitoring.survey.api.dto.evaluation.EvaluationResponseDTO;
import com.mainlevel.monitoring.survey.api.resource.EvaluationResource;

/**
 * Default implementation of {@link CustomIndicatorResource}.
 */
@RestController
@Secured(ROLE_USER)
public class CustomIndicatorResourceImpl implements CustomIndicatorResource {

    @Autowired
    private IndicatorResource indicatorResource;

    @Autowired
    private EvaluationResource evaluationResource;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Override
    public ResponseEntity<CustomIndicatorGroupListDTO> getIndicatorGroups() {

        ResponseEntity<IndicatorGroupListDTO> response = indicatorResource.getIndicatorGroups();

        if (response == null || response.getBody() == null) {
            throw new RuntimeException("Error loading indicator groups.");
        }

        List<CustomIndicatorGroupDTO> groups = collectionConversionService.convert(response.getBody().getGroups(), CustomIndicatorGroupDTO.class);
        CustomIndicatorGroupListDTO groupList = CustomIndicatorGroupListDTO.builder().groups(groups).build();

        return ResponseEntity.ok(groupList);
    }

    @Override
    public ResponseEntity<CustomIndicatorListDTO> getIndicators(@PathVariable("identifier") String groupId,
        @RequestParam Map<String, String> filterParams) {

        ResponseEntity<IndicatorListDTO> response = indicatorResource.getIndicators(groupId);

        if (response == null || response.getBody() == null) {
            throw new RuntimeException("Error loading indicator for id '" + groupId + "'.");
        }

        IndicatorListDTO indicatorList = response.getBody();

        Map<String, Object> params = getDefaultParams(response.getBody().getFilters());
        params.putAll(filterParams);

        List<CustomIndicatorDTO> customIndicators = new ArrayList<>();
        for (IndicatorDTO indicator : indicatorList.getIndicators()) {
            CustomIndicatorDTO customIndicator = loadDataForIndicator(indicator, params);

            customIndicators.add(customIndicator);
        }

        CustomIndicatorListDTO resultList = CustomIndicatorListDTO.builder().groupName(indicatorList.getGroupName()).unitName(indicatorList.getUnitName())
            .indicators(customIndicators).build();

        return ResponseEntity.ok(resultList);
    }

    @Override
    public ResponseEntity<CustomIndicatorDTO> getIndicator(@PathVariable("identifier") String groupId, @PathVariable("key") String indicatorKey,
        @RequestParam Map<String, String> filterParams) {

        ResponseEntity<IndicatorListDTO> response = indicatorResource.getIndicators(groupId);

        if (response == null || response.getBody() == null) {
            throw new RuntimeException("Error loading indicator for id '" + groupId + "'.");
        }

        Map<String, Object> params = getDefaultParams(response.getBody().getFilters());
        params.putAll(filterParams);

        IndicatorListDTO indicatorList = response.getBody();
        IndicatorDTO indicator = indicatorList.getIndicators().stream().filter(i -> i.getKey().equals(indicatorKey)).findAny().get();
        CustomIndicatorDTO customIndicator = loadDataForIndicator(indicator, params);

        return ResponseEntity.ok(customIndicator);
    }

    private Map<String, Object> getDefaultParams(List<FilterDTO> filters) {

        Map<String, Object> params = new HashMap<>();
        filters.forEach(filter -> params.put(filter.getName(), null));

        return params;
    }

    /**
     * Convert indicator to Custom indicator and add the data.
     *
     * @param indicator the admin indicator
     * @param filterParams the filter parameters
     * @return the custom indicator including data
     */
    private CustomIndicatorDTO loadDataForIndicator(IndicatorDTO indicator, Map<String, Object> filterParams) {
        CustomIndicatorDTO customIndicator = conversionService.convert(indicator, CustomIndicatorDTO.class);

        if (customIndicator.getVisualizations() != null) {
            List<List<Object>> data = loadQueryData(indicator.getDataQuery(), filterParams);
            customIndicator.getVisualizations().forEach(vis -> vis.setData(data));
        }
        return customIndicator;
    }

    /**
     * Loads the data for the given query and converts it into the visualization structure.
     *
     * @param query the query to execute
     * @param filterParams the filter parameters
     * @return the loaded data
     */
    private List<List<Object>> loadQueryData(String query, Map<String, Object> filterParams) {

        EvaluationRequestDTO evalRq = EvaluationRequestDTO.builder().query(query).parameters(filterParams).build();
        ResponseEntity<EvaluationResponseDTO> evalRs = evaluationResource.evaluateQuery(evalRq);

        if (evalRs == null || evalRs.getBody() == null) {
            throw new RuntimeException("Error evaluating query '" + query + "'.");
        }

        List<Map<String, Object>> queryResult = evalRs.getBody().getResult();

        List<List<Object>> result = new ArrayList<>();
        Optional<Map<String, Object>> first = queryResult.stream().findFirst();
        if (!first.isPresent()) {
            return result;
        }

        Set<String> keys = first.get().keySet();
        result.add(new ArrayList<>(keys));

        List<List<Object>> rows = queryResult.stream().map(row -> new ArrayList<>(row.values())).collect(Collectors.toList());
        result.addAll(rows);

        return result;
    }

}
