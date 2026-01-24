package com.sarthi.dto.finalmaterial;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for Final Dimensional Inspection
 * Transfers dimensional inspection data between frontend and backend
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinalDimensionalInspectionDto {

    private Long id;
    private String inspectionCallNo;
    private String lotNo;
    private String heatNo;

    // Dimensional Inspection Data - First Sample
    private Integer firstSampleGoGaugeFail;
    private Integer firstSampleNoGoFail;
    private Integer firstSampleFlatBearingFail;

    // Dimensional Inspection Data - Second Sample
    private Integer secondSampleGoGaugeFail;
    private Integer secondSampleNoGoFail;
    private Integer secondSampleFlatBearingFail;

    // Total and Status
    private Integer totalRejected;
    private String status; // Values: 'OK', 'NOT_OK', 'PENDING'
    private String remarks;

    // Audit Fields
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime createdAt;
    private String updatedBy;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime updatedAt;
}

