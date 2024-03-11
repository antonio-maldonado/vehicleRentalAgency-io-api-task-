package com.epam.rd.autocode.assessment.basics.service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CsvStorageImpl implements CsvStorage {
    private String encoding;
    private String quoteCharacter;
    private String valuesDelimiter;
    private boolean headerLine;

    public CsvStorageImpl() {
    }

    /**
     * @param props configuration properties.<br>
     *              {@code String encoding} default: {@code 'UTF-8'} - The encoding to use when reading the CSV files; must be a valid charset.<br>
     *              {@code String quoteCharacter} default: {@code '"'} - The quote character to use for <em>quoted strings</em>. <br>
     *              {@code String valuesDelimiter} default: {@code ','} - The column delimiter character to use when reading the CSV file.<br>
     *              {@code boolean headerLine} default: {@code 'true'} - The first line considered as headers and should be ignored
     */
    public CsvStorageImpl(Map<String, String> props) {
        // place your code here
        encoding = props.getOrDefault("encoding", "UTF-8");
        quoteCharacter = props.getOrDefault("quoteCharacter", "\"");
        valuesDelimiter = props.getOrDefault("valuesDelimiter", ",");
        headerLine = Boolean.parseBoolean(props.getOrDefault("headerLine", "true"));
    }

    /**
     * Reads data from an {@code InputStream}. The data should be a valid
     * csv document.
     * @param source an InputStream to read data from.
     * @param mapper a function that is called for each line of csv file,
     *               which converted to a string array.
     * @return List of objects.
     */
    @Override
    public <T> List<T> read(InputStream source, Function<String[], T> mapper) throws IOException {
        List<T> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(source))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                T object = mapper.apply(parts);
                result.add(object);
            }
        }
        return result;
    }

    /**
     * Write a List of objects to a OutputStream in csv format.
     * @param dest an OutputStream to write csv document to
     * @param values a List of objects to be written
     * @param mapper a function that is called for each object to transform it to csv format.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public <T> void write(OutputStream dest, List<T> values, Function<T, String[]> mapper) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(dest))) {
            for (T value : values) {
                String[] fields = mapper.apply(value);
                String line = String.join(",", fields);
                writer.write(line);
                writer.newLine();
            }
        }
    }

}
