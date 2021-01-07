package com.mainlevel.monitoring.survey.database.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.mainlevel.monitoring.survey.database.node.question.Question;

/**
 * It extends default GraphRepository and has additional methods,
 * witches provide search by various attributes.
 */
public interface QuestionRepository extends GraphRepository<Question> {

    /**
     * Find questions witches connected with the QuestionGroup by relation 'inGroup' and have same attribute questionName as given parameter
     * quesitonName
     * Cypher query:
     * "MATCH (group:QuestionGroup)-[:inGroup]->(question:Question{name:{questionName}}) WHERE ID(group) = {groupGid} RETURN question"
     *
     * @param groupGid question group id
     * @param questionName question name
     * @return the list of questions
     */
    @Query("MATCH (group:QuestionGroup)-[:inGroup]->(question:Question{name:{questionName}}) WHERE ID(group) = {groupGid} RETURN question")
    List<Question> findTemplateQuestionByName(@Param("groupGid") long groupGid, @Param("questionName") String questionName);

    /**
     * Find questions witches connected with the QuestionGroup by relation 'inGroup'.
     * Cypher query:
     * "MATCH (group:QuestionGroup)-[:inGroup]->(question:Question{name:{questionName}}) WHERE ID(group) = {groupGid} RETURN question"
     *
     * @param groupGid question group id
     * @return the list of questions
     */
    @Query("MATCH (group:QuestionGroup)-[:inGroup]->(question:Question{name:{questionName}}) WHERE ID(group) = {groupGid} RETURN question")
    List<Question> findTemplateQuestions(@Param("groupGid") long groupGid);

    /**
     * Finds exact one question with given name, which is part of the reporting period and survey with given ids
     *
     * @param reportingPeriodGid reporting period id
     * @param surveyGid survey id
     * @param questionName question name
     * @return the question
     */
    @Query("MATCH (period:ReportingPeriod)-[:withSurvey]->(survey:Survey)-[:hasGroup]->(:QuestionGroup)-[:inGroup]->(question:Question{name:{questionName}}) WHERE ID(period) = {reportingPeriodGID} AND ID(survey) = {surveyGID} RETURN question")
    public Question loadQuestionByPeriodIdSurveyIdAndName(@Param("reportingPeriodGID") Long reportingPeriodGid, @Param("surveyGID") Long surveyGid,
        @Param("questionName") String questionName);
}
