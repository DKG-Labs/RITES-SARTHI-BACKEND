package com.sarthi.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representing visual inspection defects for Raw Material.
 * Stores selected defects per heat.
 */
@Entity
@Table(name = "rm_visual_inspection", indexes = {
    @Index(name = "idx_rm_visual_call_no", columnList = "inspection_call_no"),
    @Index(name = "idx_rm_visual_heat_no", columnList = "heat_no")
})
@Data
public class RmVisualInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inspection_call_no", nullable = false, length = 50)
    private String inspectionCallNo;

    @Column(name = "heat_no", nullable = false, length = 50)
    private String heatNo;

    @Column(name = "heat_index")
    private Integer heatIndex;

    @Column(name = "defect_name", nullable = false, length = 100)
    private String defectName;

    @Column(name = "is_selected")
    private Boolean isSelected = false;

    @Column(name = "defect_length_mm", precision = 10, scale = 2)
    private BigDecimal defectLengthMm;

    @Column(name = "passed_at")
    private LocalDateTime passedAt;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}

