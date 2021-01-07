package com.mainlevel.monitoring.survey.util;

import static com.mainlevel.monitoring.survey.util.QuestionUtils.sanitizeHtmlText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class QuestionUtilsTest {

    @Test
    public void testSanitizeHtmlText() {
        assertNull(sanitizeHtmlText(null));
        assertEquals("Bla&Bla", sanitizeHtmlText("<p><b>Bla</b>&amp;Bla<p>"));
    }
}