package com.mainlevel.monitoring.admin.repository.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * MongoDB entity for an activated survey.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false, exclude = {"template"})
@Document(collection = "survey")
public class SurveyTemplateEntity {

    @Id
    private String id;

    private String surveyName;

    private String description;

    @Version
    private Long version;

    @Transient
    private TemplateEntity template;

    @Transient
    private Integer numberOfPublished;

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date lastModifiedDate;

    private long updatedById;

    private String updatedByName;

    private Boolean active;

    @DBRef
    private UnitEntity unit;

    @DBRef
    private LanguageEntity language;

    private String status;

}
