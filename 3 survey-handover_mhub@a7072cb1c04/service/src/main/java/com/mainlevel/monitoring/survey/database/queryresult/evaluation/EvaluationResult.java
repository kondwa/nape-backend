/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.database.queryresult.evaluation;

import org.springframework.data.neo4j.annotation.QueryResult;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Neo4j evaluation result containing eventy by a specific country.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@QueryResult
@ToString(callSuper = false)
public class EvaluationResult {

    private String title;

    private String recordName;

    private String xAxisValue;

    private Number yAxisValue;

    private String xAxisDesc;

    private String yAxisDesc;

}
