package com.mainlevel.monitoring.survey.database.node;

import static org.neo4j.ogm.annotation.Relationship.OUTGOING;

import java.util.Date;
import java.util.List;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.DateLong;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * An graph-node representation of a participation in a Survey.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {"gid"})
@ToString(callSuper = false, exclude = {"period", "prevVersion", "user"})
@NodeEntity
public class ParticipantSession {

    @GraphId
    private Long gid;

    @Property
    @DateLong
    private Date time;

    @Property
    private String formattedTime;

    @Relationship(type = "forPeriod", direction = OUTGOING)
    private ReportingPeriod period;

    @Relationship(type = "prevVersion", direction = OUTGOING)
    private ParticipantSession prevVersion;

    @Relationship(type = "createdBy", direction = OUTGOING)
    private User user;

    @Relationship(type = "fromClient", direction = OUTGOING)
    private Client client;

    @Relationship(type = "answered", direction = OUTGOING)
    private List<Answer> answers;

}
