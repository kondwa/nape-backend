package com.mainlevel.monitoring.survey.database.node;

import static org.neo4j.ogm.annotation.Relationship.OUTGOING;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import com.mainlevel.monitoring.survey.api.dto.survey.answer.SurveyAnswerType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * An graph-node representation of an answer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {"gid", "question"})
@ToString(callSuper = false)
@NodeEntity
public class Answer {

    @GraphId
    private Long gid;

    @Property
    private String value;

    @Property
    private SurveyAnswerType type;

    @Relationship(type = "selectedOption", direction = OUTGOING)
    private AnswerOption option;

    @Relationship(type = "answers", direction = OUTGOING)
    private QuestionAnswer question;

}
