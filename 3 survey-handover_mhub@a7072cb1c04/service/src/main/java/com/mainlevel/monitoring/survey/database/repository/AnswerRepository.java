package com.mainlevel.monitoring.survey.database.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.mainlevel.monitoring.survey.database.node.Answer;

/**
 * It extends default GraphRepository and has additional methods,
 * witches provide search answer for given question and given answer attributes.
 */
public interface AnswerRepository extends GraphRepository<Answer> {

    /**
     * Find answers connected with the question by relation 'hasAnswer'.
     *
     * @param questionGraphId parent question( connected by hasAnswer relation ).
     * @return list of anwsers
     */
    @Query("MATCH (q:Question)-[:hasAnswer]->(a:Answer) WHERE ID(q) = {questionGid} RETURN a")
    List<Answer> findQuestionAnswers(@Param("questionGid") long questionGraphId);

    /**
     * Find answers of a participant session by given reporting period graph id.
     *
     * @param reportingPeriodGid reporting period id
     * @return list of answers
     */
    @Query("MATCH p=(q:Question)<-[:answers]-(a:Answer)<-[:answered]-(ps:ParticipantSession)-[:forPeriod]->(rp:ReportingPeriod) WHERE ID(rp) = {reportingPeriodGid} OPTIONAL MATCH op=(a)-[:selectedOption]->(o:AnswerOption) RETURN a, nodes(p), rels(p), nodes(op), rels(op)")
    List<Answer> findAnswersForPeriod(@Param("reportingPeriodGid") Long reportingPeriodGid);

    /**
     * Creas a relation between answer and question.
     *
     * @param answerGid id of the answer
     * @param questionGid id of the question
     * @param title relation title
     * @param order relation order
     */
    @Query("MATCH (a:Answer) where id(a) = {answerGid} MATCH (q:Question) WHERE id(q) = {questionGid} CREATE UNIQUE (a)-[r:answers]->(q) set r.title = {title}, r.order = {order}")
    void saveQuestionRelation(@Param("answerGid") Long answerGid, @Param("questionGid") Long questionGid, @Param("title") String title,
        @Param("order") Integer order);

    /**
     * Creas a relation between answer and answer option.
     *
     * @param answerGid id of the answer
     * @param optionGid id of the option
     */
    @Query("MATCH (a:Answer) where id(a) = {answerGid} MATCH (o:AnswerOption) WHERE id(o) = {optionGid} OPTIONAL MATCH (a)-[old:selectedOption]->(n) DELETE old CREATE UNIQUE (a)-[r:selectedOption]->(o)")
    void saveOptionRelation(@Param("answerGid") Long answerGid, @Param("optionGid") Long optionGid);
}
