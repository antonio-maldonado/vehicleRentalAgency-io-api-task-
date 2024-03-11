package com.epam.rd.autocode.assessment.basics.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

class GetSetTest {
    @Test
    void testVehicle() {
        GetSetVerifier verifier = new GetSetVerifier(Vehicle.class);
        verifier.verify("id", 1L, 2L);
        verifier.verify("make", "one", "two");
        verifier.verify("model", "one", "two");
        verifier.verify("characteristics", "one", "two");
        verifier.verify("yearOfProduction", 1, 2);
        verifier.verify("odometer", 1L, 2L);
        verifier.verify("color", "one", "two");
        verifier.verify("licensePlate", "one", "two");
        verifier.verify("numberOfSeats", 1, 2);
        verifier.verify("price", BigDecimal.ZERO, BigDecimal.ONE);
        verifier.verify("requiredLicense", 'a', 'b');
        verifier.verify("bodyType", BodyType.HATCHBACK, BodyType.SALOON);
    }

    @Test
    void testOrder() {
        GetSetVerifier verifier = new GetSetVerifier(Order.class);
        verifier.verify("id", 1L, 2L);
        verifier.verify("clientId", 1L, 2L);
        verifier.verify("employeeId", 1L, 2L);
        verifier.verify("vehicleId", 1L, 2L);
        verifier.verify("startTime", LocalDateTime.now(), LocalDateTime.now().plusDays(3));
        verifier.verify("endTime", LocalDateTime.now(), LocalDateTime.now().plusDays(3));
        verifier.verify("price", BigDecimal.ZERO, BigDecimal.ONE);
    }

    @Test
    void testClient() {
        GetSetVerifier verifier = new GetSetVerifier(Client.class);
        verifier.verify("id", 1L, 2L);
        verifier.verify("email", "one", "two");
        verifier.verify("password", "one", "two");
        verifier.verify("balance", BigDecimal.ZERO, BigDecimal.ONE);
        verifier.verify("driverLicense", "A", "B C");
    }

    @Test
    void testEmployee() {
        GetSetVerifier verifier = new GetSetVerifier(Employee.class);
        verifier.verify("id", 1L, 2L);
        verifier.verify("email", "one", "two");
        verifier.verify("password", "one", "two");
        verifier.verify("phone", "one", "two");
        verifier.verify("dateOfBirth", LocalDate.now(), LocalDate.now().plusDays(3));
    }
}
