package com.epam.rd.autocode.assessment.basics.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.function.Function;

public interface CsvStorage {
    /**
     * Reads data from an {@code InputStream}. The data should be a valid
     * csv document.
     * @param source an InputStream to read data from.
     * @param mapper a function that is called for each line of csv file,
     *               which converted to a string array.
     * @return List of objects.
     */
    <T> List<T> read(InputStream source, Function<String[], T> mapper) throws IOException;

    /**
     * Write a List of objects to a OutputStream in csv format.
     * @param dest an OutputStream to write csv document to
     * @param values a List of objects to be written
     * @param mapper a function that is called for each object to transform it to csv format.
     * @throws IOException if an I/O error occurs.
     */
    <T> void write(OutputStream dest, List<T> values, Function<T, String[]> mapper) throws IOException;
}
