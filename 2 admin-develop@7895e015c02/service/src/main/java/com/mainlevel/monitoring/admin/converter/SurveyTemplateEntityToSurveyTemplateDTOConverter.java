package com.mainlevel.monitoring.admin.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.language.LanguageDTO;
import com.mainlevel.monitoring.admin.api.dto.template.SurveyTemplateDTO;
import com.mainlevel.monitoring.admin.api.dto.template.TemplateDTO;
import com.mainlevel.monitoring.admin.api.dto.unit.UnitDTO;
import com.mainlevel.monitoring.admin.repository.entity.SurveyTemplateEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * SurveyEntity BO to SurveyDTO object converter.
 */
@Component
public class SurveyTemplateEntityToSurveyTemplateDTOConverter extends AbstractApplicationAwareConverter<SurveyTemplateEntity, SurveyTemplateDTO> {

    @Override
    public SurveyTemplateDTO convert(SurveyTemplateEntity source) {

        TemplateDTO templateDTO = super.getConversionService().convert(source.getTemplate(), TemplateDTO.class);
        UnitDTO unitDTO = super.getConversionService().convert(source.getUnit(), UnitDTO.class);
        LanguageDTO languageDTO = super.getConversionService().convert(source.getLanguage(), LanguageDTO.class);

        SurveyTemplateDTO target =
            SurveyTemplateDTO.builder().surveyName(source.getSurveyName()).surveyTemplate(templateDTO).identifier(source.getId())
                .createdDate(source.getCreatedDate()).lastModifiedDate(source.getLastModifiedDate()).version(source.getVersion()).unit(unitDTO)
                .language(languageDTO).status(source.getStatus()).description(source.getDescription()).updatedById(source.getUpdatedById()).build();

        return target;
    }
}
