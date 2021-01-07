package com.mainlevel.monitoring.survey.database.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.mainlevel.monitoring.survey.database.node.Survey;
import com.mainlevel.monitoring.survey.database.queryresult.SurveyStats;

/**
 * It extends default GraphRepository and has additional methods,
 * witches provide search by various attributes.
 */
public interface SurveyRepository extends GraphRepository<Survey> {

    /**
     * Find survey including question groups and questions based on its graph id.
     *
     * @param surveyGid the survey graph id
     * @return the survey
     */
    @Query("MATCH (s:Survey) WHERE id(s) = {surveyGid} OPTIONAL MATCH p1=(s)-[:hasGroup]->(qg:QuestionGroup)<-[:inGroup]-(q:Question) OPTIONAL MATCH p2=(q)-[:hasOption]-(o:AnswerOption) OPTIONAL MATCH p3=(s)-[:inUnit]-(ou:OrganizationalUnit) return s, nodes(p1), rels(p1), nodes(p2), rels(p2), nodes(p3), rels(p3)")
    Survey findWithStructureById(@Param("surveyGid") Long surveyGid);

    /**
     * Find survey node by template id and version.
     *
     * @param templateRefId the survey unique id
     * @param templateVersion exact version of survey
     * @return survey that match by id and version, or if it doesn't exist return <code>null</code>
     */
    @Query("MATCH (s:Survey{templateRefId:{templateRefId}, templateVersion:{templateVersion}}) RETURN s")
    List<Survey> findSurveyByTemplateIdAndVersion(@Param("templateRefId") String templateRefId, @Param("templateVersion") Long templateVersion);

    /**
     * Find survey statistics for all existing surveys.
     *
     * @param username the current username
     * @param project foreign id of the project unit
     * @return the survey statistics
     */
    @Query("MATCH (s:Survey)-[:createdBy]->(c:User) " + "WHERE s.status = 'ACTIVE' WITH s,c.name as createdBy "
        + "MATCH (s)-[:inUnit]->(ou:OrganizationalUnit)-[:inUnit*0..]->(:OrganizationalUnit)<-[:participates]-(u:User) WHERE u.username = {username} AND (ou.foreignId = {project} OR {project} IS NULL) WITH s, createdBy, ou.name AS unitName "
        + "OPTIONAL MATCH (s)-[:hasGroup]->(qg:QuestionGroup) WITH s, createdBy, unitName, COUNT(qg.name) as numberOfGroups "
        + "OPTIONAL MATCH (s)-[:hasGroup]->(:QuestionGroup)<-[:inGroup]-(q:Question) WITH s, createdBy, unitName, numberOfGroups,  COUNT(q.name) as numberOfQuestions "
        + "OPTIONAL MATCH (s)<-[:forSurvey]-(rp:ReportingPeriod) WHERE NOT rp.status = 'OBSOLETE' WITH s, createdBy, unitName, numberOfGroups, numberOfQuestions, rp.status AS status "
        + "RETURN ID(s) as gid, s.name AS surveyName, s.templateTitle AS templateName, s.templateVersion AS templateVersion, s.templateRefId AS templateRefId, s.creationTime as creationTime, s.targetInstances as targetInstances, createdBy, unitName, s.type as type, s.visibility as visibility, numberOfGroups, numberOfQuestions, status, COUNT(status) AS numberOfInstances")
    List<SurveyStats> findSurveyStats(@Param("username") String username, @Param("project") String project);
}
