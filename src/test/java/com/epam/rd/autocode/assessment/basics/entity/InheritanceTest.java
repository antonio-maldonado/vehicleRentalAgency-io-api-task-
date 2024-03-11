package com.epam.rd.autocode.assessment.basics.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InheritanceTest {

    @Test
    void testInheritance() {
        assertEquals(User.class, Client.class.getSuperclass(),
                "Client should extends User");
        assertEquals(User.class, Employee.class.getSuperclass(),
                "Employee should extends User");
    }

    @ParameterizedTest
    @ValueSource(classes = {Client.class, Employee.class, Vehicle.class, Order.class, User.class})
    void testImplements(Class<?> type) {
        assertTrue(getInterfaces(type).stream()
                        .anyMatch(i -> i == Serializable.class),
                "Client should implements Serializable");
    }

    static List<Class<?>> getInterfaces(Class<?> type) {
        List<Class<?>> list = new ArrayList<>();
        for (; type != Object.class; type = type.getSuperclass()) {
            list.addAll(Arrays.asList(type.getInterfaces()));
        }
        return list;
    }
}
