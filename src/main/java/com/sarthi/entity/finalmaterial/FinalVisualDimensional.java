package com.sarthi.entity.finalmaterial;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Entity for Final Inspection - Visual & Dimensional
 * Stores visual inspection and dimensional check data for final inspection
 */
@Entity
@Table(name = "final_visual_dimensional", indexes = {
    @Index(name = "idx_final_visual_call_no", columnList = "inspection_call_no"),
    @Index(name = "idx_final_visual_lot_no", columnList = "lot_no"),
    @Index(name = "idx_final_visual_heat_no", columnList = "heat_no")
})
@Data
public class FinalVisualDimensional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inspection_call_no", nullable = false, length = 50)
    private String inspectionCallNo;

    @Column(name = "lot_no", length = 100)
    private String lotNo;

    @Column(name = "heat_no", length = 100)
    private String heatNo;

    @Column(name = "sample_no")
    private Integer sampleNo;

    @Column(name = "surface_condition", length = 100)
    private String surfaceCondition;

    @Column(name = "dimension_check", length = 50)
    private String dimensionCheck;

    @Column(name = "visual_defects", length = 500)
    private String visualDefects;

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

