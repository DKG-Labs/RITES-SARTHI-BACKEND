package com.sarthi.dto;

import java.time.LocalDate;

public record InspectionDataDto(
    String icNumber,
    String poNo,
    String vendorId,
    String typeOfCall,
    LocalDate desiredInspectionDate,
    String placeOfInspection
) {}
