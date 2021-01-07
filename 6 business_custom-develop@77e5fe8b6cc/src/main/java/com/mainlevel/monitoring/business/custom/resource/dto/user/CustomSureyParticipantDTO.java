/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.dto.user;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding a user.
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CustomSureyParticipantDTO {

    @ApiModelProperty("Graph ID of the user")
    private Long graphId;

    @ApiModelProperty("Username of the user")
    private String username;

    @ApiModelProperty("Name of the user")
    private String name;

    @ApiModelProperty("Expiry date of the user")
    private Date expiryDate;

    @ApiModelProperty("Whether to send an invitation email or not")
    private Boolean sendInvitation;

    @ApiModelProperty("An optional invitation text for the participant.")
    private String invitationText;

}
