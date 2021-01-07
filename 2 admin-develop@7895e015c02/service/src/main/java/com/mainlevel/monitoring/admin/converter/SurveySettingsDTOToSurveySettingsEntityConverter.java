package com.mainlevel.monitoring.admin.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.template.SurveySettingsDTO;
import com.mainlevel.monitoring.admin.repository.entity.LanguageEntity;
import com.mainlevel.monitoring.admin.repository.entity.UnitEntity;
import com.mainlevel.monitoring.admin.repository.entity.SurveySettingsEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * SurveySettingsDTO to Survey converter.
 */
@Component
public class SurveySettingsDTOToSurveySettingsEntityConverter extends AbstractApplicationAwareConverter<SurveySettingsDTO, SurveySettingsEntity> {

    @Override
    public SurveySettingsEntity convert(SurveySettingsDTO source) {

        LanguageEntity languageEntity = super.getConversionService().convert(source.getLanguage(), LanguageEntity.class);
        UnitEntity unitEntity = super.getConversionService().convert(source.getUnit(), UnitEntity.class);

        final SurveySettingsEntity target =
            SurveySettingsEntity.builder().surveyName(source.getSurveyName()).unit(unitEntity).language(languageEntity).build();

        return target;
    }

}
