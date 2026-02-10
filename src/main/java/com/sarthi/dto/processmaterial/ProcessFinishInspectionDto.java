package com.sarthi.dto.processmaterial;

import lombok.Data;
import java.util.List;

/**
 * Main DTO for receiving Process Material inspection finish payload.
 * Contains all submodule data collected by the inspector for all production lines.
 */
@Data
public class ProcessFinishInspectionDto {

    private String inspectionCallNo;
    private String remarks;
    private List<ProcessLineDataDto> linesData;
    private String createdBy; // User ID who is finishing the inspection
    private String updatedBy; // User ID who is updating the inspection
    private String shiftCode;
}

