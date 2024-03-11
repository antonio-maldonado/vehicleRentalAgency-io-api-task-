package com.epam.rd.autocode.assessment.basics.service;

import com.epam.rd.autocode.assessment.basics.entity.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CsvStorageImplIntegrationTest {
    static final String EOL = System.lineSeparator();

    public static Stream<Arguments> casesDefaultRead() {
        return Stream.of(
                Arguments.of(
                        """
                                id,email,password,name,balance,driverLicense
                                1,a@a.a,p,n,1,A
                                1,"","","",,""
                                ,,,,,
                                """,
                        List.of(
                                new Client(1, "a@a.a", "p", "n", new BigDecimal("1"), "A"),
                                new Client(1, "", "", "", null, ""),
                                new Client(0, null, null, null, null, null))
                )
        );
    }

    @ParameterizedTest
    @MethodSource("casesDefaultRead")
    void testDefaultRead(String csvData, List<Client> expected) {
        CsvStorage storage = new CsvStorageImpl();
//         = "";
        ByteArrayInputStream bis = new ByteArrayInputStream(csvData.getBytes());
        try {
            List<Client> clients = storage.read(bis, Mapper::csvToClient);
            assertIterableEquals(expected, clients);
        } catch (IOException e) {
            fail(e);
        }
    }

    public static Stream<Arguments> casesReadClient() {
        return Stream.of(
                Arguments.of(
                        "src/test/resources/service/clients.csv", "cp1251", "_", ";", "false",
                        List.of(
                                new Client(6, "Client12@acw.com", "ш#25#21&ї", "Клієнт;12", new BigDecimal("1585"), "A B C D"),
                                new Client(0, "", "", "", null, ""),
                                new Client(0, null, null, null, null, null))
                )
        );
    }

    @ParameterizedTest
    @MethodSource("casesReadClient")
    void testReadClient(String fName,
                        String encoding,
                        String quoteCharacter,
                        String valuesDelimiter,
                        String headerLine,
                        List<Client> expected) {
        Map<String, String> props = Map.of("encoding", encoding,
                "quoteCharacter", quoteCharacter,
                "valuesDelimiter", valuesDelimiter,
                "headerLine", headerLine);
        CsvStorage storage = new CsvStorageImpl(props);
        try (FileInputStream in = new FileInputStream(fName)) {
            List<Client> clients = storage.read(in, Mapper::csvToClient);
            assertIterableEquals(expected, clients);
        } catch (IOException e) {
            fail(e);
        }
    }

    public static Stream<Arguments> casesReadEmployee() {
        return Stream.of(
                Arguments.of(
                        "src/test/resources/service/employees.csv", "cp1251", "'", ";", "false",
                        List.of(
                                new Employee(1, "admin@vpa.com", "kY$60;25,IL", "Адмінь", "111-602-23-00", LocalDate.parse("1996-07-03")),
                                new Employee(0, "", "", "", "", null),
                                new Employee(0, null, null, null, null, null)
                        )
                ));
    }

    @ParameterizedTest
    @MethodSource("casesReadEmployee")
    void testReadEmployee(String fName,
                          String encoding,
                          String quoteCharacter,
                          String valuesDelimiter,
                          String headerLine,
                          List<Employee> expected) {
        Map<String, String> props = Map.of("encoding", encoding,
                "quoteCharacter", quoteCharacter,
                "valuesDelimiter", valuesDelimiter,
                "headerLine", headerLine);
        CsvStorage storage = new CsvStorageImpl(props);
        try (FileInputStream in = new FileInputStream(fName)) {
            List<Employee> employees = storage.read(in, Mapper::csvToEmployee);
            assertIterableEquals(expected, employees);
        } catch (IOException e) {
            fail(e);
        }
    }

    public static Stream<Arguments> casesReadVehicle() {
        return Stream.of(
                Arguments.of(
                        "src/test/resources/service/vehicles.csv", "cp1251", "'", ";", "false",
                        List.of(
                                new Vehicle(1, "Тойота", "Айго Хатчбек",
                                        "1.0; VVT-i; X-Trend; TSS; 5dr", 2021, 22401,
                                        "сіра", "АХ 50-10 ХА", 5, new BigDecimal("10900"),
                                        'B', BodyType.HATCHBACK),
                                new Vehicle(1, "Тойота", "Айго Хатчбек",
                                        "1.0 VVT-i X-Trend TSS 5dr", 2021, 22401,
                                        "сіра", "АХ 50-10 ХА", 5, new BigDecimal("10900"),
                                        'B', BodyType.HATCHBACK),
                                new Vehicle(0, "", "", "", 0, 0,
                                        "", "", 0, null, (char) 0, null),
                                new Vehicle(0, null, null, null, 0, 0,
                                        null, null, 0, null, (char) 0, null)
                        ))
                );
    }

    @ParameterizedTest
    @MethodSource("casesReadVehicle")
    void testReadVehicle(String fName,
                         String encoding,
                         String quoteCharacter,
                         String valuesDelimiter,
                         String headerLine,
                         List<Vehicle> expected) {
        Map<String, String> props = Map.of("encoding", encoding,
                "quoteCharacter", quoteCharacter,
                "valuesDelimiter", valuesDelimiter,
                "headerLine", headerLine);
        CsvStorage storage = new CsvStorageImpl(props);
        try (FileInputStream in = new FileInputStream(fName)) {
            List<Vehicle> vehicle = storage.read(in, Mapper::csvToVehicle);
            assertIterableEquals(expected, vehicle);
        } catch (IOException e) {
            fail(e);
        }
    }

    public static Stream<Arguments> casesReadOrder() {
        return Stream.of(
                Arguments.of(
                        "src/test/resources/service/orders.csv", "cp1251", "'", ";", "false",
                        List.of(
                                new Order(1, 1, 2, 9, LocalDateTime.parse("2022-09-04T00:00"),
                                        LocalDateTime.parse("2022-10-14T00:00"), new BigDecimal("114")),
                                new Order(0, 0, 0, 0, null, null, null),
                                new Order(0, 0, 0, 0, null, null, null)
                        )
                ));
    }

    @ParameterizedTest
    @MethodSource("casesReadOrder")
    void testReadOrder(String fName,
                         String encoding,
                         String quoteCharacter,
                         String valuesDelimiter,
                         String headerLine,
                         List<Order> expected) {
        Map<String, String> props = Map.of("encoding", encoding,
                "quoteCharacter", quoteCharacter,
                "valuesDelimiter", valuesDelimiter,
                "headerLine", headerLine);
        CsvStorage storage = new CsvStorageImpl(props);
        try (FileInputStream in = new FileInputStream(fName)) {
            List<Order> order = storage.read(in, Mapper::csvToOrder);
            assertIterableEquals(expected, order);
        } catch (IOException e) {
            fail(e);
        }
    }

    public static Stream<Arguments> casesWriteDefault() {
        return Stream.of(
                Arguments.of(
                        "1,a@a.a,p,n,1,A" + EOL +
                                "1,\"\",\"\",\"\",,\"\"" + EOL +
                                "0,,,,," + EOL,
                        List.of(
                                new Client(1, "a@a.a", "p", "n", new BigDecimal("1"), "A"),
                                new Client(1, "", "", "", null, ""),
                                new Client(0, null, null, null, null, null))
                )
        );
    }

    @ParameterizedTest
    @MethodSource("casesWriteDefault")
    void testWriteClientDefault(String expected, List<Client> clients) {
        CsvStorage storage = new CsvStorageImpl();
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            storage.write(out, clients, Mapper::clientToCsv);
            out.flush();
            assertEquals(expected, out.toString());
        } catch (IOException e) {
            fail(e);
        }
    }

    public static Stream<Arguments> casesWriteClient() {
        return Stream.of(
                Arguments.of(
                        "src/test/resources/service/clientsw.csv",
                        "cp1251", "_", ";", "false",
                        List.of(
                                new Client(6, "Client12@acw.com", "ш#25#21&ї", "Клієнт;12", new BigDecimal("1585"), "A B C D"),
                                new Client(0, "", "", "", null, ""),
                                new Client(0, null, null, null, null, null))
                )
        );
    }

    @ParameterizedTest
    @MethodSource("casesWriteClient")
    void testWriteClient(String fName,
                         String encoding,
                         String quoteCharacter,
                         String valuesDelimiter,
                         String headerLine,
                         List<Client> clients) {
        CsvStorage storage = new CsvStorageImpl(Map.of("encoding", encoding,
                "quoteCharacter", quoteCharacter,
                "valuesDelimiter", valuesDelimiter,
                "headerLine", headerLine));
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            storage.write(out, clients, Mapper::clientToCsv);
            out.flush();
            String expected = Files.readString(Path.of(fName), Charset.forName(encoding));
            assertEquals(expected, out.toString(Charset.forName(encoding)));
        } catch (IOException e) {
            fail(e);
        }
    }

    public static Stream<Arguments> casesWriteEmployee() {
        return Stream.of(
                Arguments.of(
                        "src/test/resources/service/employeesw.csv",
                        "cp1251", "'", ";", "false",
                        List.of(
                                new Employee(1, "admin@vpa.com", "kY$60;25,IL", "Адмінь", "111-602-23-00", LocalDate.parse("1996-07-03")),
                                new Employee(0, "", "", "", "", null),
                                new Employee(0, null, null, null, null, null)
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("casesWriteEmployee")
    void testWriteEmployee(String fName,
                           String encoding,
                           String quoteCharacter,
                           String valuesDelimiter,
                           String headerLine,
                           List<Employee> employees) {
        CsvStorage storage = new CsvStorageImpl(Map.of("encoding", encoding,
                "quoteCharacter", quoteCharacter,
                "valuesDelimiter", valuesDelimiter,
                "headerLine", headerLine));
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            storage.write(out, employees, Mapper::employeeToCsv);
            out.flush();
            String expected = Files.readString(Path.of(fName), Charset.forName(encoding));
            assertEquals(expected, out.toString(Charset.forName(encoding)));
        } catch (IOException e) {
            fail(e);
        }
    }

    public static Stream<Arguments> casesWriteVehicle() {
        return Stream.of(
                Arguments.of(
                        "src/test/resources/service/vehiclesw.csv", "cp1251", "'", ";", "false",
                        List.of(
                                new Vehicle(1, "Тойота", "Айго Хатчбек",
                                        "1.0; VVT-i; X-Trend; TSS; 5dr", 2021, 22401,
                                        "сіра", "АХ 50-10 ХА", 5, new BigDecimal("10900"),
                                        'B', BodyType.HATCHBACK),
                                new Vehicle(1, "Тойота", "Айго Хатчбек",
                                        "1.0 VVT-i X-Trend TSS 5dr", 2021, 22401,
                                        "сіра", "АХ 50-10 ХА", 5, new BigDecimal("10900"),
                                        'B', BodyType.HATCHBACK),
                                new Vehicle(0, "", "", "", 0, 0,
                                        "", "", 0, null, (char) 0, null),
                                new Vehicle(0, null, null, null, 0, 0,
                                        null, null, 0, null, (char) 0, null)
                        )
                ));
    }

    @ParameterizedTest
    @MethodSource("casesWriteVehicle")
    void testWriteVehicle(String fName,
                          String encoding,
                          String quoteCharacter,
                          String valuesDelimiter,
                          String headerLine,
                          List<Vehicle> vehicles) {
        CsvStorage storage = new CsvStorageImpl(Map.of("encoding", encoding,
                "quoteCharacter", quoteCharacter,
                "valuesDelimiter", valuesDelimiter,
                "headerLine", headerLine));
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            storage.write(out, vehicles, Mapper::vehicleToCsv);
            out.flush();
            String expected = Files.readString(Path.of(fName), Charset.forName(encoding));
            String actual = out.toString(Charset.forName(encoding));
//            Files.writeString(Path.of("src/test/resources/service/vehiclesw.csv"),
//                    actual, Charset.forName(encoding));
            assertEquals(expected, actual);
        } catch (IOException e) {
            fail(e);
        }
    }

    public static Stream<Arguments> casesWriteOrder() {
        return Stream.of(
                Arguments.of(
                        "src/test/resources/service/ordersw.csv", "cp1251", "'", ";", "false",
                        List.of(
                                new Order(1, 1, 2, 9, LocalDateTime.parse("2022-09-04T00:00"), LocalDateTime.parse("2022-10-14T00:00"), new BigDecimal("114")),
                                new Order(0, 0, 0, 0, null, null, null),
                                new Order(0, 0, 0, 0, null, null, null)
                        )
                ));
    }

    @ParameterizedTest
    @MethodSource("casesWriteOrder")
    void testWriteOrder(String fName,
                        String encoding,
                        String quoteCharacter,
                        String valuesDelimiter,
                        String headerLine,
                        List<Order> orders) {
        CsvStorage storage = new CsvStorageImpl(Map.of("encoding", encoding,
                "quoteCharacter", quoteCharacter,
                "valuesDelimiter", valuesDelimiter,
                "headerLine", headerLine));
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            storage.write(out, orders, Mapper::orderToCsv);
            out.flush();
            String expected = Files.readString(Path.of(fName), Charset.forName(encoding));
            assertEquals(expected, out.toString(Charset.forName(encoding)));
        } catch (IOException e) {
            fail(e);
        }
    }
}