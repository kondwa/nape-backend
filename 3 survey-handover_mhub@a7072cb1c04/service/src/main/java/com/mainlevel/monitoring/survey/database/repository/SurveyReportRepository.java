/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.database.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.mainlevel.monitoring.survey.database.queryresult.SurveySummaryResult;

/**
 * Neo4j repository for loading survey reports.
 */
public interface SurveyReportRepository extends GraphRepository<Void> {

    /**
     * Load the entries for report SURVEY_SUMMARY_RESULT.
     *
     * @param surveyGid the survey graph id
     * @param username username of the requesting user
     * @return the list of entries
     */
    @Query("MATCH (u:User)-[r:participates]->(:OrganizationalUnit)<-[:inUnit*0..]-(ou:OrganizationalUnit)<-[:inUnit]-(s:Survey)-[:hasGroup]->(qg:QuestionGroup)<-[:inGroup]-(q:Question) "
        + "WHERE id(s) = {surveyGid} and u.username = {username} "
        + "OPTIONAL MATCH (s)<-[:forSurvey]-(rp:ReportingPeriod)<-[:forPeriod]-(ps:ParticipantSession)-[:answered]->(a:Answer)-[:answers]->(q) "
        + "WHERE NOT rp.status = \"OBSOLETE\" " + "OPTIONAL MATCH (rp)-[:createdBy]->(c:User) "
        + "WITH ou.name AS unitName, s.name AS surveyName, ID(rp) AS instanceId, rp.status AS status, c.name AS creator, qg.name AS questionGroup, ID(qg) AS questionGroupId, q.title AS questionTitle, ID(q) AS questionId, q.index AS questionIndex, q.type AS questionType, a.value AS answer, r.role as role, id(u) as userId, id(c) as creatorId "
        + "WHERE NOT r.role = \"ASSISTANT\" or creatorId = userId "
        + "RETURN unitName, surveyName, creator, instanceId, status, questionGroup, questionGroupId, questionIndex, questionTitle, questionId, questionType, answer "
        + "ORDER BY surveyName, instanceId, questionIndex, answer")
    List<SurveySummaryResult> loadSurveySummaryReport(@Param("surveyGid") Long surveyGid, @Param("username") String username);

}
