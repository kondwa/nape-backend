/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */
package com.mainlevel.monitoring.business.custom.converter;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.template.SurveyTemplateDTO;
import com.mainlevel.monitoring.admin.api.dto.template.TemplateDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.CustomLanguageDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.template.CustomTemplateDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.template.CustomTemplateQuestionGroupDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.template.CustomTemplateStatus;
import com.mainlevel.monitoring.business.custom.resource.dto.unit.CustomUnitDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link SurveyTemplateDTO} to {@link CustomTemplateDTO}.
 */
@Component
public class SurveyTemplateDTOToCustomTemplateDTOConverter extends AbstractApplicationAwareConverter<SurveyTemplateDTO, CustomTemplateDTO> {

    @Override
    public CustomTemplateDTO convert(SurveyTemplateDTO source) {

        TemplateDTO template = source.getSurveyTemplate();
        CustomTemplateStatus status = CustomTemplateStatus.valueOfTemplateStatus(source.getStatus());

        List<CustomTemplateQuestionGroupDTO> pages = template != null
            ? super.getCollectionConversionService().convert(template.getPages(), CustomTemplateQuestionGroupDTO.class) : Collections.emptyList();

        CustomUnitDTO unit = super.getConversionService().convert(source.getUnit(), CustomUnitDTO.class);
        CustomLanguageDTO language = super.getConversionService().convert(source.getLanguage(), CustomLanguageDTO.class);

        CustomTemplateDTO result =
            CustomTemplateDTO.builder().identifier(source.getIdentifier()).name(source.getSurveyName()).description(source.getDescription())
                .version(source.getVersion()).createdDate(source.getCreatedDate()).modifiedDate(source.getLastModifiedDate())
                .modifiedByName(source.getUpdatedBy()).unit(unit).language(language).status(status).pages(pages).build();

        return result;
    }

}
