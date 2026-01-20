package com.sarthi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InspectionQtySummaryResponse {

    private int acceptedQty;
    private int totalOfferedQty;
    private int totalManufactureQty;
}

