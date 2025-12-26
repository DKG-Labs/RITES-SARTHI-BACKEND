package com.sarthi.dto.processmaterial;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ProcessCalibrationDocumentsDTO {

    private Long id;
    private String inspectionCallNo;
    private String poNo;
    private String lineNo;
    private String instrumentName;
    private String instrumentId;
    private String calibrationStatus;
    private LocalDate calibrationValidFrom;
    private LocalDate calibrationValidTo;
    private Boolean isVerified;
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}

