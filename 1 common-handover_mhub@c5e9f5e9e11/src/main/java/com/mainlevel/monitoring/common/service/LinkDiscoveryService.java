package com.mainlevel.monitoring.common.service;

import java.util.function.Consumer;

import org.springframework.web.util.UriComponents;

/**
 * Service that provides HATEAOS link discovery.
 */
public interface LinkDiscoveryService {

    /**
     * Creates a hateaos link by using the discovery service, preferres zuul if available
     *
     * @param serviceId the id of the service in the discovery client
     * @param consumer called if a link can be discovered
     */
    void createRemoteURIComponentsLink(String serviceId, Consumer<UriComponents> consumer);

    /**
     * Creates a hateaos link by using the discovery service, preferres zuul if available
     *
     * @param serviceId the id of the service in the discovery client
     * @param path uri path after service id
     * @param consumer called if a link can be discovered
     */
    void createRemoteURIComponentsLink(String serviceId, String path, Consumer<UriComponents> consumer);
}
