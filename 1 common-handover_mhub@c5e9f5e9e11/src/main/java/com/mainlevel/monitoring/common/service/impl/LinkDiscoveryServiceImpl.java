package com.mainlevel.monitoring.common.service.impl;

import static org.apache.commons.lang3.StringUtils.isNoneBlank;
import static org.springframework.web.util.UriComponentsBuilder.fromUri;

import java.util.List;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.mainlevel.monitoring.common.service.LinkDiscoveryService;

/**
 * Default implementation of {@link LinkDiscoveryService}.
 */
@Service
public class LinkDiscoveryServiceImpl implements LinkDiscoveryService {

    private final String X_FORWARDED_PROTO = "X-Forwarded-Proto"; // X-Forwarded-Proto: https
    private final String X_FORWARDED_HOST = "X-Forwarded-Host"; // X-Forwarded-Host: www.daad.de
    private final String X_FORWARDED_PORT = "X-Forwarded-Port"; // X-Forwarded-Port: 80
    private final String X_FORWARDED_CONTEXT = "X-Forwarded-Context"; // X-Forwarded-Context: /

    private final String ZUUL = "zuul";

    @Autowired(required = false)
    private DiscoveryClient discoveryClient;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public void createRemoteURIComponentsLink(final String serviceId, Consumer<UriComponents> consumer) {
        createRemoteURIComponentsLink(serviceId, "", consumer);
    }

    @Override
    public void createRemoteURIComponentsLink(final String serviceId, final String path, Consumer<UriComponents> consumer) {
        final String servicePath = String.format("/%s", serviceId);
        UriComponentsBuilder uriComponentsBuilder = createDefaultURIComponentsBuilder(serviceId, servicePath);

        uriComponentsBuilder = enhanceUriComponentsBuilderWithForwardedHeaders(uriComponentsBuilder);
        uriComponentsBuilder.path(path);
        consumer.accept(uriComponentsBuilder.build());
    }

    private UriComponentsBuilder enhanceUriComponentsBuilderWithForwardedHeaders(final UriComponentsBuilder uriComponentsBuilder) {
        final String forwardedProtocol = httpServletRequest.getHeader(X_FORWARDED_PROTO);

        if (isNoneBlank(forwardedProtocol)) {
            uriComponentsBuilder.scheme(forwardedProtocol.trim());
        }
        final String forwardedHost = httpServletRequest.getHeader(X_FORWARDED_HOST);

        if (isNoneBlank(forwardedHost)) {
            final String[] splittedHosts = forwardedHost.trim().split(",");
            uriComponentsBuilder.host(splittedHosts[0]);
        }
        final String forwardedPort = httpServletRequest.getHeader(X_FORWARDED_PORT);

        if (isNoneBlank(forwardedPort)) {
            uriComponentsBuilder.port(forwardedPort.trim());
        }
        final String forwardedContext = httpServletRequest.getHeader(X_FORWARDED_CONTEXT);

        if (isNoneBlank(forwardedContext)) {
            final String path = uriComponentsBuilder.build().getPath();
            uriComponentsBuilder.replacePath(forwardedContext.trim()).path(path);
        }
        return uriComponentsBuilder;
    }

    private UriComponentsBuilder createDefaultURIComponentsBuilder(final String serviceId, final String servicePath) {

        if (discoveryClient != null && discoveryClient.getServices().contains(serviceId)) {
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(ZUUL);

            if (!serviceInstances.isEmpty()) {
                final ServiceInstance zuulServiceInstance = serviceInstances.get(0);
                String newServicePath = servicePath;

                if (serviceId.matches("^[a-zA-Z_\\-\\d]+_v[\\d]+$")) {
                    final int lastIndexOf = servicePath.lastIndexOf("_");
                    newServicePath = String.format("/%s/%s", servicePath.substring(lastIndexOf + 1), servicePath.substring(1, lastIndexOf));
                }
                return fromUri(zuulServiceInstance.getUri()).path(newServicePath);
            }
            // No zuul found, search for service directly

            serviceInstances = discoveryClient.getInstances(serviceId);

            if (!serviceInstances.isEmpty()) {
                final ServiceInstance concretServiceInstance = serviceInstances.get(0);
                return fromUri(concretServiceInstance.getUri());
            }
        }

        return UriComponentsBuilder.newInstance().path(servicePath);
    }
}
