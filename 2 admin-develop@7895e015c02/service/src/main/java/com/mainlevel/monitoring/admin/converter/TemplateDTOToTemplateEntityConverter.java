package com.mainlevel.monitoring.admin.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.template.TemplateDTO;
import com.mainlevel.monitoring.admin.repository.entity.PageEntity;
import com.mainlevel.monitoring.admin.repository.entity.TemplateEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * SurveyTemplateDTO to SurveyTemplateEntity converter.
 */
@Component
public class TemplateDTOToTemplateEntityConverter extends AbstractApplicationAwareConverter<TemplateDTO, TemplateEntity> {

    /**
     * Method converts SurveyTemplateEntity(an object representation of template) from DTO into BO.
     * 
     * @param survey -input DTO object
     * @return if survey is null, return null, otherwise converted object.
     */
    @Override
    public TemplateEntity convert(TemplateDTO survey) {
        final List<PageEntity> pages = getCollectionConversionService().convert(survey.getPages(), PageEntity.class);
		
        final TemplateEntity target = TemplateEntity.builder().id(survey.getIdentifier()).pages(pages)
                .title(survey.getTitle()).version(survey.getVersion()).description(survey.getDescription()).updated(survey.getUpdated()).build();
		
        return target;
    }
}
