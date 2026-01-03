package com.sarthi.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Utility class for generating IC Numbers
 * Format: {PREFIX}-{YEAR}-{SEQUENCE}
 * Examples: RM-IC-2025-0001, PROC-IC-2025-0001, FINAL-IC-2025-0001
 */
@Component
public class IcNumberGenerator {

    /**
     * Generate IC Number for a given type and sequence
     * @param typeOfCall - Type of inspection call (Raw Material, Process, Final)
     * @param sequence - Sequence number
     * @return Generated IC number
     */
    public String generateIcNumber(String typeOfCall, long sequence) {
        String prefix = getPrefixForType(typeOfCall);
        int currentYear = LocalDate.now().getYear();
        
        return String.format("%s-%d-%04d", prefix, currentYear, sequence);
    }

    /**
     * Get prefix for inspection call type
     */
    private String getPrefixForType(String typeOfCall) {
        switch (typeOfCall) {
            case "Raw Material":
                return "RM-IC";
            case "Process":
                return "PROC-IC";
            case "Final":
                return "FINAL-IC";
            default:
                throw new IllegalArgumentException("Invalid type of call: " + typeOfCall);
        }
    }
}

