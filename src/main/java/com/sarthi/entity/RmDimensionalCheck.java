package com.sarthi.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representing dimensional check samples for Raw Material.
 * Stores 20 diameter samples per heat.
 */
@Entity
@Table(name = "rm_dimensional_check", indexes = {
    @Index(name = "idx_rm_dim_call_no", columnList = "inspection_call_no"),
    @Index(name = "idx_rm_dim_heat_no", columnList = "heat_no")
})
@Data
public class RmDimensionalCheck {

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

    @Column(name = "diameter", precision = 8, scale = 4)
    private BigDecimal diameter;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}

