package com.sarthi.dto.reports;

import lombok.Data;

@Data
public class ForgingDefectsDto {

    private Integer forgingTemperature;
    private Integer forgingStabilisationRejection;
    private Integer improperForging;
    private Integer forgingMarksNotches;
}

