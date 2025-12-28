package com.sarthi.entity.processmaterial;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity for Tempering Section data in 8-Hour Grid.
 * Stores hourly tempering temperature, duration and quantity data.
 */
@Entity
@Table(name = "process_tempering_data", indexes = {
    @Index(name = "idx_proc_temper_call_no", columnList = "inspection_call_no"),
    @Index(name = "idx_proc_temper_po_no", columnList = "po_no")
})
@Data
public class ProcessTemperingData {

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

    // Tempering parameters
    @Column(name = "tempering_temperature", precision = 10, scale = 2)
    private BigDecimal temperingTemperature;

    @Column(name = "tempering_duration", precision = 10, scale = 2)
    private BigDecimal temperingDuration;

    @Column(name = "accepted_qty")
    private Integer acceptedQty;

    @Column(name = "rejected_qty")
    private Integer rejectedQty;

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

