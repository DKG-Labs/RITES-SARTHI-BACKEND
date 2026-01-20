package com.sarthi.entity.finalmaterial;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity for Final Inspection - Hardness Test
 * Stores hardness test measurements for final inspection
 */
@Entity
@Table(name = "final_hardness_test", indexes = {
    @Index(name = "idx_final_hard_call_no", columnList = "inspection_call_no"),
    @Index(name = "idx_final_hard_lot_no", columnList = "lot_no"),
    @Index(name = "idx_final_hard_heat_no", columnList = "heat_no")
})
@Data
public class FinalHardnessTest {

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

    @Column(name = "hardness_1st_sample", precision = 5, scale = 2)
    private BigDecimal hardness1stSample;

    @Column(name = "hardness_2nd_sample", precision = 5, scale = 2)
    private BigDecimal hardness2ndSample;

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

