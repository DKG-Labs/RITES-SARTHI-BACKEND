package com.sarthi.dto;

import java.math.BigDecimal;

public interface InspectionQtySummaryView {

//    Integer getAcceptedQty();
//    Integer getTotalOfferedQty();
//    Integer getTotalManufactureQty();
//    Integer getTotalRejectedQty();

//    String getLotNumber();
//    Integer getAcceptedQty();
//    Integer getTotalOfferedQty();
//    Integer getTotalManufactureQty();
//    BigDecimal getTotalRejectedQty();

    String getLotNumber();

    Integer getAcceptedQty();

    Integer getManufacturedQty();

   Integer getRejectedQty();
}