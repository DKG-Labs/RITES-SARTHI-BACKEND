package com.sarthi.entity.finalmaterial;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Child Entity for Final Inspection - Application & Deflection Test Sample
 *
 * Stores sample-level data for application & deflection test.
 * Multiple samples can exist for a single inspection (1st sampling, 2nd sampling, etc.)
 *
 * Design Semantics:
 * - Many rows in final_application_deflection_samples = all sample readings for one inspection.
 * - One row per (inspection, sampling_no) combination (enforced by unique constraint).
 * - created_by is set ONLY on first insert and never changed.
 */
@Entity
@Table(name = "final_application_deflection_samples", indexes = {
    @Index(name = "idx_fads_parent_id", columnList = "final_application_deflection_id"),
    @Index(name = "idx_fads_sampling_no", columnList = "sampling_no")
},
uniqueConstraints = {
    @UniqueConstraint(name = "uk_fads_unique", columnNames = {"final_application_deflection_id", "sampling_no"})
})
@Data
public class FinalApplicationDeflectionSample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Foreign key to parent inspection record.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "final_application_deflection_id", nullable = false)
    private FinalApplicationDeflection finalApplicationDeflection;

    /**
     * Sampling round number (1st sampling, 2nd sampling, etc.)
     */
    @Column(name = "sampling_no", nullable = false)
    private Integer samplingNo;

    /**
     * Number of samples failed in this sampling round.
     */
    @Column(name = "no_of_samples_failed")
    private Integer noOfSamplesFailed = 0;

    // ========== Audit Fields ==========

    /**
     * Timestamp when this sample record was created.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * User ID who created this sample record.
     */
    @Column(name = "created_by", length = 100)
    private String createdBy;

    // ========== Lifecycle Callbacks ==========

    /**
     * Called on FIRST insert.
     * Sets created_at.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}

