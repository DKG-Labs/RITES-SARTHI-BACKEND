package com.sarthi.entity.processmaterial;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity for Turning Section data in 8-Hour Grid.
 * Stores hourly turning production data.
 */
@Entity
@Table(name = "process_turning_data", indexes = {
    @Index(name = "idx_proc_turn_call_no", columnList = "inspection_call_no"),
    @Index(name = "idx_proc_turn_po_no", columnList = "po_no")
})
@Data
public class ProcessTurningData {

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

    // Straight Length - 3 measurements
    @Column(name = "straight_length_1", precision = 10, scale = 2)
    private BigDecimal straightLength1;

    @Column(name = "straight_length_2", precision = 10, scale = 2)
    private BigDecimal straightLength2;

    @Column(name = "straight_length_3", precision = 10, scale = 2)
    private BigDecimal straightLength3;

    // Taper Length - 3 measurements
    @Column(name = "taper_length_1", precision = 10, scale = 2)
    private BigDecimal taperLength1;

    @Column(name = "taper_length_2", precision = 10, scale = 2)
    private BigDecimal taperLength2;

    @Column(name = "taper_length_3", precision = 10, scale = 2)
    private BigDecimal taperLength3;

    // Diameter - 3 measurements
    @Column(name = "dia_1", precision = 10, scale = 2)
    private BigDecimal dia1;

    @Column(name = "dia_2", precision = 10, scale = 2)
    private BigDecimal dia2;

    @Column(name = "dia_3", precision = 10, scale = 2)
    private BigDecimal dia3;

    @Column(name = "accepted_qty")
    private Integer acceptedQty;

    @Column(name = "rejected_qty_1")
    private Integer rejectedQty1;

    @Column(name = "rejected_qty_2")
    private Integer rejectedQty2;

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

