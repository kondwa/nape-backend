package com.mainlevel.monitoring.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.Formatter;

/**
 * URL Date formatter for inter-service communication.
 */
public class DateUrlFormatter implements Formatter<Date> {

    /** Default Date Format */
    public final static String URL_DATE_FORMAT = "yyyyMMdd-HHmmssSSS";

    private final DateFormat urlFormat = new SimpleDateFormat(URL_DATE_FORMAT);

    @Override
    public String print(Date t, Locale locale) {
        return urlFormat.format(t);
    }

    @Override
    public Date parse(String string, Locale locale) throws ParseException {
        return urlFormat.parse(string);
    }

}
