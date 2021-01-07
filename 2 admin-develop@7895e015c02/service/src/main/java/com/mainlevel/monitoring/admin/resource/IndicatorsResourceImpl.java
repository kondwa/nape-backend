package com.mainlevel.monitoring.admin.resource;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.admin.api.dto.indicator.IndicatorGroupDTO;
import com.mainlevel.monitoring.admin.api.dto.indicator.IndicatorGroupListDTO;
import com.mainlevel.monitoring.admin.api.dto.indicator.IndicatorListDTO;
import com.mainlevel.monitoring.admin.api.resource.IndicatorResource;
import com.mainlevel.monitoring.admin.repository.entity.IndicatorGroupEntity;
import com.mainlevel.monitoring.admin.service.IndicatorService;
import com.mainlevel.monitoring.common.service.CollectionConversionService;

/**
 * Default implementation of {@link IndicatorResource}.
 */
@RestController
@Secured(ROLE_USER)
public class IndicatorsResourceImpl implements IndicatorResource {

    @Autowired
    private IndicatorService indicatorService;

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Autowired
    private ConversionService conversionService;

    @Override
    public ResponseEntity<IndicatorGroupListDTO> getIndicatorGroups() {

        Set<IndicatorGroupEntity> groups = indicatorService.loadGroups();

        List<IndicatorGroupDTO> groupDTOs = collectionConversionService.convert(groups, IndicatorGroupDTO.class);

        Collections.sort(groupDTOs, Comparator.comparing(IndicatorGroupDTO::getName));

        IndicatorGroupListDTO groupListDTO = IndicatorGroupListDTO.builder().groups(groupDTOs).build();

        return ResponseEntity.ok(groupListDTO);
    }

    @Override
    public ResponseEntity<IndicatorListDTO> getIndicators(@PathVariable("identifier") String groupId) {

        IndicatorGroupEntity group = indicatorService.loadIndicatorsGroup(groupId);

        IndicatorListDTO indicatorListDTO = conversionService.convert(group, IndicatorListDTO.class);

        return ResponseEntity.ok(indicatorListDTO);
    }

}
