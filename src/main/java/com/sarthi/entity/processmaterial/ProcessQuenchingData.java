package com.sarthi.entity.processmaterial;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity for Quenching Section data in 8-Hour Grid.
 * Stores hourly quenching temperature, duration and hardness data.
 */
@Entity
@Table(name = "process_quenching_data", indexes = {
    @Index(name = "idx_proc_quench_call_no", columnList = "inspection_call_no"),
    @Index(name = "idx_proc_quench_po_no", columnList = "po_no")
})
@Data
public class ProcessQuenchingData {

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

    // Quenching parameters (2 samples each)
    @Column(name = "quenching_temperature_1", precision = 10, scale = 2)
    private BigDecimal quenchingTemperature1;

    @Column(name = "quenching_temperature_2", precision = 10, scale = 2)
    private BigDecimal quenchingTemperature2;

    @Column(name = "quenching_duration_1", precision = 10, scale = 2)
    private BigDecimal quenchingDuration1;

    @Column(name = "quenching_duration_2", precision = 10, scale = 2)
    private BigDecimal quenchingDuration2;

    // Quenching Hardness - 2 readings
    @Column(name = "quenching_hardness_1", precision = 10, scale = 2)
    private BigDecimal quenchingHardness1;

    @Column(name = "quenching_hardness_2", precision = 10, scale = 2)
    private BigDecimal quenchingHardness2;

    @Column(name = "rejected_qty")
    private Integer rejectedQty;

    // Quenching Rejection - separate counts
    @Column(name = "quenching_temperature_rejected")
    private Integer quenchingTemperatureRejected;

    @Column(name = "quenching_duration_rejected")
    private Integer quenchingDurationRejected;

    @Column(name = "quenching_hardness_rejected")
    private Integer quenchingHardnessRejected;

    // Gauge Check - 2 readings each
    @Column(name = "box_gauge_1", length = 30)
    private String boxGauge1;

    @Column(name = "box_gauge_2", length = 30)
    private String boxGauge2;

    @Column(name = "box_gauge_rejected")
    private Integer boxGaugeRejected;

    @Column(name = "flat_bearing_area_1", length = 30)
    private String flatBearingArea1;

    @Column(name = "flat_bearing_area_2", length = 30)
    private String flatBearingArea2;

    @Column(name = "flat_bearing_area_rejected")
    private Integer flatBearingAreaRejected;

    @Column(name = "falling_gauge_1", length = 30)
    private String fallingGauge1;

    @Column(name = "falling_gauge_2", length = 30)
    private String fallingGauge2;

    @Column(name = "falling_gauge_rejected")
    private Integer fallingGaugeRejected;

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

