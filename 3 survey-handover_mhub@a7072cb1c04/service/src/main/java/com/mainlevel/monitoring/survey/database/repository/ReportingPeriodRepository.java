package com.mainlevel.monitoring.survey.database.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.mainlevel.monitoring.survey.database.node.ReportingPeriod;
import com.mainlevel.monitoring.survey.database.queryresult.ReportingPeriodOverview;

/**
 * It extends default GraphRepository and has additional methods,
 * witches provide search by various attributes.
 */
public interface ReportingPeriodRepository extends GraphRepository<ReportingPeriod> {

    /**
     * Return overview list of reporting periods, with optional filtering by organizational unit.
     *
     * @param unitId reference id of the organizational unit (optional)
     * @param username username of the user
     * @return list of reporting period overview information.
     */
    // TODO: Refactor like findOverviews(long, string)
    @Query("MATCH (ou:OrganizationalUnit)<-[:inUnit]-(rp:ReportingPeriod)-[:forSurvey]->(s:Survey) WHERE {unitId} IS NULL OR ou.refId = {unitId} WITH ou, rp, s MATCH (ou)-[:inUnit*0..]->(p:OrganizationalUnit)<-[r:participates]-(u:User) WHERE u.username = {username} OPTIONAL MATCH (rp)<-[:forPeriod]-(ps:ParticipantSession)-[:createdBy]->(c:User) RETURN ID(s), ID(rp), s.templateTitle, s.templateRefId, s.templateVersion, ou.name, rp.start, rp.end, ps.time, c.username, c.name, rp.status")
    List<ReportingPeriodOverview> findOverviews(@Param("unitId") String unitId, @Param("username") String username);

    /**
     * Return overview list of reporting periods, with optional filtering by organizational unit.
     *
     * @param surveyGid graph id of the survey
     * @param username username of the user
     * @return list of reporting period overview information.
     */
    @Query("MATCH (rp:ReportingPeriod)-[:forSurvey]->(s:Survey)-[:inUnit]->(ou:OrganizationalUnit) "
        + "WHERE id(s) = {surveyGid} AND NOT rp.status = 'OBSOLETE' WITH ou, rp, s "
        + "MATCH (ou)-[:inUnit*0..]->(p:OrganizationalUnit)<-[r:participates]-(u:User) WHERE u.username = {username} WITH ou, rp, s, u, r.role as role "
        + "OPTIONAL MATCH (rp)<-[:forPeriod]-(ps:ParticipantSession)-[:createdBy]->(c:User) WITH s, rp, ou, u, role, ps, c "
        + "OPTIONAL MATCH (rp)-[:createdBy]->(au:User) WITH s, rp, ou, u, role, ps, c, au "
        + "OPTIONAL MATCH (rp)<-[:participates {role:\"OWNER\"}]-(own:User) WITH s, rp, ou, u, role, ps, c, au, own "
        + "OPTIONAL MATCH (rp)<-[:forPeriod]-(l:Link) WITH s, rp, ou, u, role, ps, c, au, own, l "
        + "OPTIONAL MATCH (ps)-[:answered]->(a:Answer)-[:answers]->(aq:Question) WITH s, rp, ou, u, role, ps, c, au, own, l, COUNT(DISTINCT aq) AS numberOfAnsweredQuestions  "
        + "OPTIONAL MATCH (s)-[:hasGroup]->(qg:QuestionGroup)<-[:inGroup]-(q:Question) WITH s, rp, ou, u, role, ps, c, au, own, l, numberOfAnsweredQuestions, COUNT(q) AS numberOfQuestions  "
        + "OPTIONAL MATCH (ps)-[:fromClient]->(cl:Client) WITH s, rp, ou, u, role, ps, c, au, own, l, numberOfAnsweredQuestions, numberOfQuestions, cl.address as clientAddress, cl.type as clientType "
        + "WHERE NOT role = \"ASSISTANT\" or id(c) = id(u) "
        + "RETURN ID(s), ID(rp), s.templateTitle, s.templateRefId, s.templateVersion, ou.name, role, rp.start, rp.end, ps.time, c.username, c.name, rp.status, rp.created, au.name, own.name, l.owner, numberOfQuestions, numberOfAnsweredQuestions, clientAddress, clientType "
        + "ORDER by ps.time DESC, rp.created DESC")
    List<ReportingPeriodOverview> findOverviews(@Param("surveyGid") Long surveyGid, @Param("username") String username);

    /**
     * Load the path for the reporting period.
     *
     * @param reportingPeriodGid id of the reporting period
     * @return the survey instance path
     */
    @Query("MATCH p=(rp:ReportingPeriod)-[:forSurvey]->(s:Survey)-[:hasGroup]->(qg:QuestionGroup)<-[:inGroup]-(q:Question) WHERE id(rp) = {reportingPeriodGid} OPTIONAL MATCH tp=(q)-[:hasTrigger]->(t:QuestionTrigger)-[:triggersQuestion]->(:Question) OPTIONAL MATCH op=(q)-[:hasOption]->(o:AnswerOption) OPTIONAL MATCH qp=(q)-[:hasRow]->(r:MatrixQuestionRow) RETURN rp, nodes(p), rels(p), nodes(tp), rels(tp), nodes(op), rels(op), nodes(qp), rels(qp)")
    ReportingPeriod findReportingPeriodWithSurvey(@Param("reportingPeriodGid") Long reportingPeriodGid);

    /**
     * Find the distinct start dates of all existing reporting periods.
     *
     * @return the list of start dates
     */
    @Query("MATCH (rp:ReportingPeriod) WITH toInt(rp.start) as startDate RETURN DISTINCT startDate ORDER BY startDate")
    List<Long> findStartTimes();
    
    List<ReportingPeriod> findByForeignSurveyId(String foreignSurveyId);
}
