package com.epam.rd.autocode.assessment.basics;

import com.epam.rd.autocode.assessment.basics.entity.Client;
import com.epam.rd.autocode.assessment.basics.entity.Employee;
import com.epam.rd.autocode.assessment.basics.entity.Order;
import com.epam.rd.autocode.assessment.basics.entity.Vehicle;
import com.epam.rd.autocode.assessment.basics.service.CsvStorage;
import com.epam.rd.autocode.assessment.basics.service.CsvStorageImpl;
import com.epam.rd.autocode.assessment.basics.service.Mapper;

import java.io.*;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        try {
            CsvStorage storage = new CsvStorageImpl();
            try (InputStream in = new FileInputStream("src/test/resources/entity/client.csv")) {
                List<Client> clients = storage.read(in, Mapper::csvToClient);
                clients.forEach(System.out::println);
            }

            try (InputStream in = new FileInputStream("src/test/resources/entity/employee.csv")) {
                List<Employee> employees = storage.read(in, Mapper::csvToEmployee);
                employees.forEach(System.out::println);
            }

            try (InputStream in = new FileInputStream("src/test/resources/entity/vehicle.csv")) {
                List<Vehicle> vehicles = storage.read(in, Mapper::csvToVehicle);
                vehicles.forEach(System.out::println);
            }

            try (InputStream in = new FileInputStream("src/test/resources/entity/order.csv");
                 OutputStream out = new FileOutputStream("order.csv")) {
                List<Order> orders = storage.read(in, Mapper::csvToOrder);
                orders.forEach(System.out::println);
                storage.write(out, orders, Mapper::orderToCsv);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
