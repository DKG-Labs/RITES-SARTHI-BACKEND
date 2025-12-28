package com.sarthi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity representing Section C: Sub PO Details.
 * Stores sub PO / heat details for Raw Material and Process inspections.
 * Multiple SubPoDetails can be linked to one InspectionCallDetails (1:N relationship).
 */
@Entity
@Table(name = "sub_po_details", indexes = {
    @Index(name = "idx_sub_po_call_no", columnList = "inspection_call_no"),
    @Index(name = "idx_sub_po_sub_po_no", columnList = "sub_po_no"),
    @Index(name = "idx_sub_po_heat_no", columnList = "heat_no")
})
@Data
@EqualsAndHashCode(callSuper = true)
public class SubPoDetails extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inspection_call_no", nullable = false, length = 50)
    private String inspectionCallNo;

    @Column(name = "raw_material_name", length = 200)
    private String rawMaterialName;

    @Column(name = "grade_spec", length = 100)
    private String gradeSpec;

    @Column(name = "heat_no", length = 100)
    private String heatNo;

    @Column(name = "manufacturer_steel_bars", length = 200)
    private String manufacturerSteelBars;

    @Column(name = "tc_no", length = 100)
    private String tcNo;

    @Column(name = "tc_date")
    private LocalDate tcDate;

    @Column(name = "sub_po_no", length = 50)
    private String subPoNo;

    @Column(name = "sub_po_date")
    private LocalDate subPoDate;

    @Column(name = "invoice_no", length = 100)
    private String invoiceNo;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "sub_po_qty", precision = 15, scale = 3)
    private BigDecimal subPoQty;

    @Column(name = "unit", length = 20)
    private String unit;

    /* Place of inspection - common for all heats */
    @Column(name = "place_of_inspection", length = 200)
    private String placeOfInspection;

    /* Approval status: pending, approved, rejected */
    @Column(name = "status", length = 20)
    private String status = "pending";

    @Column(name = "rejection_remarks", columnDefinition = "TEXT")
    private String rejectionRemarks;

    /* Many-to-One relationship with InspectionCallDetails */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspection_call_details_id", referencedColumnName = "id")
    private InspectionCallDetails inspectionCallDetails;
}

