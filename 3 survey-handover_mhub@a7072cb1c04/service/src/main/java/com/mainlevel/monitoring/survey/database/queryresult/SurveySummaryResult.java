/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.database.queryresult;

import org.neo4j.ogm.annotation.Property;
import org.springframework.data.neo4j.annotation.QueryResult;

import com.mainlevel.monitoring.survey.api.dto.QuestionType;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Result entry for the survey summary report.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@QueryResult
public class SurveySummaryResult {

    @Property(name = "unitName")
    private String unitName;

    @Property(name = "surveyName")
    private String surveyName;

    @Property(name = "creator")
    private String creator;

    @Property(name = "instanceId")
    private Long reportingPeriodId;

    @Property(name = "status")
    private ReportingPeriodStatus status;

    @Property(name = "questionGroup")
    private String questionGroup;

    @Property(name = "questionGroupId")
    private Long questionGroupId;

    @Property(name = "questionIndex")
    private Integer questionIndex;

    @Property(name = "questionTitle")
    private String questionTitle;

    @Property(name = "questionId")
    private Long questionId;

    @Property(name = "questionType")
    private QuestionType questionType;

    @Property(name = "answer")
    private Object answer;

}
