package com.sarthi.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * DTO for inspection initiation data from IE.
 */
@Data
public class InspectionInitiationDto {

    private Long id;
    private Long inspectionRequestId;
    private String callNo;
    private String poNo;
    private String shiftOfInspection;
    private LocalDate dateOfInspection;
    private BigDecimal offeredQty;
    private Boolean cmApproval;
    private Boolean sectionAVerified;
    private Boolean sectionBVerified;
    private Boolean sectionCVerified;
    private Boolean sectionDVerified;
    private Boolean multipleLinesActive;
    private List<Map<String, Object>> productionLines;
    private String productType;
    private String status;

    // Fields for withheld/cancelled actions
    private String actionType;       // WITHHELD, CANCELLED
    private String reason;           // MATERIAL_NOT_AVAILABLE, PLACE_NOT_AS_PER_PO, VENDOR_WITHDRAWN, ANY_OTHER
    private String remarks;          // Remarks for "Any other" reason
    private LocalDateTime actionDate;

    private String initiatedBy;
    private LocalDateTime initiatedAt;
}

