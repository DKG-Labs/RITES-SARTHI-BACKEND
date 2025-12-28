package com.sarthi.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity representing IE's inspection initiation data.
 * Stores shift, date, verification status, and other initiation details.
 */
@Entity
@Table(name = "inspection_initiation", indexes = {
    @Index(name = "idx_init_inspection_request_id", columnList = "inspection_request_id"),
    @Index(name = "idx_init_call_no", columnList = "call_no"),
    @Index(name = "idx_init_status", columnList = "status")
})
@Data
public class InspectionInitiation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Reference to the vendor inspection request
    @Column(name = "inspection_request_id")
    private Long inspectionRequestId;

    // Call number for quick reference
    @Column(name = "call_no", length = 50)
    private String callNo;

    // PO Number
    @Column(name = "po_no", length = 50)
    private String poNo;

    // IE selected shift (Morning, Afternoon, Night, General)
    @Column(name = "shift_of_inspection", length = 20)
    private String shiftOfInspection;

    // Date of inspection selected by IE
    @Column(name = "date_of_inspection")
    private LocalDate dateOfInspection;

    // Offered quantity
    @Column(name = "offered_qty", precision = 15, scale = 3)
    private BigDecimal offeredQty;

    // CM Approval checkbox (if qty differs)
    @Column(name = "cm_approval")
    private Boolean cmApproval = false;

    // Section verification flags
    @Column(name = "section_a_verified")
    private Boolean sectionAVerified = false;

    @Column(name = "section_b_verified")
    private Boolean sectionBVerified = false;

    @Column(name = "section_c_verified")
    private Boolean sectionCVerified = false;

    @Column(name = "section_d_verified")
    private Boolean sectionDVerified = false;

    // Multiple production lines
    @Column(name = "multiple_lines_active")
    private Boolean multipleLinesActive = false;

    // Production lines data (stored as JSON)
    @Column(name = "production_lines_json", columnDefinition = "TEXT")
    private String productionLinesJson;

    // Product type (Raw Material, Process, Final)
    @Column(name = "product_type", length = 50)
    private String productType;

    // Status: INITIATED, IN_PROGRESS, COMPLETED, WITHHELD, CANCELLED
    @Column(name = "status", length = 20)
    private String status = "INITIATED";

    // Action type for withheld/cancelled: WITHHELD, CANCELLED
    @Column(name = "action_type", length = 20)
    private String actionType;

    // Reason code for withheld/cancelled
    // Values: MATERIAL_NOT_AVAILABLE, PLACE_NOT_AS_PER_PO, VENDOR_WITHDRAWN, ANY_OTHER
    @Column(name = "action_reason", length = 50)
    private String actionReason;

    // Remarks for "Any other" reason
    @Column(name = "action_remarks", columnDefinition = "TEXT")
    private String actionRemarks;

    // Date when the action (withheld/cancelled) was taken
    @Column(name = "action_date")
    private LocalDateTime actionDate;

    // IE who initiated
    @Column(name = "initiated_by", length = 50)
    private String initiatedBy;

    @Column(name = "initiated_at")
    private LocalDateTime initiatedAt = LocalDateTime.now();

    // Audit fields
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

