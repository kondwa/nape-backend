/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.template.PageDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomQuestionDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.template.CustomTemplateQuestionGroupDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link PageDTO} to {@link CustomTemplateQuestionGroupDTO}.
 */
@Component
public class PageDTOToCustomTemplatePageDTOConverter extends AbstractApplicationAwareConverter<PageDTO, CustomTemplateQuestionGroupDTO> {

    @Override
    public CustomTemplateQuestionGroupDTO convert(PageDTO source) {

        List<CustomQuestionDTO> questions = super.getCollectionConversionService().convert(source.getQuestions(), CustomQuestionDTO.class);

        CustomTemplateQuestionGroupDTO result = CustomTemplateQuestionGroupDTO.builder().name(source.getName()).description(source.getDescription())
            .visible(source.getVisible()).questions(questions).build();

        return result;
    }

}
