package com.mainlevel.monitoring.admin.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.template.SurveyTemplateDTO;
import com.mainlevel.monitoring.admin.repository.entity.LanguageEntity;
import com.mainlevel.monitoring.admin.repository.entity.UnitEntity;
import com.mainlevel.monitoring.admin.repository.entity.SurveyTemplateEntity;
import com.mainlevel.monitoring.admin.repository.entity.TemplateEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * SurveyDTO to SurveyEntity converter.
 */
@Component
public class SurveyTemplateDTOToSurveyTemplateEntityConverter extends AbstractApplicationAwareConverter<SurveyTemplateDTO, SurveyTemplateEntity> {

    @Override
    public SurveyTemplateEntity convert(SurveyTemplateDTO source) {

        TemplateEntity templateEntity = super.getConversionService().convert(source.getSurveyTemplate(), TemplateEntity.class);
        UnitEntity unitEntity = super.getConversionService().convert(source.getUnit(), UnitEntity.class);
        LanguageEntity languageEntity = super.getConversionService().convert(source.getLanguage(), LanguageEntity.class);

        SurveyTemplateEntity target = SurveyTemplateEntity.builder().createdDate(source.getCreatedDate()).id(source.getIdentifier())
            .lastModifiedDate(source.getLastModifiedDate()).surveyName(source.getSurveyName()).template(templateEntity).version(source.getVersion())
            .unit(unitEntity).language(languageEntity).description(source.getDescription()).build();

        return target;
    }
}
