package com.sarthi.entity.processmaterial;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity for Process Summary Report.
 * Stores consolidated inspection results and IE final remarks.
 */
@Entity
@Table(name = "process_summary_report", indexes = {
    @Index(name = "idx_proc_summary_call_no", columnList = "inspection_call_no"),
    @Index(name = "idx_proc_summary_po_no", columnList = "po_no")
})
@Data
public class ProcessSummaryReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inspection_call_no", nullable = false, length = 50)
    private String inspectionCallNo;

    @Column(name = "po_no", nullable = false, length = 50)
    private String poNo;

    @Column(name = "line_no", nullable = false, length = 20)
    private String lineNo;

    // Heat/Lot information
    @Column(name = "heat_no", length = 50)
    private String heatNo;

    @Column(name = "lot_no", length = 50)
    private String lotNo;

    // Final inspection result
    @Column(name = "accepted_rejected", length = 20)
    private String acceptedRejected;

    @Column(name = "weight_of_material", precision = 10, scale = 2)
    private BigDecimal weightOfMaterial;

    @Column(name = "heat_remarks", length = 500)
    private String heatRemarks;

    // Static checks summary
    @Column(name = "static_checks_passed")
    private Boolean staticChecksPassed = false;

    // Oil tank counter summary
    @Column(name = "oil_tank_counter_value")
    private Integer oilTankCounterValue;

    @Column(name = "oil_tank_status", length = 20)
    private String oilTankStatus;

    // Calibration summary
    @Column(name = "calibration_verified")
    private Boolean calibrationVerified = false;

    // IE final review
    @Column(name = "ie_remarks", length = 1000)
    private String ieRemarks;

    @Column(name = "final_status", length = 30)
    private String finalStatus;

    @Column(name = "inspection_completed")
    private Boolean inspectionCompleted = false;

    @Column(name = "inspection_completed_at")
    private LocalDateTime inspectionCompletedAt;

    // Audit fields
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "updated_by", length = 100)
    private String updatedBy;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

