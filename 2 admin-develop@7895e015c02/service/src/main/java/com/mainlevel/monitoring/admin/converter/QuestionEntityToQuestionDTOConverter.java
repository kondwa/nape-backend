package com.mainlevel.monitoring.admin.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.template.ChoiceDTO;
import com.mainlevel.monitoring.admin.api.dto.template.QuestionDTO;
import com.mainlevel.monitoring.admin.api.dto.template.TriggerDTO;
import com.mainlevel.monitoring.admin.api.dto.template.ValidatorDTO;
import com.mainlevel.monitoring.admin.api.dto.template.ValueItemDTO;
import com.mainlevel.monitoring.admin.repository.entity.ChoiceEntity;
import com.mainlevel.monitoring.admin.repository.entity.QuestionEntity;
import com.mainlevel.monitoring.admin.repository.entity.TriggerEntity;
import com.mainlevel.monitoring.admin.repository.entity.ValidatorEntity;
import com.mainlevel.monitoring.admin.repository.entity.ValueItemEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * PageDTO to Page converter.
 */
@Component
public class QuestionEntityToQuestionDTOConverter extends AbstractApplicationAwareConverter<QuestionEntity, QuestionDTO> {

    @Override
    public QuestionDTO convert(QuestionEntity question) {

        List<ValueItemDTO> choices = convertValueItems(question.getChoices());
        List<ValidatorDTO> validators = convertValidators(question.getValidators());
        List<TriggerDTO> triggers = convertTriggers(question.getTriggers());
        List<ValueItemDTO> rows = convertValueItems(question.getRows());
        List<ValueItemDTO> columns = convertValueItems(question.getColumns());

        final QuestionDTO target = QuestionDTO.builder().name(question.getName()).title(question.getTitle()).choices(choices)
            .description(question.getDescription()).isRequired(question.getIsRequired()).validators(validators).suffix(question.getSuffix())
            .hasOther(question.getHasOther()).choiceExclusive(question.getChoiceExclusive()).triggers(triggers).rows(rows).columns(columns)
            .visible(question.getVisible()).predefinedListType(question.getPredefinedListType()).type(question.getType()).view(question.getView()).build();

        return target;
    }

    private List<TriggerDTO> convertTriggers(List<TriggerEntity> entities) {

        List<TriggerDTO> dtos = new ArrayList<>();

        if (entities != null) {
            entities.forEach(dto -> {
                TriggerDTO entity = TriggerDTO.builder().action(dto.getAction()).answerValue(dto.getAnswerValue()).id(dto.getId())
                    .targetName(dto.getTargetName()).compare(dto.getCompare()).build();
                dtos.add(entity);
            });

        }

        return dtos;
    }

    private List<ValidatorDTO> convertValidators(List<ValidatorEntity> entities) {
        List<ValidatorDTO> dtos = new ArrayList<>();

        if (entities != null) {
            entities.forEach(dto -> {
                ValidatorDTO entity = ValidatorDTO.builder().text(dto.getText()).validatorType(dto.getValidatorType()).columnName(dto.getColumnName())
                    .props(dto.getProps()).build();

                dtos.add(entity);
            });
        }
        return dtos;
    }

    private List<ValueItemDTO> convertValueItems(List<ValueItemEntity> entities) {

        List<ValueItemDTO> dtos = new ArrayList<>();

        if (entities != null) {
            entities.forEach(entity -> {

                List<ChoiceDTO> choices = convertChoices(entity.getChoices());

                ValueItemDTO dto = ValueItemDTO.builder().choiceExclusive(entity.getChoiceExclusive()).value(entity.getValue()).name(entity.getName())
                    .type(entity.getType()).hasOther(entity.getHasOther()).infoText(entity.getInfoText()).text(entity.getText())
                    .predefinedList(entity.getPredefinedList()).predefinedListType(entity.getPredefinedListType()).suffix(entity.getSuffix())
                    .choices(choices).build();

                dtos.add(dto);
            });
        }

        return dtos;
    }

    private List<ChoiceDTO> convertChoices(List<ChoiceEntity> entities) {

        List<ChoiceDTO> dtos = new ArrayList<>();
        if (entities != null) {
            entities.forEach(entity -> {
                ChoiceDTO dto =
                    ChoiceDTO.builder().infoText(entity.getInfoText()).text(entity.getText()).name(entity.getName()).value(entity.getValue()).build();
                dtos.add(dto);
            });
        }

        return dtos;
    }
}
