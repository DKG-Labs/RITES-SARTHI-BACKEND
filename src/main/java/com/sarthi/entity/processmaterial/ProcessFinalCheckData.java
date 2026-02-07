package com.sarthi.entity.processmaterial;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Entity for Final Check Section data in 8-Hour Grid.
 * Stores hourly visual, dimension, and hardness check data.
 */
@Entity
@Table(name = "process_final_check_data", indexes = {
    @Index(name = "idx_proc_final_call_no", columnList = "inspection_call_no"),
    @Index(name = "idx_proc_final_po_no", columnList = "po_no")
})
@Data
public class ProcessFinalCheckData {

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

    // Box Gauge - 2 readings
    @Column(name = "box_gauge_1", length = 50)
    private String boxGauge1;

    @Column(name = "box_gauge_2", length = 50)
    private String boxGauge2;

    // Flat Bearing Area - 2 readings
    @Column(name = "flat_bearing_area_1", length = 50)
    private String flatBearingArea1;

    @Column(name = "flat_bearing_area_2", length = 50)
    private String flatBearingArea2;

    // Falling Gauge - 2 readings
    @Column(name = "falling_gauge_1", length = 50)
    private String fallingGauge1;

    @Column(name = "falling_gauge_2", length = 50)
    private String fallingGauge2;

    // Surface Defect - 2 readings
    @Column(name = "surface_defect_1", length = 50)
    private String surfaceDefect1;

    @Column(name = "surface_defect_2", length = 50)
    private String surfaceDefect2;

    // Embossing Defect - 2 readings
    @Column(name = "embossing_defect_1", length = 50)
    private String embossingDefect1;

    @Column(name = "embossing_defect_2", length = 50)
    private String embossingDefect2;

    // Marking - 2 readings
    @Column(name = "marking_1", length = 50)
    private String marking1;

    @Column(name = "marking_2", length = 50)
    private String marking2;

    // Tempering Hardness - 2 readings
    @Column(name = "tempering_hardness_1", length = 50)
    private String temperingHardness1;

    @Column(name = "tempering_hardness_2", length = 50)
    private String temperingHardness2;

    // Rejected numbers


    // Separate rejection fields for each measurement
    @Column(name = "box_gauge_rejected")
    private Integer boxGaugeRejected;

    @Column(name = "flat_bearing_area_rejected")
    private Integer flatBearingAreaRejected;

    @Column(name = "falling_gauge_rejected")
    private Integer fallingGaugeRejected;

    @Column(name = "surface_defect_rejected")
    private Integer surfaceDefectRejected;

    @Column(name = "embossing_defect_rejected")
    private Integer embossingDefectRejected;

    @Column(name = "marking_rejected")
    private Integer markingRejected;

    @Column(name = "tempering_hardness_rejected")
    private Integer temperingHardnessRejected;

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

