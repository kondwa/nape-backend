package com.mainlevel.monitoring.survey.database.node;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

import java.util.List;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import com.mainlevel.monitoring.survey.database.node.question.Question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * A graph representation of Page( a group of question, or survey chapter).
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false, exclude = {"gid", "questions"})
@ToString(callSuper = false, exclude = {"questions"})
@NodeEntity
public class QuestionGroup {

    @GraphId
    private Long gid;

    @Property
    private String name;

    @Property
    private String description;

    @Property
    private Boolean visible;

    @Property
    private Integer index;

    @Relationship(type = "inGroup", direction = INCOMING)
    private List<Question> questions;

}
