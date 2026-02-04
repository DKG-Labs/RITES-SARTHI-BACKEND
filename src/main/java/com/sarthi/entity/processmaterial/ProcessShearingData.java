package com.sarthi.entity.processmaterial;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity for Shearing Section data in 8-Hour Grid.
 * Stores hourly shearing production data.
 */
@Entity
@Table(name = "process_shearing_data", indexes = {
    @Index(name = "idx_proc_shear_call_no", columnList = "inspection_call_no"),
    @Index(name = "idx_proc_shear_po_no", columnList = "po_no")
})
@Data
public class ProcessShearingData {

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

    // Length of Cut Bar - 3 measurements
    @Column(name = "length_cut_bar_1", precision = 10, scale = 2)
    private BigDecimal lengthCutBar1;

    @Column(name = "length_cut_bar_2", precision = 10, scale = 2)
    private BigDecimal lengthCutBar2;

    @Column(name = "length_cut_bar_3", precision = 10, scale = 2)
    private BigDecimal lengthCutBar3;

    // Improper Dia (Quality)
    @Column(name = "improper_dia_1", length = 20)
    private String improperDia1;

    @Column(name = "improper_dia_2", length = 20)
    private String improperDia2;

    @Column(name = "improper_dia_3", length = 20)
    private String improperDia3;

    // Sharp Edges checks
    @Column(name = "sharp_edges_1", length = 20)
    private String sharpEdges1;

    @Column(name = "sharp_edges_2", length = 20)
    private String sharpEdges2;

    @Column(name = "sharp_edges_3", length = 20)
    private String sharpEdges3;

    // Cracked Edges
    @Column(name = "cracked_edges_1", length = 20)
    private String crackedEdges1;

    @Column(name = "cracked_edges_2", length = 20)
    private String crackedEdges2;

    @Column(name = "cracked_edges_3", length = 20)
    private String crackedEdges3;

    // Rejected quantity
    @Column(name = "length_cut_bar_rejected")
    private Integer lengthCutBarRejected;

    @Column(name = "improper_dia_rejected")
    private Integer improperDiaRejected;

    @Column(name = "sharp_edges_rejected")
    private Integer sharpEdgesRejected;

    @Column(name = "cracked_edges_rejected")
    private Integer crackedEdgesRejected;

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

