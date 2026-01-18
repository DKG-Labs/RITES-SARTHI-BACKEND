package com.sarthi.entity.finalmaterial;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity for Final Inspection - Calibration & Documents
 * Stores calibration status and document verification data for final inspection
 */
@Entity
@Table(name = "final_calibration_documents", indexes = {
    @Index(name = "idx_final_calib_call_no", columnList = "inspection_call_no"),
    @Index(name = "idx_final_calib_lot_no", columnList = "lot_no"),
    @Index(name = "idx_final_calib_heat_no", columnList = "heat_no")
})
@Data
public class FinalCalibrationDocuments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inspection_call_no", nullable = false, length = 50)
    private String inspectionCallNo;

    @Column(name = "lot_no", length = 100)
    private String lotNo;

    @Column(name = "heat_no", length = 100)
    private String heatNo;

    @Column(name = "instrument_name", length = 200)
    private String instrumentName;

    @Column(name = "calibration_status", length = 50)
    private String calibrationStatus;

    @Column(name = "calibration_date")
    private LocalDate calibrationDate;

    @Column(name = "next_calibration_date")
    private LocalDate nextCalibrationDate;

    @Column(name = "certificate_no", length = 100)
    private String certificateNo;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    // Audit Fields
    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_by", length = 100)
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

