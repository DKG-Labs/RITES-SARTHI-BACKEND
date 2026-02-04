package com.sarthi.dto.processmaterial;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProcessMpiDataDTO {

    private Long id;
    private String inspectionCallNo;
    private String poNo;
    private String lineNo;
    private String shift;
    private Integer hourIndex;
    private String hourLabel;
    private Boolean noProduction;
    private String lotNo;
    private String testResult1;
    private String testResult2;
    private String testResult3;
    private Integer mpiRejected;
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}

