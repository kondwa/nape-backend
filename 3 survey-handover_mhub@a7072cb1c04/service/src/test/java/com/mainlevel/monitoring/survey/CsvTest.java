/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.junit.Ignore;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class CsvTest {

    private final Pattern PATTERN = Pattern.compile("\\[\\\"(\\d*)\\\",\\\"(.*)\\\"");

    private static final String NEW_LINE_SEPARATOR = "\n";

    @Test
    @Ignore
    public void createCsv() throws Exception {

        Reader in = new FileReader("export.csv");

        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withDelimiter('|').withRecordSeparator(NEW_LINE_SEPARATOR).withHeader("Program", "Project ID",
            "Project", "Status", "Question ID", "Question", "Question Title", "Row", "Spalte 1", "Spalte 2");

        FileWriter fileWriter = new FileWriter("result.csv");
        CSVPrinter printer = new CSVPrinter(fileWriter, csvFileFormat);

        try {
            CSVParser parser = CSVFormat.DEFAULT.parse(in);

            List<CSVRecord> records = parser.getRecords();

            for (CSVRecord record : records) {
                String program = record.get(0);
                String project = record.get(1);
                String projectId = record.get(2);
                String status = record.get(3);
                String question = record.get(4);
                String questionTitle = record.get(5);
                String questionId = record.get(6);
                // String user = record.get(7);
                String row = record.get(8);

                String cols = record.get(9).replaceAll("\n", "");

                Map<Integer, String> values = this.parse(cols);

                int size = getMaxSize(values);

                List<String> printRecord = new ArrayList<>();
                printRecord.add(program);
                printRecord.add(projectId);
                printRecord.add(project);
                printRecord.add(status);
                printRecord.add(questionId);
                printRecord.add(question);
                printRecord.add(questionTitle);
                printRecord.add(row);

                for (int i = 0; i <= size; i++) {
                    String value = values.get(i);
                    printRecord.add(value);
                }

                printer.printRecord(printRecord);
            }

        } finally {
            in.close();
            fileWriter.close();
            printer.close();
        }
    }

    private Map<Integer, String> parse(String cols) {

        Map<Integer, String> values = new HashMap<>();

        String[] tokens = cols.split("],");

        for (String token : tokens) {
            Matcher matcher = PATTERN.matcher(token);
            if (matcher.find()) {
                Integer id = Integer.parseInt(matcher.group(1));
                String value = matcher.group(2);

                if (values.containsKey(id)) {
                    value = values.get(id) + " / " + value;
                }

                values.put(id, value);
            }
        }

        return values;
    }

    private int getMaxSize(Map<Integer, String> values) {

        int size = 0;

        for (Integer index : values.keySet()) {
            if (index > size) {
                size = index;
            }
        }

        return size;
    }

}
