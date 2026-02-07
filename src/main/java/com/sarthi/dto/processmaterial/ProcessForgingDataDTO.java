package com.sarthi.dto.processmaterial;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProcessForgingDataDTO {

    private Long id;
    private String inspectionCallNo;
    private String poNo;
    private String lineNo;
    private String shift;
    private Integer hourIndex;
    private String hourLabel;
    private Boolean noProduction;
    private String lotNo;
    private BigDecimal forgingTemp1;
    private BigDecimal forgingTemp2;

    // Separate rejection fields for each measurement
    private Integer forgingTempRejected;

    // Forging Stabilisation Rejection - 2 readings + rejected count
    private String forgingStabilisationRejection1;
    private String forgingStabilisationRejection2;
    private Integer forgingStabilisationRejectionRejected;

    // Improper Forging - 2 readings + rejected count
    private String improperForging1;
    private String improperForging2;
    private Integer improperForgingRejected;

    // Forging Defect (Marks / Notches) - 2 readings + rejected count
    private String forgingDefect1;
    private String forgingDefect2;
    private Integer forgingDefectRejected;

    // Embossing Defect - 2 readings + rejected count
    private String embossingDefect1;
    private String embossingDefect2;
    private Integer embossingDefectRejected;

    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}

