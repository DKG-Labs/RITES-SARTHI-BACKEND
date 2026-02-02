package com.sarthi.dto.reports;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BasicDetailsDto {

    private LocalDate date;
    private String shift;
    private String rlyName;
    private String poSrNo;
    private String lotNumber;

    private Integer totalAcceptedQty;
    private Integer totalRejectionQty;
}
