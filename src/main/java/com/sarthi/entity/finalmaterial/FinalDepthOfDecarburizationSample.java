package com.sarthi.entity.finalmaterial;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * Child Entity for Final Inspection - Depth of Decarburization Sample
 * 
 * Stores EVERY individual sample value entered in the UI.
 * Supports multiple samplings and dynamic sample counts.
 * 
 * Design Semantics:
 * - One row in final_depth_of_decarburization = one inspection session.
 * - Many rows in final_depth_of_decarburization_sample = all sample readings.
 * - sampling_no groups samples into 1st sampling, 2nd sampling, etc.
 * - sample_no preserves the UI order (#1, #2, #3, ...).
 * - There is NO separate table for sampling rounds.
 * - There is NO per-round status column.
 * - Overall status is stored ONLY in final_depth_of_decarburization.status.
 */
@Entity
@Table(name = "final_depth_of_decarburization_sample")
@Data
public class FinalDepthOfDecarburizationSample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Foreign key to parent inspection record.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "final_depth_of_decarburization_id", nullable = false)
    private FinalDepthOfDecarburization finalDepthOfDecarburization;

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
     * Actual decarburization value entered by the inspector.
     */
    @Column(name = "sample_value", precision = 10, scale = 4)
    private BigDecimal sampleValue;

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

