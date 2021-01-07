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
public class QuestionDTOToQuestionEntityConverter extends AbstractApplicationAwareConverter<QuestionDTO, QuestionEntity> {

    @Override
    public QuestionEntity convert(QuestionDTO question) {

        List<ValueItemEntity> choices = convertValueItems(question.getChoices());
        List<ValueItemEntity> rows = convertValueItems(question.getRows());
        List<ValueItemEntity> columns = convertValueItems(question.getColumns());
        List<ValidatorEntity> validtors = convertValidators(question.getValidators());
        List<TriggerEntity> triggers = convertTriggers(question.getTriggers());

        final QuestionEntity target = QuestionEntity.builder().name(question.getName()).title(question.getTitle()).choices(choices)
            .description(question.getDescription()).isRequired(question.getIsRequired()).validators(validtors).suffix(question.getSuffix())
            .hasOther(question.getHasOther()).choiceExclusive(question.getChoiceExclusive()).triggers(triggers).rows(rows).columns(columns)
            .visible(question.getVisible()).predefinedListType(question.getPredefinedListType()).type(question.getType()).view(question.getView()).build();

        return target;
    }

    private List<TriggerEntity> convertTriggers(List<TriggerDTO> dtos) {

        List<TriggerEntity> entities = new ArrayList<>();

        if (dtos != null) {
            dtos.forEach(dto -> {
                TriggerEntity entity = TriggerEntity.builder().action(dto.getAction()).answerValue(dto.getAnswerValue()).id(dto.getId())
                    .targetName(dto.getTargetName()).compare(dto.getCompare()).build();
                entities.add(entity);
            });

        }

        return entities;
    }

    private List<ValidatorEntity> convertValidators(List<ValidatorDTO> dtos) {
        List<ValidatorEntity> entities = new ArrayList<>();

        if (dtos != null) {
            dtos.forEach(dto -> {
                ValidatorEntity entity = ValidatorEntity.builder().text(dto.getText()).validatorType(dto.getValidatorType())
                    .columnName(dto.getColumnName()).props(dto.getProps()).build();

                entities.add(entity);
            });
        }
        return entities;
    }

    private List<ValueItemEntity> convertValueItems(List<ValueItemDTO> dtos) {

        List<ValueItemEntity> entities = new ArrayList<>();

        if (dtos != null) {
            dtos.forEach(dto -> {

                List<ChoiceEntity> choices = convertChoices(dto.getChoices());

                ValueItemEntity entity = ValueItemEntity.builder().choiceExclusive(dto.getChoiceExclusive()).name(dto.getName()).type(dto.getType())
                    .hasOther(dto.getHasOther()).infoText(dto.getInfoText()).predefinedList(dto.getPredefinedList())
                    .predefinedListType(dto.getPredefinedListType()).suffix(dto.getSuffix()).choices(choices).build();

                entities.add(entity);
            });
        }

        return entities;
    }

    private List<ChoiceEntity> convertChoices(List<ChoiceDTO> dtos) {

        List<ChoiceEntity> entities = new ArrayList<>();
        if (entities != null) {
            dtos.forEach(dto -> {
                ChoiceEntity entity = ChoiceEntity.builder().infoText(dto.getInfoText()).name(dto.getName()).value(dto.getValue()).build();
                entities.add(entity);
            });
        }

        return entities;
    }

}
