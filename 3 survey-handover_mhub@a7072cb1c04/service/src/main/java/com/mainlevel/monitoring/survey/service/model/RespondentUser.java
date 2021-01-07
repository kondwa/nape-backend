package com.mainlevel.monitoring.survey.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * A user(regular and temporary) which is responsible for given project and reporting period.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class RespondentUser {

    private String userId;

    private String fullName;

    private String role;

    private String email;

}
