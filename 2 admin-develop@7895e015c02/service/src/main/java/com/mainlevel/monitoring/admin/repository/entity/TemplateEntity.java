package com.mainlevel.monitoring.admin.repository.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * An object representation of the SurveyEditor.Template JSON.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false, exclude = "survey")
@Document(collection = "surveyTemplate")
public class TemplateEntity {

    @Id
    private String id;

    @Indexed
    private Long version;

    @DBRef
    private SurveyTemplateEntity survey;

    private String title;

    private List<PageEntity> pages;

    private Date updated;

    private long updatedById;

    private String updatedByName;

    private Boolean active;

    private String status;

    @Transient
    private Integer numberOfPublished;

    private String description;

}
