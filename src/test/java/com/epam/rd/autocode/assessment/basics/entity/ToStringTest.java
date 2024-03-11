package com.epam.rd.autocode.assessment.basics.entity;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToStringTest {
    static final Locale DEFAULT = Locale.getDefault();

    @BeforeAll
    static void setUp() {
        Locale.setDefault(new Locale("en"));
    }

    @AfterAll
    static void tearDown() {
        Locale.setDefault(DEFAULT);
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/entity/employee.csv", numLinesToSkip = 1)
    void testEmployeeToString(long id, String email, String password, String name,
                              String phone, String dateOfBirth, String expected) {
        LocalDate date;
        date = LocalDate.parse(dateOfBirth);
        String actual = new Employee(id, email, password, name, phone, date).toString();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/entity/client.csv", numLinesToSkip = 1)
    void testClientToString(long id, String email, String password, String name,
                            BigDecimal balance, String driverLicense, String expected) {

        String actual = new Client(id, email, password, name, balance, driverLicense).toString();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/entity/vehicle.csv", numLinesToSkip = 1)
    void testVehicleToString(long id, String make, String model, String characteristics,
                             int yearOfProduction, long odometer, String color,
                             String licensePlate, int numberOfSeats, BigDecimal price,
                             char requiredLicense, BodyType bodyType, String expected) {

        String actual = new Vehicle(id, make, model, characteristics, yearOfProduction,
                odometer, color, licensePlate, numberOfSeats, price, requiredLicense,
                bodyType).toString();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/entity/order.csv", numLinesToSkip = 1)
    void testOrderToString(long id, long clientId, long employeeId, long vehicleId,
                           String startTime, String endTime, BigDecimal price,
                           String expected) {

        LocalDateTime sd;
        LocalDateTime ed;
        sd = LocalDateTime.parse(startTime);
        ed = LocalDateTime.parse(endTime);
        String actual = new Order(id, clientId, employeeId, vehicleId, sd, ed, price)
                .toString();
        assertEquals(expected, actual);
    }
}
