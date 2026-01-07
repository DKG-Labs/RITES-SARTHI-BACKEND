package com.sarthi.dto.IcDtos;

import lombok.Data;

/**
 * DTO for Final Inspection Details Request
 * Contains summary information for Final inspection
 */
@Data
public class FinalInspectionDetailsRequestDto {

    // ---- RM IC REFERENCE ----
    private String rmIcNumber;

    // ---- PROCESS IC REFERENCE ----
    private String processIcNumber;

    // ---- PLACE OF INSPECTION ----
    private Integer companyId;
    private String companyName;
    private Integer unitId;
    private String unitName;
    private String unitAddress;

    // ---- SUMMARY INFORMATION ----
    private Integer totalLots;
    private Integer totalOfferedQty;
}

