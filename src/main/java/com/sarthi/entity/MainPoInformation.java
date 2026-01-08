package com.sarthi.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @Column(name = "po_qty", precision = 15, scale = 3)
    private BigDecimal poQty;

    @Column(name = "place_of_inspection", length = 200)
    private String placeOfInspection;

    @Column(name = "vendor_name", length = 200)
    private String vendorName;

    @Column(name = "ma_no", length = 100)
    private String maNo;

    @Column(name = "ma_date", length = 255)
    private String maDate;

    @Column(name = "purchasing_authority", length = 200)
    private String purchasingAuthority;

    @Column(name = "bill_paying_officer", length = 200)
    private String billPayingOfficer;

    /* Approval status: pending, approved, rejected */
    @Column(name = "status", length = 20)
    private String status = "pending";

    @Column(name = "rejection_remarks", columnDefinition = "TEXT")
    private String rejectionRemarks;

    /* One-to-One relationship with InspectionCallDetails */
    @OneToOne(mappedBy = "mainPoInformation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private InspectionCallDetails inspectionCallDetails;
}

