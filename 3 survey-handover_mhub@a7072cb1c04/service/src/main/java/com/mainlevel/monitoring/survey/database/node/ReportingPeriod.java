package com.mainlevel.monitoring.survey.database.node;

import static org.neo4j.ogm.annotation.Relationship.OUTGOING;

import java.util.Date;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.DateLong;

import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * A graph representation of ReportingPeriod entity. It contains:
 * - start: start of covered report period
 * - end: end of covered report period
 * - lastEdited: last edit timestamp of covered period
 * - project: to which project is report related
 * - survey: with which survey
 * - participantSession: who reported
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false, exclude = {"gid"})
@ToString(callSuper = false)
@NodeEntity
public class ReportingPeriod {

    @GraphId
    private Long gid;

    @Property
    @DateLong
    private Date start;

    @Property
    @DateLong
    private Date end;

    @Property
    @DateLong
    private Date created;
    
    @Property
    private String submissionTimestamp;
    
    @Property
    private String foreignSurveyId;

    @Property
    private ReportingPeriodStatus status;

    @Relationship(type = "inUnit", direction = OUTGOING)
    private OrganizationalUnit unit;

    @Relationship(type = "forSurvey", direction = OUTGOING)
    private Survey survey;

    @Relationship(type = "createdBy", direction = OUTGOING)
    private User user;

    @Relationship(type = "fromClient", direction = OUTGOING)
    private Client client;

}
