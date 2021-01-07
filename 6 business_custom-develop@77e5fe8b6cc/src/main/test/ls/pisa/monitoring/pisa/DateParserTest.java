/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class DateParserTest {

    @Test
    public void testDateFormat() throws Exception {

        String date1 = prepareDate("7/28/2017");
        Assert.assertEquals("2017-07-28", date1);

        String date2 = prepareDate("2017-07-28");
        Assert.assertEquals("2017-07-28", date2);

        String date3 = prepareDate("2017-07-28T22:15:13.555Z");
        Assert.assertEquals("2017-07-28", date3);

    }

    private String prepareDate(String originalValue) {
        if (originalValue == null || originalValue.isEmpty()) {
            return originalValue;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[yyyy-MM-dd'T'HH:mm:ss.SSS'Z'][M/d/yyyy][yyyy-MM-dd]");

        LocalDate date = LocalDate.parse(originalValue, formatter);

        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
