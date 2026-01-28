package com.sarthi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InspectionQtySummaryResponse {

//    private int acceptedQty;
//    private int totalOfferedQty;
//    private int totalManufactureQty;
//
//    private int totalRejectedQty;


    private String lotNumber;

    private Integer offeredQty;        // same for all lots

    private Integer acceptedQty;       // lot-wise (nullable)

    private Integer manufacturedQty;   // lot-wise (nullable)

    private Integer rejectedQty;


}

