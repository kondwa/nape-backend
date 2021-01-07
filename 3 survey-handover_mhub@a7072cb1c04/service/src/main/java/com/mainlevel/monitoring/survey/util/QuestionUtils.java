package com.mainlevel.monitoring.survey.util;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.web.util.HtmlUtils;

/**
 * Utility functions for survey question processing.
 */
public class QuestionUtils {

    /**
     * Sanitizite the given HTML text.
     *
     * @see <a href="https://en.wikipedia.org/wiki/HTML_sanitization">https://en.wikipedia.org/wiki/HTML_sanitization</a>
     * @param html the HTML text to sanitize
     * @return the santized text
     */
    public static String sanitizeHtmlText(String html) {
        String result = null;
        if (html != null) {
            PolicyFactory policy = new HtmlPolicyBuilder().toFactory();
            result = policy.sanitize(html);
            result = HtmlUtils.htmlUnescape(result);
        }
        return result;
    }
}
