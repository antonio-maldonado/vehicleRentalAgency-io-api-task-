package com.epam.rd.autocode.assessment.basics.service;

import com.epam.rd.autocode.assessment.basics.entity.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Mapper {
    public static Client csvToClient(String[] values) {
//        if (values.length != 6) {
//            throw new IllegalArgumentException();
//        }

        long id = Long.parseLong(values[0]);
        String email = values[1];
        String password = values[2];
        String name = values[3];
        BigDecimal balance = new BigDecimal(values[4]);
        String driverLicense = values[5];

        return new Client(id, email, password, name, balance, driverLicense);
    }

    public static Employee csvToEmployee(String[] values) {
//        if (values.length != 6) {
//            throw new IllegalArgumentException();
//        }

        long id = Long.parseLong(values[0]);
        String email = values[1];
        String password = values[2];
        String name = values[3];
        String phone = values[4];
        LocalDate dateOfBirth = LocalDate.parse(values[5]);


        return new Employee(id, email, password, name, phone, dateOfBirth);
    }

    public static Vehicle csvToVehicle(String[] values) {
//        if (values.length != 12) {
//            throw new IllegalArgumentException();
//        }

        long id = Long.parseLong(values[0]);
        String make = values[1];
        String model = values[2];
        String characteristics = values[3];
        int yearOfProduction = Integer.parseInt(values[4]);
        long odometer = Long.parseLong(values[5]);
        String color = values[6];
        String licensePlate = values[7];
        int numberOfSeats = Integer.parseInt(values[8]);
        BigDecimal price = new BigDecimal(values[9]);
        char requiredLicense = values[10].charAt(0);
        BodyType bodyType = BodyType.valueOf(values[11]);

        return new Vehicle(id, make, model, characteristics, yearOfProduction, odometer, color, licensePlate, numberOfSeats, price, requiredLicense, bodyType);
    }

    public static Order csvToOrder(String[] values) {
//        if (values.length != 7) {
//            throw new IllegalArgumentException();
//        }

        long id = Long.parseLong(values[0]);
        long clientId = Long.parseLong(values[1]);
        long employeeId = Long.parseLong(values[2]);
        long vehicleId = Long.parseLong(values[3]);
        LocalDateTime startTime = LocalDateTime.parse(values[4]);
        LocalDateTime endTime = LocalDateTime.parse(values[5]);
        BigDecimal price = new BigDecimal(values[6]);

        return new Order(id, clientId, employeeId, vehicleId, startTime, endTime, price);
    }

    public static String[] orderToCsv(Order order) {
        String[] values = new String[7];

        values[0] = String.valueOf(order.getId());
        values[1] = String.valueOf(order.getClientId());
        values[2] = String.valueOf(order.getEmployeeId());
        values[3] = String.valueOf(order.getVehicleId());
        values[4] = order.getStartTime().toString();
        values[5] = order.getEndTime().toString();
        values[6] = order.getPrice().toString();

        return values;
    }

    public static String[] vehicleToCsv(Vehicle vehicle) {
        String[] values = new String[12];

        values[0] = String.valueOf(vehicle.getId());
        values[1] = vehicle.getMake();
        values[2] = vehicle.getModel();
        values[3] = vehicle.getCharacteristics();
        values[4] = String.valueOf(vehicle.getYearOfProduction());
        values[5] = String.valueOf(vehicle.getOdometer());
        values[6] = vehicle.getColor();
        values[7] = vehicle.getLicensePlate();
        values[8] = String.valueOf(vehicle.getNumberOfSeats());
        values[9] = vehicle.getPrice().toString();
        values[10] = String.valueOf(vehicle.getRequiredLicense());
        values[11] = vehicle.getBodyType().toString();

        return values;
    }

    public static String[] clientToCsv(Client client) {
        String[] values = new String[6];

        values[0] = String.valueOf(client.getId());
        values[1] = client.getEmail();
        values[2] = client.getPassword();
        values[3] = client.getName();
        values[4] = client.getBalance().toString();
        values[5] = client.getDriverLicense();

        return values;
    }

    public static String[] employeeToCsv(Employee employee) {
        String[] values = new String[6];

        values[0] = String.valueOf(employee.getId());
        values[1] = employee.getEmail();
        values[2] = employee.getPassword();
        values[3] = employee.getName();
        values[4] = employee.getPhone();
        values[5] = employee.getDateOfBirth().toString();

        return values;
    }
}
