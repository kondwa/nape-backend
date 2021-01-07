package com.mainlevel.monitoring.admin.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.filter.FilterDTO;
import com.mainlevel.monitoring.admin.api.dto.indicator.IndicatorGroupDTO;
import com.mainlevel.monitoring.admin.api.dto.unit.UnitDTO;
import com.mainlevel.monitoring.admin.repository.entity.IndicatorGroupEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link IndicatorGroupEntity} to {@link IndicatorGroupDTO}.
 */
@Component
public class IndicatorGroupEntityToIndicatorGroupDTOConverter extends AbstractApplicationAwareConverter<IndicatorGroupEntity, IndicatorGroupDTO> {

    @Override
    public IndicatorGroupDTO convert(IndicatorGroupEntity source) {

        UnitDTO unitDTO = super.getConversionService().convert(source.getUnit(), UnitDTO.class);
        int size = source.getIndicators() != null ? source.getIndicators().size() : 0;

        List<FilterDTO> filters = super.getCollectionConversionService().convert(source.getFilters(), FilterDTO.class);

        final IndicatorGroupDTO target =
            IndicatorGroupDTO.builder().id(source.getId()).name(source.getName()).unit(unitDTO).filters(filters).size(size).build();

        return target;
    }

}
