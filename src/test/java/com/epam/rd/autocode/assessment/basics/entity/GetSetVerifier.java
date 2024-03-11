package com.epam.rd.autocode.assessment.basics.entity;

import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class GetSetVerifier {

    private final Class<?> type;
    List<Field> fields;
    List<Method> methods;

    public GetSetVerifier(Class<?> type) {
        this.type = type;
        getAllFields();
        getAllMethods();
    }

    private void getAllFields() {
        fields = new ArrayList<>();
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        for (Class<?> c = type.getSuperclass(); c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.stream(c.getDeclaredFields())
                    .filter(f -> !Modifier.isPrivate(f.getModifiers()) || !Modifier.isStatic(f.getModifiers()))
                    .toList());
        }
    }

    private void getAllMethods() {
        methods = new ArrayList<>();
        methods.addAll(Arrays.asList(type.getDeclaredMethods()));
        for (Class<?> c = type.getSuperclass(); c != null; c = c.getSuperclass()) {
            methods.addAll(Arrays.stream(c.getDeclaredMethods())
                    .filter(m -> !Modifier.isPrivate(m.getModifiers()) || !Modifier.isStatic(m.getModifiers()))
                    .toList());
        }
    }

    public Method verifyGetterExistenceForField(Field field) {
        String methodName = "get" + field.getName().substring(0, 1).toUpperCase()
                + field.getName().substring(1);
        return methods.stream().filter(m -> m.getName().equals(methodName))
                .findFirst()
                .orElseThrow(() -> new AssertionError(methodName + " in " + type + " not found."));
    }

    public Method verifySetterExistenceForField(Field field) {
        Assertions.assertFalse(Modifier.isFinal(field.getModifiers()),
                "The field '" + field.getName() + "' in '" + type + "' is final.");
        String methodName = "set" + field.getName().substring(0, 1).toUpperCase()
                + field.getName().substring(1);
        return methods.stream()
                .filter(m -> m.getName().equals(methodName) &&
                        m.getParameterCount() == 1 &&
                        m.getParameterTypes()[0] == field.getType())
                .findFirst()
                .orElseThrow(() -> new AssertionError(methodName + " in " + type + " not found."));
    }

    public void verify(String fieldName, Object one, Object two) {
        verify(getFieldByName(fieldName), one, two);
    }

    private Field getFieldByName(String fieldName) {
        List<Field> list = fields.stream().filter(f -> f.getName().equals(fieldName)).toList();
        if (list.isEmpty()) {
            fail(new NoSuchFieldException(fieldName));
        }
        return list.get(0);
    }

    private void verify(Field field, Object one, Object two) {
        Method getter = verifyGetterExistenceForField(field);
        Method setter = verifySetterExistenceForField(field);
        try {
            Constructor<?> constructor = type.getDeclaredConstructor();
            Object instance = constructor.newInstance();
            setter.invoke(instance, one);
            Object actual = getter.invoke(instance);
            assertEquals(one, actual,
                    setter.getName() + "() should set new value " +
                            "and " + getter.getName() + "() should return current value " +
                            "of " + field);
            setter.invoke(instance, two);
            actual = getter.invoke(instance);
            assertEquals(two, actual,
                    setter.getName() + "() should set new value " +
                            "and " + getter.getName() + "() should return current value " +
                            "of " + field);
        } catch (ReflectiveOperationException e) {
            String msg = String.format("Class: %s, filed name: %s%n", type, field);
            fail(msg, e);
        }
    }
}
