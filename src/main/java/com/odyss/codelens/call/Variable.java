package com.odyss.codelens.call;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

public class Variable {
    private final String name;
    private final String type;
    private final String value;
    private final LocalDateTime timestamp;

    private static final ObjectMapper objectMapper = new ObjectMapper(); // Create a static instance

    public Variable(String name, String type, String value, LocalDateTime timestamp) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.timestamp = timestamp;
    }

    public static Variable fromObject(String name, Object obj, LocalDateTime timestamp) {
        if (obj == null) {
            return new Variable(name, "null", null, timestamp);
        }
        String typeName = obj.getClass().getName();
        String stringValue;
        try {
            stringValue = objectMapper.writeValueAsString(obj); // Use ObjectMapper
        } catch (Exception e) {
            stringValue = obj.toString();
        }
        return new Variable(name, typeName, stringValue, timestamp);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
