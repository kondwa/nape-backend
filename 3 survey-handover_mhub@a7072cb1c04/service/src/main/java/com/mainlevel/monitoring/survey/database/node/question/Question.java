package com.mainlevel.monitoring.survey.database.node.question;

import static org.neo4j.ogm.annotation.Relationship.OUTGOING;

import java.util.List;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import com.mainlevel.monitoring.survey.api.dto.QuestionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * An graph-node representation of an question
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false, exclude = {"gid"})
@ToString(callSuper = false)
@NodeEntity
public abstract class Question {

    @GraphId
    private Long gid;

    @Property
    private String name;

    @Property
    private Integer index;

    @Property
    private String title;

    @Property
    private String description;

    @Property
    private QuestionType type;

    @Property
    private Boolean mandatory;

    @Property
    private Boolean visible;

    @Relationship(type = "hasTrigger", direction = OUTGOING)
    private List<QuestionTrigger> triggers;

    @Relationship(type = "hasValidator", direction = OUTGOING)
    private List<QuestionValidator> validators;

}
