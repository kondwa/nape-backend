package com.mainlevel.monitoring.survey.database.node;

import static org.neo4j.ogm.annotation.Relationship.OUTGOING;

import java.util.Date;
import java.util.List;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.DateLong;

import com.mainlevel.monitoring.survey.api.dto.survey.SurveyStatus;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyType;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyVisibilityType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * A graph-node representation of an survey.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false, exclude = {"gid", "unit", "groups"})
@ToString(callSuper = false, exclude = {"unit", "groups"})
@NodeEntity
public class Survey {

    @GraphId
    private Long gid;

    @Property
    private String name;

    @Property
    private String templateRefId;

    @Property
    private Long templateVersion;

    @Property
    private String templateTitle;

    @Property
    private SurveyStatus status;

    @Property
    @DateLong
    private Date creationTime;

    @Relationship(type = "createdBy", direction = OUTGOING)
    private User user;

    @Property
    private SurveyType type;

    @Property
    private SurveyVisibilityType visibility;

    @Property
    private String introductionText;

    @Property
    private String closingText;

    @Property
    private Integer targetInstances;

    @Relationship(type = "inUnit", direction = OUTGOING)
    private OrganizationalUnit unit;

    @Relationship(type = "hasGroup", direction = OUTGOING)
    private List<QuestionGroup> groups;
}
