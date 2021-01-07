package com.mainlevel.monitoring.survey.database.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.mainlevel.monitoring.survey.database.node.ParticipantSession;

/**
 * It extends default GraphRepository and has additional methods,
 * witches provide search participant for given participantId.
 */
public interface ParticipantSessionRepository extends GraphRepository<ParticipantSession> {

    /**
     * Find participant session by given reporting period graph id..
     *
     * @param reportingPeriodGid reporting period id
     * @return list of participant sessions
     */
    @Query("MATCH (p:ParticipantSession)-[:forPeriod]->(s:ReportingPeriod) WHERE ID(s) = {reportingPeriodGid} RETURN p")
    List<ParticipantSession> findByPeriodId(@Param("reportingPeriodGid") Long reportingPeriodGid);

    /**
     * Find participant session including answers by given reporting period graph id..
     *
     * @param reportingPeriodGid reporting period id
     * @return list of participant sessions
     */
    @Query("MATCH p=(q:Question)<-[:answers]-(a:Answer)<-[:answered]-(ps:ParticipantSession)-[:forPeriod]->(rp:ReportingPeriod) WHERE ID(rp) = {reportingPeriodGid} OPTIONAL MATCH op=(a)-[:selectedOption]->(o:AnswerOption) RETURN ps, nodes(p), rels(p), nodes(op), rels(op)")
    ParticipantSession findByPeriodIdWithAnswers(@Param("reportingPeriodGid") Long reportingPeriodGid);

    /**
     * Create relationship between participant session and the given answer ids
     *
     * @param participantSessionGid participant session id
     * @param answerGids list of anwser ids
     */
    @Query("UNWIND {answerGids} as answerId MATCH (a:Answer) WHERE ID(a) = answerId MATCH (ps:ParticipantSession) WHERE ID(ps) = {participantSessionGid} CREATE (ps)-[:answered]->(a)")
    void createParticipantSessionAnswerRelation(@Param("participantSessionGid") Long participantSessionGid,
        @Param("answerGids") List<Long> answerGids);

    /**
     * Create relationship between participant session and the given answer ids
     *
     * @param participantSessionGid participant session id
     * @param reportingPeriodGid reporting period id
     */
    @Query("MATCH (ps:ParticipantSession)-[rel:forPeriod]->(rp:ReportingPeriod) WHERE ID(ps) = {participantSessionGid} AND ID(rp) = {reportingPeriodGid} DELETE rel")
    void deleteRelationToReportingPeriod(@Param("participantSessionGid") Long participantSessionGid,
        @Param("reportingPeriodGid") Long reportingPeriodGid);

}
