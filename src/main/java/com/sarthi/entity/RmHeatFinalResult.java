package com.sarthi.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity representing per-heat final results for Raw Material Inspection.
 * Stores heat pre-inspection data, inspection status, and submodule statuses.
 */
@Entity
@Table(name = "rm_heat_final_result", indexes = {
    @Index(name = "idx_rm_heat_call_no", columnList = "inspection_call_no"),
    @Index(name = "idx_rm_heat_heat_no", columnList = "heat_no"),
    @Index(name = "idx_rm_heat_status", columnList = "status")
})
@Data
public class RmHeatFinalResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inspection_call_no", nullable = false, length = 50)
    private String inspectionCallNo;

    @Column(name = "heat_index")
    private Integer heatIndex;

    @Column(name = "heat_no", nullable = false, length = 50)
    private String heatNo;

    // Heat Pre-Inspection Data (from vendor call)
    @Column(name = "tc_no", length = 50)
    private String tcNo;

    @Column(name = "tc_date")
    private LocalDate tcDate;

    @Column(name = "manufacturer_name", length = 200)
    private String manufacturerName;

    @Column(name = "invoice_number", length = 50)
    private String invoiceNumber;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "sub_po_number", length = 50)
    private String subPoNumber;

    @Column(name = "sub_po_date")
    private LocalDate subPoDate;

    @Column(name = "sub_po_qty", precision = 12, scale = 4)
    private BigDecimal subPoQty;

    @Column(name = "total_value_of_po", length = 50)
    private String totalValueOfPo;

    @Column(name = "tc_quantity", precision = 12, scale = 4)
    private BigDecimal tcQuantity;

    @Column(name = "offered_qty", precision = 12, scale = 4)
    private BigDecimal offeredQty;

    @Column(name = "color_code", length = 50)
    private String colorCode;

    // Final Status and Weights
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "weight_offered_mt", precision = 12, scale = 4)
    private BigDecimal weightOfferedMt;

    @Column(name = "weight_accepted_mt", precision = 12, scale = 4)
    private BigDecimal weightAcceptedMt;

    @Column(name = "weight_rejected_mt", precision = 12, scale = 4)
    private BigDecimal weightRejectedMt;

    // Per-Submodule Status
    @Column(name = "calibration_status", length = 20)
    private String calibrationStatus;

    @Column(name = "visual_status", length = 20)
    private String visualStatus;

    @Column(name = "dimensional_status", length = 20)
    private String dimensionalStatus;

    @Column(name = "material_test_status", length = 20)
    private String materialTestStatus;

    @Column(name = "packing_status", length = 20)
    private String packingStatus;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    // Audit Fields
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}

