package com.mainlevel.monitoring.survey.database.queryresult;

import java.util.Date;

import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.typeconversion.DateLong;
import org.springframework.data.neo4j.annotation.QueryResult;

import com.mainlevel.monitoring.survey.api.dto.ClientType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Contains overview version
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
@QueryResult
public class ReportingPeriodOverview {

    @Property(name = "ID(s)")
    private Long surveyGid;

    @Property(name = "ID(rp)")
    private Long reportPeriodGid;

    @Property(name = "s.templateRefId")
    private String templateRefId;

    @Property(name = "s.templateVersion")
    private Long templateVersion;

    @Property(name = "s.templateTitle")
    private String templateTitle;

    @Property(name = "ou.name")
    private String unitName;

    @Property(name = "role")
    private String role;

    @DateLong
    @Property(name = "rp.start")
    private Date start;

    @DateLong
    @Property(name = "rp.end")
    private Date end;

    @Property(name = "au.name")
    private String author;

    @Property(name = "own.name")
    private String originalOwner;

    @Property(name = "l.owner")
    private String owner;

    @DateLong
    @Property(name = "rp.created")
    private Date creationTime;

    @DateLong
    @Property(name = "ps.time")
    private Date lastEdit;

    @Property(name = "c.username")
    private String editedByUsername;

    @Property(name = "clientAddress")
    private String editedByAddress;

    @Property(name = "clientType")
    private ClientType editedByClient;

    @Property(name = "c.name")
    private String editedBy;

    @Property(name = "rp.status")
    private String status;

    @Property(name = "numberOfQuestions")
    private Integer numberOfQuestions;

    @Property(name = "numberOfAnsweredQuestions")
    private Integer numberOfAnsweredQuestions;

}
