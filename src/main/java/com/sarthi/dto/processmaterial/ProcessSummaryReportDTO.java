package com.sarthi.dto.processmaterial;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProcessSummaryReportDTO {

    private Long id;
    private String inspectionCallNo;
    private String poNo;
    private String lineNo;
    private String heatNo;
    private String lotNo;
    private String acceptedRejected;
    private BigDecimal weightOfMaterial;
    private String heatRemarks;
    private Boolean staticChecksPassed;
    private Integer oilTankCounterValue;
    private String oilTankStatus;
    private Boolean calibrationVerified;
    private String ieRemarks;
    private String finalStatus;
    private Boolean inspectionCompleted;
    private LocalDateTime inspectionCompletedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}

