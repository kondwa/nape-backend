/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.service.AuthenticationAccessService;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Interceptor for feign clients.
 */
@Component
public class FeignClientInterceptor implements RequestInterceptor {

    @Autowired
    private AuthenticationAccessService authenticationAccessService;

    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization", authenticationAccessService.getCurrentAccessToken());
    }

}
