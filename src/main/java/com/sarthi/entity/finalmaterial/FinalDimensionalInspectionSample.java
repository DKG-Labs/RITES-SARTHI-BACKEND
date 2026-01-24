package com.sarthi.entity.finalmaterial;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Child Entity for Final Inspection - Dimensional Inspection Sample
 *
 * Stores sample-level data for dimensional inspection.
 * Multiple samples can exist for a single inspection (1st sampling, 2nd sampling, etc.)
 *
 * Design Semantics:
 * - Many rows in final_dimensional_inspection_samples = all sample readings for one inspection.
 * - One row per (inspection, sampling_no) combination (enforced by unique constraint).
 * - created_by is set ONLY on first insert and never changed.
 */
@Entity
@Table(name = "final_dimensional_inspection_samples", indexes = {
    @Index(name = "idx_fdis_parent_id", columnList = "final_dimensional_inspection_id"),
    @Index(name = "idx_fdis_sampling_no", columnList = "sampling_no")
},
uniqueConstraints = {
    @UniqueConstraint(name = "uk_fdis_unique", columnNames = {"final_dimensional_inspection_id", "sampling_no"})
})
@Data
public class FinalDimensionalInspectionSample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Foreign key to parent inspection record.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "final_dimensional_inspection_id", nullable = false)
    private FinalDimensionalInspection finalDimensionalInspection;

    /**
     * Sampling round number (1st sampling, 2nd sampling, etc.)
     */
    @Column(name = "sampling_no", nullable = false)
    private Integer samplingNo;

    /**
     * Number of GO Gauge failures in this sampling round.
     */
    @Column(name = "go_gauge_failed")
    private Integer goGaugeFailed = 0;

    /**
     * Number of NO-GO Gauge failures in this sampling round.
     */
    @Column(name = "no_go_gauge_failed")
    private Integer noGoGaugeFailed = 0;

    /**
     * Number of Flatness failures in this sampling round.
     */
    @Column(name = "flatness_failed")
    private Integer flatnessFailed = 0;

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

