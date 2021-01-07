/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.ClientDTO;
import com.mainlevel.monitoring.survey.api.dto.ClientType;
import com.mainlevel.monitoring.survey.database.node.Client;

/**
 * Converter converting {@link ClientDTO} to {@link Client}.
 */
@Component
public class ClientDTOToClientConverter extends AbstractApplicationAwareConverter<ClientDTO, Client> {

    private static final String UNDEFINED = "UNDEFINED";

    @Override
    public Client convert(ClientDTO source) {

        String address = source.getAddress() != null ? source.getAddress() : UNDEFINED;
        String browser = source.getBrowser() != null ? source.getBrowser() : UNDEFINED;
        String os = source.getOs() != null ? source.getOs() : UNDEFINED;
        ClientType type = source.getType() != null ? source.getType() : ClientType.UNDEFINED;

        Client result = Client.builder().address(address).browser(browser).os(os).type(type).build();

        return result;

    }

}
