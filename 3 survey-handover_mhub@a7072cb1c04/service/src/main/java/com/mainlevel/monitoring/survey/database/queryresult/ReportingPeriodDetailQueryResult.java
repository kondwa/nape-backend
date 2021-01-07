/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.database.queryresult;

import java.util.List;

import org.springframework.data.neo4j.annotation.QueryResult;

import com.mainlevel.monitoring.survey.database.node.Answer;
import com.mainlevel.monitoring.survey.database.node.ReportingPeriod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Defines the path that is loaded for a single survey instance.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@QueryResult
public class ReportingPeriodDetailQueryResult {

    private List<Answer> answers;

    private ReportingPeriod reportingPeriod;

}
