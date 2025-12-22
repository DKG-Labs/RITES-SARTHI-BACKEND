package com.sarthi.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representing material testing samples for Raw Material.
 * Stores 2 samples per heat with chemical composition and metallurgical data.
 */
@Entity
@Table(name = "rm_material_testing", indexes = {
    @Index(name = "idx_rm_mat_call_no", columnList = "inspection_call_no"),
    @Index(name = "idx_rm_mat_heat_no", columnList = "heat_no")
})
@Data
public class RmMaterialTesting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inspection_call_no", nullable = false, length = 50)
    private String inspectionCallNo;

    @Column(name = "heat_no", nullable = false, length = 50)
    private String heatNo;

    @Column(name = "heat_index")
    private Integer heatIndex;

    @Column(name = "sample_number", nullable = false)
    private Integer sampleNumber;

    // Chemical Composition
    @Column(name = "carbon_percent", precision = 6, scale = 4)
    private BigDecimal carbonPercent;

    @Column(name = "silicon_percent", precision = 6, scale = 4)
    private BigDecimal siliconPercent;

    @Column(name = "manganese_percent", precision = 6, scale = 4)
    private BigDecimal manganesePercent;

    @Column(name = "phosphorus_percent", precision = 6, scale = 4)
    private BigDecimal phosphorusPercent;

    @Column(name = "sulphur_percent", precision = 6, scale = 4)
    private BigDecimal sulphurPercent;

    // Mechanical Properties
    @Column(name = "grain_size", precision = 8, scale = 2)
    private BigDecimal grainSize;

    @Column(name = "hardness", precision = 8, scale = 2)
    private BigDecimal hardness;

    @Column(name = "decarb", precision = 8, scale = 4)
    private BigDecimal decarb;

    // Inclusion Ratings
    @Column(name = "inclusion_a", precision = 8, scale = 2)
    private BigDecimal inclusionA;

    @Column(name = "inclusion_b", precision = 8, scale = 2)
    private BigDecimal inclusionB;

    @Column(name = "inclusion_c", precision = 8, scale = 2)
    private BigDecimal inclusionC;

    @Column(name = "inclusion_d", precision = 8, scale = 2)
    private BigDecimal inclusionD;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}

