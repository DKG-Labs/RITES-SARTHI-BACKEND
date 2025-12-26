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

    // Sharp Edges checks
    @Column(name = "sharp_edges_1")
    private Boolean sharpEdges1 = false;

    @Column(name = "sharp_edges_2")
    private Boolean sharpEdges2 = false;

    @Column(name = "sharp_edges_3")
    private Boolean sharpEdges3 = false;

    // Rejected quantity
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

