package com.sarthi.dto.IcDtos;

import lombok.Data;

@Data
public class RmChemicalAnalysisRequestDto {

    private String heatNumber;

    private Double carbon;
    private Double manganese;
    private Double silicon;
    private Double sulphur;
    private Double phosphorus;
    private Double chromium;
}
