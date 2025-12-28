package com.sarthi.enums;

/**
 * Enum for Inspection Call Type
 * Maps to database ENUM('Raw Material','Process','Final')
 */
public enum TypeOfCall {
    RAW_MATERIAL("Raw Material"),
    PROCESS("Process"),
    FINAL("Final");

    private final String value;

    TypeOfCall(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Convert from database value to enum
     */
    public static TypeOfCall fromValue(String value) {
        for (TypeOfCall type : TypeOfCall.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown type of call: " + value);
    }

    @Override
    public String toString() {
        return this.value;
    }
}

