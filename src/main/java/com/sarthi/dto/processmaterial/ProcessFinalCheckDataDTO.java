package com.sarthi.dto.processmaterial;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProcessFinalCheckDataDTO {

    private Long id;
    private String inspectionCallNo;
    private String poNo;
    private String lineNo;
    private String shift;
    private Integer hourIndex;
    private String hourLabel;
    private Boolean noProduction;
    private String lotNo;
    private String visualCheck1;
    private String visualCheck2;
    private String dimensionCheck1;
    private String dimensionCheck2;
    private String hardnessCheck1;
    private String hardnessCheck2;
    private Integer rejectedNo1;
    private Integer rejectedNo2;
    private Integer rejectedNo3;

    // Separate rejection fields for each measurement
    private Integer boxGaugeRejected;
    private Integer flatBearingAreaRejected;
    private Integer fallingGaugeRejected;
    private Integer surfaceDefectRejected;
    private Integer embossingDefectRejected;
    private Integer markingRejected;
    private Integer temperingHardnessRejected;

    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}

