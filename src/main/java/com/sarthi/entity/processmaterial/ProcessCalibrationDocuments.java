package com.sarthi.entity.processmaterial;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity for Calibration & Documents verification in Process Material Inspection.
 * Stores instrument calibration status and document verification.
 */
@Entity
@Table(name = "process_calibration_documents", indexes = {
    @Index(name = "idx_proc_cal_call_no", columnList = "inspection_call_no"),
    @Index(name = "idx_proc_cal_po_no", columnList = "po_no")
})
@Data
public class ProcessCalibrationDocuments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inspection_call_no", nullable = false, length = 50)
    private String inspectionCallNo;

    @Column(name = "po_no", nullable = false, length = 50)
    private String poNo;

    @Column(name = "line_no", nullable = false, length = 20)
    private String lineNo;

    // Instrument calibration details
    @Column(name = "instrument_name", length = 100)
    private String instrumentName;

    @Column(name = "instrument_id", length = 50)
    private String instrumentId;

    @Column(name = "calibration_status", length = 20)
    private String calibrationStatus;

    @Column(name = "calibration_valid_from")
    private LocalDate calibrationValidFrom;

    @Column(name = "calibration_valid_to")
    private LocalDate calibrationValidTo;

    @Column(name = "is_verified")
    private Boolean isVerified = false;

    @Column(name = "remarks", length = 500)
    private String remarks;

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

