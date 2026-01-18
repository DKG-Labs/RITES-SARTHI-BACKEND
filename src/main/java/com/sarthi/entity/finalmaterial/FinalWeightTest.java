package com.sarthi.entity.finalmaterial;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity for Final Inspection - Weight Test
 * Stores weight measurement data for final inspection
 */
@Entity
@Table(name = "final_weight_test", indexes = {
    @Index(name = "idx_final_weight_call_no", columnList = "inspection_call_no"),
    @Index(name = "idx_final_weight_lot_no", columnList = "lot_no"),
    @Index(name = "idx_final_weight_heat_no", columnList = "heat_no")
})
@Data
public class FinalWeightTest {

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

    @Column(name = "weight_value", precision = 10, scale = 3)
    private BigDecimal weightValue;

    @Column(name = "min_weight", precision = 10, scale = 3)
    private BigDecimal minWeight;

    @Column(name = "max_weight", precision = 10, scale = 3)
    private BigDecimal maxWeight;

    @Column(name = "status", length = 50)
    private String status;

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

