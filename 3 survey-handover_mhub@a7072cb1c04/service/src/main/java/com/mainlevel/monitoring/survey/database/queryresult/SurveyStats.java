/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.database.queryresult;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Property;
import org.springframework.data.neo4j.annotation.QueryResult;

import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodStatus;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyType;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyVisibilityType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Contains statistics of a survey.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@QueryResult
public class SurveyStats {

    @GraphId
    private Long gid;

    @Property
    private String surveyName;

    @Property
    private String templateName;

    @Property
    private String templateRefId;

    @Property
    private Long templateVersion;

    @Property
    private String createdBy;

    @Property
    private Long creationTime;

    @Property
    private String unitName;

    @Property
    private SurveyType type;

    @Property
    private SurveyVisibilityType visibility;

    @Property
    private ReportingPeriodStatus status;

    @Property
    private Integer targetInstances;

    @Property
    private Integer numberOfGroups;

    @Property
    private Integer numberOfQuestions;

    @Property
    private Integer numberOfInstances;

}
