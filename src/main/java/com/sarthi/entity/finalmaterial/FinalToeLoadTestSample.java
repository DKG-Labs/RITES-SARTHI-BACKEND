package com.sarthi.entity.finalmaterial;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;
/**
 * Child Entity for Final Inspection - Toe Load Test Sample
 * 
 * Stores EVERY individual sample value entered in the UI.
 * Supports multiple samplings and dynamic sample counts.
 * 
 * Design Semantics:
 * - One row in final_toe_load_test = one inspection session.
 * - Many rows in final_toe_load_test_sample = all sample readings.
 * - sampling_no groups samples into 1st sampling, 2nd sampling, etc.
 * - sample_no preserves the UI order (#1, #2, #3, ...).
 * - There is NO separate table for sampling rounds.
 * - There is NO per-round status column.
 * - Overall status is stored ONLY in final_toe_load_test.status.
 */
@Entity
@Table(name = "final_toe_load_test_sample", indexes = {
    @Index(name = "idx_ftlts_parent_id", columnList = "final_toe_load_test_id"),
    @Index(name = "idx_ftlts_sampling_no", columnList = "sampling_no"),
    @Index(name = "idx_ftlts_unique", columnList = "final_toe_load_test_id,sampling_no,sample_no", unique = true)
})
@Data
public class FinalToeLoadTestSample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Foreign key to final_toe_load_test.
     * Identifies which inspection session this sample belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "final_toe_load_test_id", nullable = false)
    private FinalToeLoadTest finalToeLoadTest;

    /**
     * Sampling attempt number:
     * 1 = 1st sampling
     * 2 = 2nd sampling
     * 3 = 3rd sampling
     * etc.
     */
    @Column(name = "sampling_no", nullable = false)
    private Integer samplingNo;

    /**
     * Sequence number within a sampling round:
     * 1, 2, 3, ... n.
     */
    @Column(name = "sample_no", nullable = false)
    private Integer sampleNo;

    /**
     * Actual toe load value entered by the inspector.
     */
    @Column(name = "sample_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal sampleValue;

    /**
     * Whether this individual sample failed acceptance criteria.
     */
    @Column(name = "is_rejected", nullable = false)
    private Boolean isRejected;

    /**
     * Timestamp when this sample value was recorded.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

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

