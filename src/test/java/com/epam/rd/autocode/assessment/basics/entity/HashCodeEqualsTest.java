package com.epam.rd.autocode.assessment.basics.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class HashCodeEqualsTest {

    @Test
    void testHashCodeEquals() {
        EqualsVerifier.forClasses(Vehicle.class, Client.class, Employee.class, Order.class, User.class)
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS, Warning.BIGDECIMAL_EQUALITY)
                .verify();
    }
}
