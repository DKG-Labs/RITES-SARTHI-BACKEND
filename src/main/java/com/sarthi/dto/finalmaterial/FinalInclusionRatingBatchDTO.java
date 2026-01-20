package com.sarthi.dto.finalmaterial;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * DTO for batch saving Inclusion Rating data with multiple samples per lot
 * Accepts array data from frontend and transforms it into individual sample records
 */
@Data
public class FinalInclusionRatingBatchDTO {
    
    private String inspectionCallNo;
    private String lotNo;
    private String heatNo;
    
    // Arrays of values - one per sample
    private List<String> microstructure1st;
    private List<String> microstructure2nd;
    private List<String> decarb1st;
    private List<String> decarb2nd;
    
    // Array of inclusion objects - each has A, B, C, D ratings and type
    private List<Map<String, String>> inclusion1st;
    private List<Map<String, String>> inclusion2nd;
    
    // Arrays of defect values
    private List<String> defects1st;
    private List<String> defects2nd;
    
    // Remarks (shared across all samples in the lot)
    private String microstructureRemarks;
    private String decarbRemarks;
    private String inclusionRemarks;
    private String defectsRemarks;
    
    // Audit field
    private String createdBy;
}

