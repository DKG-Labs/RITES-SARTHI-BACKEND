package com.sarthi.dto.processmaterial;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProcessOilTankCounterDTO {

    private Long id;
    private String inspectionCallNo;
    private String poNo;
    private String lineNo;
    private Integer oilTankCounter;
    private Boolean cleaningDone;
    private LocalDateTime cleaningDoneAt;
    private Boolean isLocked;
    private String counterStatus;
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}

