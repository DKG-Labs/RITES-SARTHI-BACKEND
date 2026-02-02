package com.sarthi.dto.reports;

import lombok.Data;

@Data
public class ShearingDefectsDto {

    private Integer lengthOfCutBar;
    private Integer ovalityImproperDiaAtEnd;
    private Integer sharpEdges;
    private Integer crackedEdges;
}
