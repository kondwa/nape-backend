package com.mainlevel.monitoring.admin.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.template.PageDTO;
import com.mainlevel.monitoring.admin.api.dto.template.TemplateDTO;
import com.mainlevel.monitoring.admin.repository.entity.TemplateEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * SurveyTemplateEntity(Business object) to SurveyTemplateDTO( transfer object) converter.
 */
@Component
public class TemplateEntityToTemplateDTOConverter extends AbstractApplicationAwareConverter<TemplateEntity, TemplateDTO> {

    @Override
    public TemplateDTO convert(TemplateEntity source) {
        final List<PageDTO> pages = getCollectionConversionService().convert(source.getPages(), PageDTO.class);

        final TemplateDTO target = TemplateDTO.builder().identifier(source.getId()).pages(pages).title(source.getTitle()).version(source.getVersion())
            .updated(source.getUpdated()).description(source.getDescription()).build();

        return target;
    }
}
