package com.sarthi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Inspection Schedule request/response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InspectionScheduleDto {
    
    private Long id;
    private String callNo;
    private String scheduleDate;
    private String reason;
    private String status;
    private String createdBy;
    private String createdAt;
    private String updatedBy;
    private String updatedAt;
}

