package com.sarthi.dto.processmaterial;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProcessStaticPeriodicCheckDTO {

    private Long id;
    private String inspectionCallNo;
    private String poNo;
    private String lineNo;
    private Boolean shearingPressCheck;
    private Boolean forgingPressCheck;
    private Boolean reheatingFurnaceCheck;
    private Boolean quenchingTimeCheck;
    private Boolean forgingDieCheck;
    private Boolean allChecksPassed;
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}

