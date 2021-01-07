/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */
package com.mainlevel.monitoring.business.custom.converter;

import java.util.ArrayList;
import java.util.List;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import com.mainlevel.monitoring.admin.api.dto.template.QuestionDTO;
import com.mainlevel.monitoring.admin.api.dto.template.ValueItemDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.QuestionType;
import com.mainlevel.monitoring.survey.api.dto.survey.question.AnswerOptionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.AnswerOptionType;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyDateQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyMatrixQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyMatrixRowDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyNumberQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveySelectionQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveySelectionType;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyTextQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.trigger.SurveyTriggerDTO;

/**
 * Converter converting {@link QuestionDTO} from template to {@link SurveyQuestionDTO}.
 */
@Component
public class QuestionDTOToSurveyQuestionDTOConverter extends AbstractApplicationAwareConverter<QuestionDTO, SurveyQuestionDTO> {

    @Override
    public SurveyQuestionDTO convert(QuestionDTO source) {

        QuestionType type = QuestionType.byName(source.getType());

        SurveyQuestionDTO question;

        switch (type) {

            case COMMENT:
            case TEXT:
                question = createTextQuestion(source);
                type = QuestionType.TEXT;
                break;

            case DATE:
                question = createDateQuestion(source);
                break;

            case NUMBER:
                question = creteNumberQuestion(source);
                break;

            case RADIO:
            case CHECKBOX:
            case DROPDOWN:
            case PREDEFINED_LIST:
                question = creteSelectionQuestion(source, type);
                break;

            case MATRIX_SINGLE_CHOICE:
            case MATRIX_MULTIPLE_CHOICE:
                question = createMatrixQuestion(source);
                break;

            default:
                throw new IllegalStateException("The question type '" + source.getType() + "' is currently not supported.");
        }

        List<SurveyTriggerDTO> triggers = super.getCollectionConversionService().convert(source.getTriggers(), SurveyTriggerDTO.class);

        question.setName(source.getName());
        question.setTitle(sanitizeHtmlText(source.getTitle()));
        question.setType(type);
        question.setDescription(sanitizeHtmlText(source.getDescription()));
        question.setMandatory(source.getIsRequired());
        question.setVisible(source.getVisible());
        question.setTriggers(triggers);

        return question;
    }

    private SurveyNumberQuestionDTO creteNumberQuestion(QuestionDTO source) {
        SurveyNumberQuestionDTO question = new SurveyNumberQuestionDTO();
        question.setSuffix(source.getSuffix());
        return question;
    }

    private SurveyDateQuestionDTO createDateQuestion(QuestionDTO source) {
        SurveyDateQuestionDTO question = new SurveyDateQuestionDTO();
        return question;
    }

    private SurveyTextQuestionDTO createTextQuestion(QuestionDTO source) {
        SurveyTextQuestionDTO question = new SurveyTextQuestionDTO();
        return question;
    }

    private SurveySelectionQuestionDTO creteSelectionQuestion(QuestionDTO source, QuestionType type) {
        SurveySelectionQuestionDTO question = new SurveySelectionQuestionDTO();

        if (type == QuestionType.CHECKBOX) {
            question.setSelectionType(SurveySelectionType.MULTIPLE);
        } else {
            question.setSelectionType(SurveySelectionType.SINGLE);
        }

        List<AnswerOptionDTO> options = createOptions(source);
        question.setOptions(options);

        return question;
    }

    private SurveyMatrixQuestionDTO createMatrixQuestion(QuestionDTO source) {
        SurveyMatrixQuestionDTO question = new SurveyMatrixQuestionDTO();

        List<AnswerOptionDTO> options = createOptions(source);
        question.setOptions(options);

        List<SurveyMatrixRowDTO> rows = new ArrayList<>();

        int rowIndex = 0;
        for (ValueItemDTO row : source.getRows()) {
            SurveyMatrixRowDTO resultRow = SurveyMatrixRowDTO.builder().name(sanitizeHtmlText(row.getText()))
                .description(sanitizeHtmlText(row.getInfoText())).index(rowIndex++).build();
            rows.add(resultRow);
        }
        question.setRows(rows);

        return question;
    }

    private List<AnswerOptionDTO> createOptions(QuestionDTO source) {
        List<AnswerOptionDTO> options = new ArrayList<>();

        int optionIndex = 0;
        for (ValueItemDTO choice : source.getChoices()) {
            AnswerOptionDTO option = AnswerOptionDTO.builder().name(choice.getText()).type(AnswerOptionType.SELECTION)
                .description(sanitizeHtmlText(choice.getInfoText())).index(optionIndex++).value(choice.getValue()).build();
            options.add(option);
        }

        if (Boolean.TRUE.equals(source.getHasOther())) {
            AnswerOptionDTO otherOption =
                AnswerOptionDTO.builder().name("Other").type(AnswerOptionType.TEXT).description("Other Option").index(options.size()).build();
            options.add(otherOption);
        }

        return options;
    }

    /**
     * Sanitizite the given HTML text.
     *
     * @see <a href="https://en.wikipedia.org/wiki/HTML_sanitization">https://en.wikipedia.org/wiki/HTML_sanitization</a>
     * @param html the HTML text to sanitize
     * @return the santized text
     */
    public static String sanitizeHtmlText(String html) {
        String result = null;
        if (html != null) {
            PolicyFactory policy = new HtmlPolicyBuilder().toFactory();
            result = policy.sanitize(html);
            result = HtmlUtils.htmlUnescape(result);
        }
        return result;
    }
}
