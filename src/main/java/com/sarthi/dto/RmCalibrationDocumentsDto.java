package com.sarthi.dto;

import lombok.Data;
import java.math.BigDecimal;

/**
 * DTO for calibration and documents per heat.
 */
@Data
public class RmCalibrationDocumentsDto {

    private String inspectionCallNo;
    private String heatNo;
    private Integer heatIndex;

    // RDSO Approval Details
    private String rdsoApprovalId;
    private String rdsoValidFrom;
    private String rdsoValidTo;
    private Boolean gaugesAvailable;

    // Ladle Chemical Composition
    private BigDecimal ladleCarbonPercent;
    private BigDecimal ladleSiliconPercent;
    private BigDecimal ladleManganesePercent;
    private BigDecimal ladlePhosphorusPercent;
    private BigDecimal ladleSulphurPercent;

    // Vendor Verification
    private Boolean vendorVerified;
    private String verifiedBy;
    private String verifiedAt;
}

