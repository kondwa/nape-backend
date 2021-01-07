package com.mainlevel.monitoring.common.header;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * HTTP Header class that provides content type application/json.
 */
@Component
@SuppressWarnings("serial")
public class ContentTypeJSONHeader extends HttpHeaders {

    /**
     * Constructor for ContentTypeJSONHeader.
     */
    public ContentTypeJSONHeader() {
        setContentType(MediaType.APPLICATION_JSON);
    }
}
