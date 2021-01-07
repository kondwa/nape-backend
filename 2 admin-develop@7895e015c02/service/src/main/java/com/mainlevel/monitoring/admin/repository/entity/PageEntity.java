package com.mainlevel.monitoring.admin.repository.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * An object representation of the SurveyEditor.PageEntity JSON.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class PageEntity {

    private String name;

    private String title;

    private Boolean visible;

    private String description;

    private List<QuestionEntity> questions;

}
