package com.mainlevel.monitoring.admin.api.dto.template;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding information about a question.
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {

    @ApiModelProperty("Name of question, should be unique per survey.")
    @NotNull(message = "notnull")
    private String name;

    @ApiModelProperty("Title of survey")
    private String title;

    @ApiModelProperty("Defined choices for question(type=[single|multiple|dropdown])")
    private List<ValueItemDTO> choices;

    @ApiModelProperty("Additional description for the question.")
    private String description;

    @ApiModelProperty("Is the question required.")
    private Boolean isRequired;

    @ApiModelProperty("List of validators for the question.")
    private List<ValidatorDTO> validators;

    @ApiModelProperty("Suffix for value filed(i.e. $, EURO, %)")
    private String suffix;

    @ApiModelProperty("Flags determinates that the question has 'Other optinons'")
    private Boolean hasOther;

    @ApiModelProperty("In case of question with choices, contains name of exclusive choice.")
    private String choiceExclusive;

    @ApiModelProperty("List of triggers(page,quesiton) for the question.")
    private List<TriggerDTO> triggers;

    @ApiModelProperty("List of rows for question(type=[staticTable].")
    private List<ValueItemDTO> rows;

    @ApiModelProperty("List of columns for question(type=[staticTable|dynamicTable|matrix].")
    private List<ValueItemDTO> columns;

    @ApiModelProperty("The visibility flag.")
    private Boolean visible;

    @ApiModelProperty("Contains type of predefined list(i.e. coutnry, region, degree).")
    private String predefinedListType;

    @ApiModelProperty("The type of question list(i.e. text, number, ...).")
    private String type;
    
    @ApiModelProperty("The view configuration for date questions (day, month, year)")
    private String view;
}
