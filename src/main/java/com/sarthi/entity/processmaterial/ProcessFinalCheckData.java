package com.sarthi.entity.processmaterial;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Entity for Final Check Section data in 8-Hour Grid.
 * Stores hourly visual, dimension, and hardness check data.
 */
@Entity
@Table(name = "process_final_check_data", indexes = {
    @Index(name = "idx_proc_final_call_no", columnList = "inspection_call_no"),
    @Index(name = "idx_proc_final_po_no", columnList = "po_no")
})
@Data
public class ProcessFinalCheckData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inspection_call_no", nullable = false, length = 50)
    private String inspectionCallNo;

    @Column(name = "po_no", nullable = false, length = 50)
    private String poNo;

    @Column(name = "line_no", nullable = false, length = 20)
    private String lineNo;

    // Shift and hour info
    @Column(name = "shift", length = 5)
    private String shift;

    @Column(name = "hour_index")
    private Integer hourIndex;

    @Column(name = "hour_label", length = 30)
    private String hourLabel;

    @Column(name = "no_production")
    private Boolean noProduction = false;

    @Column(name = "lot_no", length = 50)
    private String lotNo;

    // Visual Check - 2 readings
    @Column(name = "visual_check_1", length = 50)
    private String visualCheck1;

    @Column(name = "visual_check_2", length = 50)
    private String visualCheck2;

    // Dimension Check - 2 readings
    @Column(name = "dimension_check_1", length = 50)
    private String dimensionCheck1;

    @Column(name = "dimension_check_2", length = 50)
    private String dimensionCheck2;

    // Hardness Check - 2 readings
    @Column(name = "hardness_check_1", length = 50)
    private String hardnessCheck1;

    @Column(name = "hardness_check_2", length = 50)
    private String hardnessCheck2;

    // Rejected numbers
    @Column(name = "rejected_no_1")
    private Integer rejectedNo1;

    @Column(name = "rejected_no_2")
    private Integer rejectedNo2;

    @Column(name = "rejected_no_3")
    private Integer rejectedNo3;

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

