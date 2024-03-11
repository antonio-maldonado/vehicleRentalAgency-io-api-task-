package com.epam.rd.autocode.assessment.basics.service;

import com.epam.rd.autocode.assessment.basics.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MapperTest {

    public static Stream<Arguments> casesCsvToClient() {
        return Stream.of(
                Arguments.of(new String[]{"1", "a@a.a", "p", "n", "1", "A"},
                        new Client(1, "a@a.a", "p", "n", new BigDecimal("1"), "A")),
                Arguments.of(new String[]{"", "\"\"",  "\"\"", "\"\"", "", "\"\""},
                        new Client(0, "", "", "", null, "")),
                Arguments.of(new String[]{"", "",  "", "", "", ""},
                        new Client(0, null, null, null, null, null))
        );
    }

    @ParameterizedTest()
    @MethodSource("casesCsvToClient")
    void csvToClient(String[] values, Client expected) {
        Client actual = Mapper.csvToClient(values);
        assertEquals(expected, actual);
    }

    public static Stream<Arguments> casesCsvToEmployee() {
        return Stream.of(
                Arguments.of(new String[]{"1", "a@a.a", "p", "n", "1", "1111-12-22"},
                        new Employee(1, "a@a.a", "p", "n", "1", LocalDate.parse("1111-12-22"))),
                Arguments.of(new String[]{ "", "\"\"",  "\"\"",  "\"\"",  "\"\"",  ""},
                        new Employee(0, "", "", "", "", null)),
                Arguments.of(new String[]{ "", "",  "",  "",  "",  ""},
                        new Employee(0, null, null, null, null, null))
        );
    }

    @ParameterizedTest()
    @MethodSource("casesCsvToEmployee")
    void csvToEmployee(String[] values, Employee expected) {
        assertEquals(expected, Mapper.csvToEmployee(values));
    }

    public static Stream<Arguments> casesCsvToVehicle() {
        return Stream.of(
                Arguments.of(new String[]{"1", "m", "md", "ch", "1", "2", "c", "lp", "1", "1", "l", "HATCHBACK"},
                        new Vehicle(1, "m", "md", "ch", 1, 2, "c",
                                "lp", 1, new BigDecimal("1"), 'l', BodyType.valueOf("HATCHBACK"))),
                Arguments.of(new String[]{"", "m", "md", "ch", "1", "2", "c", "lp", "", "", "", ""},
                        new Vehicle(0, "m", "md", "ch", 1, 2, "c",
                                "lp", 0, null, (char) 0, null)),
                Arguments.of(new String[]{null, "m", "md", "ch", "1", "2", "c", "lp", null, null, null, null},
                        new Vehicle(0, "m", "md", "ch", 1, 2, "c",
                                "lp", 0, null, (char) 0, null))
        );
    }

    @ParameterizedTest()
    @MethodSource("casesCsvToVehicle")
    void csvToVehicle(String[] values, Vehicle expected) {
        assertEquals(expected, Mapper.csvToVehicle(values));
    }

    @Test
    void csvToOrder() {
    }

    public static Stream<Arguments> casesOrderToCsv() {
        return Stream.of(
                Arguments.of(new Order(1, 1, 1, 1,
                                LocalDateTime.parse("1122-11-22T00:00"),
                                LocalDateTime.parse("1122-11-23T00:00"),
                                new BigDecimal("1")),
                        new String[]{"1", "1", "1", "1", "1122-11-22T00:00", "1122-11-23T00:00", "1"}),
                Arguments.of(new Order(1, 1, 1, 1, null, null, null),
                        new String[]{"1", "1", "1", "1", null, null, null})
        );
    }

    @ParameterizedTest()
    @MethodSource("casesOrderToCsv")
    void orderToCsv(Order value, String[] expected) {
        String[] actual = Mapper.orderToCsv(value);
        assertArrayEquals(expected, actual);
    }

    public static Stream<Arguments> casesClientToCsv() {
        return Stream.of(
                Arguments.of(
                        new Client(1, "a@a.a", "p", "n", new BigDecimal("1"), "A"),
                        new String[]{"1", "a@a.a", "p", "n", "1", "A"}
                ),
                Arguments.of(
                        new Client(1, "\"\"", null, "n", null, "A"),
                        new String[]{"1", "\"\"", null, "n", null, "A"}
                ),
                Arguments.of(
                        new Client(1, null, null, "n", null, "A"),
                        new String[]{"1", null, null, "n", null, "A"}
                )
        );
    }

    @ParameterizedTest()
    @MethodSource("casesClientToCsv")
    void clientToCsv(Client value, String[] expected) {
        assertArrayEquals(expected, Mapper.clientToCsv(value));
    }

    public static Stream<Arguments> casesEmployeeToCsv() {
        return Stream.of(
                Arguments.of(
                        new Employee(1, "a@a.a", "p", "n", "1", LocalDate.parse("1111-12-22")),
                        new String[]{"1", "a@a.a", "p", "n", "1", "1111-12-22"}
                ),
                Arguments.of(
                        new Employee(1, "\"\"", null, "n", "1", null),
                        new String[]{"1", "\"\"", null, "n", "1", null}
                ),
                Arguments.of(
                        new Employee(1, null, null, "n", "1", null),
                        new String[]{"1", null, null, "n", "1", null}
                )
        );
    }

    @ParameterizedTest()
    @MethodSource("casesEmployeeToCsv")
    void employeeToCsv(Employee value, String[] expected) {
        assertArrayEquals(expected, Mapper.employeeToCsv(value));
    }

    public static Stream<Arguments> casesVehicleToCsv() {
        return Stream.of(
                Arguments.of(
                        new Vehicle(1, "m", "md", "ch", 1, 2, "c",
                                "lp", 1, new BigDecimal("1"), 'l', BodyType.valueOf("HATCHBACK")),
                        new String[]{"1", "m", "md", "ch", "1", "2", "c", "lp", "1", "1", "l", "HATCHBACK"}
                ),
                Arguments.of(
                        new Vehicle(0, "m", "md", "ch", 1, 2, "c",
                                "lp", 0, null, (char) 0, null),
                        new String[]{"0", "m", "md", "ch", "1", "2", "c", "lp", "0", null, String.valueOf((char) 0), null}
                ),
                Arguments.of(
                        new Vehicle(0, "m", "", "ch", 1, 2, "c",
                                "lp", 0, null, (char) 0, null),
                        new String[]{"0", "m", "", "ch", "1", "2", "c", "lp", "0", null, String.valueOf((char) 0), null}
                )
        );
    }

    @ParameterizedTest()
    @MethodSource("casesVehicleToCsv")
    void vehicleToCsv(Vehicle value, String[] expected) {
        assertArrayEquals(expected, Mapper.vehicleToCsv(value));
    }
}