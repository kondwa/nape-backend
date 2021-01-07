package com.mainlevel.monitoring.survey.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Provides access to the configured Neo4j Properties.
 */
@ConfigurationProperties("neo4j")
@Configuration
public class Neo4jProperties {

    private static final String COLON = ":";
    private static final String AT = "@";
    private static final String COLON_SLASH_SLASH = "://";

    private String protocoll;
    private String host;
    private String port;
    private String username;
    private String password;

    /**
     * Getter for the neo4j connection protocol.
     *
     * @return the protocol
     */
    public String getProtocoll() {
        return protocoll;
    }

    /**
     * Setter for the neo4j connection protocol.
     *
     * @param protocoll the protocol to set
     */
    public void setProtocoll(final String protocoll) {
        this.protocoll = protocoll;
    }

    /**
     * Getter for the neo4j connection host.
     *
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * Setter for the neo4j connection protocol.
     *
     * @param host the host to set
     */
    public void setHost(final String host) {
        this.host = host;
    }

    /**
     * Getter for the neo4j connection port.
     *
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * Setter for the neo4j connection protocol.
     *
     * @param port the port to set
     */
    public void setPort(final String port) {
        this.port = port;
    }

    /**
     * Getter for the neo4j connection username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the neo4j connection protocol.
     *
     * @param username the username to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Getter for the neo4j connection password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the neo4j connection protocol.
     *
     * @param password the password to set
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Getter for the complete Neo4j connection URI.
     *
     * @return A fully-qualified URI as {@link String} to the environment specific Neo4J instance.
     */
    public String getNeo4jUri() {

        final StringBuffer neo4jUri = new StringBuffer();
        neo4jUri.append(getProtocoll());
        neo4jUri.append(COLON_SLASH_SLASH);
        /*
        neo4jUri.append(getUsername());
        neo4jUri.append(COLON);
        neo4jUri.append(getPassword());
        neo4jUri.append(AT);
        */
        neo4jUri.append(getHost());
        neo4jUri.append(COLON);
        neo4jUri.append(getPort());

        return neo4jUri.toString();
    }
}
