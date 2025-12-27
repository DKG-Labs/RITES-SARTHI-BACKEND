package com.sarthi.enums;

/**
 * Enum for Inspection Call Status
 * Maps to database ENUM('Pending','Scheduled','Under Inspection','Approved','Rejected','Completed')
 */
public enum InspectionStatus {
    PENDING("Pending"),
    SCHEDULED("Scheduled"),
    UNDER_INSPECTION("Under Inspection"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    COMPLETED("Completed");

    private final String value;

    InspectionStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Convert from database value to enum
     */
    public static InspectionStatus fromValue(String value) {
        for (InspectionStatus status : InspectionStatus.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + value);
    }

    @Override
    public String toString() {
        return this.value;
    }
}

