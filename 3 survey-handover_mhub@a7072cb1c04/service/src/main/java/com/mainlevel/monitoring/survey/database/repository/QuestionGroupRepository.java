package com.mainlevel.monitoring.survey.database.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.mainlevel.monitoring.survey.database.node.QuestionGroup;

/**
 * It extends default GraphRepository and has additional methods,
 * witches provide search through QuestionGroup nodes by various attributes.
 */
public interface QuestionGroupRepository extends GraphRepository<QuestionGroup> {

    /**
     * It finds all QuestionGroupe nodes with given name, which are in relation( hasGroup) with Survey with given surveyGid.
     *
     * @param surveyGid graphId of Survey which has a group
     * @param name name of a group
     * @return a empty list or a list with on element
     */
    @Query("MATCH (t:Survey)-[:hasGroup]->(group:QuestionGroup{name:{name}}) WHERE ID(t) = {surveyGid} RETURN group")
    List<QuestionGroup> findBySurveyAndName(@Param("surveyGid") long surveyGid, @Param("name") String name);

    /**
     * Return all group of a Survey with given surveyGraphId.
     *
     * @param surveyGid graph id of the target Survey which has groups.
     * @return list of all question groups, or empty list.
     */
    @Query("MATCH (s:Survey)-[:hasGroup]->(group:QuestionGroup) WHERE ID(s) = {surveyGid} RETURN group")
    List<QuestionGroup> findBySurvey(@Param("surveyGid") long surveyGid);
}
