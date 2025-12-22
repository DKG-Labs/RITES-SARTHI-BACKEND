package com.sarthi.dto;

import lombok.Data;

/**
 * DTO for inspector details when finishing inspection.
 */
@Data
public class RmInspectorDetailsDto {

    private String finishedBy;
    private String finishedAt;
    private String inspectionDate;
    private String shiftOfInspection;
}

