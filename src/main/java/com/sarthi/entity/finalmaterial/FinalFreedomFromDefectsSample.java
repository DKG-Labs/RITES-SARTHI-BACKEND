package com.sarthi.entity.finalmaterial;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Child Entity for Final Inspection - Freedom from Defects Sample
 * 
 * Stores EVERY individual sample value entered in the UI.
 * Supports multiple samplings and dynamic sample counts.
 * 
 * Design Semantics:
 * - One row in final_freedom_from_defects_test = one inspection session.
 * - Many rows in final_freedom_from_defects_sample = all sample readings.
 * - sample_no preserves the UI order (#1, #2, #3, ...).
 * - sample_type stores the type of defect sample.
 * - There is NO separate table for sampling rounds.
 * - There is NO per-round status column.
 * - Overall status is stored ONLY in final_freedom_from_defects_test.status.
 */
@Entity
@Table(name = "final_freedom_from_defects_sample")
@Data
public class FinalFreedomFromDefectsSample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Foreign key to parent inspection record.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "final_freedom_from_defects_test_id", nullable = false)
    private FinalFreedomFromDefectsTest finalFreedomFromDefectsTest;

    /**
     * Sampling round number (1st sampling, 2nd sampling, etc.).
     */
    @Column(name = "sampling_no", nullable = false)
    private Integer samplingNo;

    /**
     * Sample number within the sampling round (#1, #2, #3, ...).
     */
    @Column(name = "sample_no", nullable = false)
    private Integer sampleNo;

    /**
     * Type of defect sample.
     */
    @Column(name = "sample_type", length = 100)
    private String sampleType;

    /**
     * Timestamp when this sample value was recorded.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * User who created this sample record.
     */
    @Column(name = "created_by", length = 100)
    private String createdBy;

    // ========== Lifecycle Callbacks ==========

    /**
     * Called on insert.
     * Sets created_at timestamp.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}

