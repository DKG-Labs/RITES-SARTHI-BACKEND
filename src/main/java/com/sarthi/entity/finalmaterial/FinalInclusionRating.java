package com.sarthi.entity.finalmaterial;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity for Final Inspection - Inclusion & Decarb
 * Stores inclusion rating and decarburization data for final inspection
 */
@Entity
@Table(name = "final_inclusion_rating", indexes = {
    @Index(name = "idx_final_incl_call_no", columnList = "inspection_call_no"),
    @Index(name = "idx_final_incl_lot_no", columnList = "lot_no"),
    @Index(name = "idx_final_incl_heat_no", columnList = "heat_no")
})
@Data
public class FinalInclusionRating {

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

    @Column(name = "microstructure_1st", length = 100)
    private String microstructure1st;

    @Column(name = "microstructure_2nd", length = 100)
    private String microstructure2nd;

    @Column(name = "decarb_1st", precision = 5, scale = 2)
    private BigDecimal decarb1st;

    @Column(name = "decarb_2nd", precision = 5, scale = 2)
    private BigDecimal decarb2nd;

    @Column(name = "inclusion_a_rating", length = 50)
    private String inclusionARating;

    @Column(name = "inclusion_b_rating", length = 50)
    private String inclusionBRating;

    @Column(name = "inclusion_c_rating", length = 50)
    private String inclusionCRating;

    @Column(name = "inclusion_d_rating", length = 50)
    private String inclusionDRating;

    @Column(name = "inclusion_type", length = 50)
    private String inclusionType;

    @Column(name = "freedom_from_defects", length = 50)
    private String freedomFromDefects;

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

