package com.sarthi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity representing Section A: Main PO Information.
 * Stores PO details verified by IE during inspection initiation.
 * Related to InspectionCallDetails via inspection_call_no.
 */
@Entity
@Table(name = "main_po_information", indexes = {
    @Index(name = "idx_main_po_call_no", columnList = "inspection_call_no"),
    @Index(name = "idx_main_po_po_no", columnList = "po_no"),
    @Index(name = "idx_main_po_status", columnList = "status")
})
@Data
@EqualsAndHashCode(callSuper = true)
public class MainPoInformation extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inspection_call_no", nullable = false, length = 50, unique = true)
    private String inspectionCallNo;

    @Column(name = "po_no", length = 50)
    private String poNo;

    @Column(name = "po_date")
    private LocalDate poDate;

    @Column(name = "vendor_code", length = 50)
    private String vendorCode;

    @Column(name = "vendor_name", length = 200)
    private String vendorName;

    @Column(name = "vendor_address", columnDefinition = "TEXT")
    private String vendorAddress;

    @Column(name = "place_of_inspection", length = 200)
    private String placeOfInspection;

    @Column(name = "manufacturer", length = 200)
    private String manufacturer;

    @Column(name = "consignee_rly", length = 100)
    private String consigneeRly;

    @Column(name = "consignee", length = 200)
    private String consignee;

    @Column(name = "item_description", columnDefinition = "TEXT")
    private String itemDescription;

    @Column(name = "po_qty", precision = 15, scale = 3)
    private BigDecimal poQty;

    @Column(name = "unit", length = 20)
    private String unit;

    @Column(name = "orig_dp", length = 50)
    private String origDp;

    @Column(name = "ext_dp", length = 50)
    private String extDp;

    @Column(name = "orig_dp_start")
    private LocalDate origDpStart;

    @Column(name = "bpo", length = 200)
    private String bpo;

    @Column(name = "date_of_inspection")
    private LocalDate dateOfInspection;

    @Column(name = "shift_of_inspection", length = 20)
    private String shiftOfInspection;

    @Column(name = "offered_qty", precision = 15, scale = 3)
    private BigDecimal offeredQty;

    /* Approval status: pending, approved, rejected */
    @Column(name = "status", length = 20)
    private String status = "pending";

    @Column(name = "rejection_remarks", columnDefinition = "TEXT")
    private String rejectionRemarks;

    /* One-to-One relationship with InspectionCallDetails */
    @OneToOne(mappedBy = "mainPoInformation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private InspectionCallDetails inspectionCallDetails;
}

