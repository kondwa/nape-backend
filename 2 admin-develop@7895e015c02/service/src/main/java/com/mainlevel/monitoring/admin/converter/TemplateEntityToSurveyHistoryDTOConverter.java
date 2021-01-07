package com.mainlevel.monitoring.admin.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.template.SurveyHistoryDTO;
import com.mainlevel.monitoring.admin.repository.entity.TemplateEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * SurveyTemplateEntity(Business object) to SurveyTemplateDTO( transfer object) converter.
 */
@Component
public class TemplateEntityToSurveyHistoryDTOConverter extends AbstractApplicationAwareConverter<TemplateEntity, SurveyHistoryDTO> {

    @Override
    public SurveyHistoryDTO convert(TemplateEntity template) {

        final SurveyHistoryDTO target = SurveyHistoryDTO.builder().identifier(template.getId()).title(template.getTitle())
            .version(template.getVersion()).updated(template.getUpdated()).status(template.getStatus()).updatedBy(template.getUpdatedByName())
            .description(template.getDescription()).build();

        return target;
    }
}
