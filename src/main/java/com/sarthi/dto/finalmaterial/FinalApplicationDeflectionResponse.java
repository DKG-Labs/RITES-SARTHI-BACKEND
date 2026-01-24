package com.sarthi.dto.finalmaterial;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Response DTO for Final Application & Deflection Test
 */
@Data
public class FinalApplicationDeflectionResponse {

    private Long id;
    private String inspectionCallNo;
    private String lotNo;
    private String heatNo;
    private Integer sampleSize;
    private String status;
    private String remarks;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
    private List<SampleResponse> samples;

    @Data
    public static class SampleResponse {
        private Long id;
        private Integer samplingNo;
        private Integer noOfSamplesFailed;
        private LocalDateTime createdAt;
        private String createdBy;
    }
}

