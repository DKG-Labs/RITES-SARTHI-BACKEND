package com.sarthi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity representing heat number to TC (Test Certificate) mapping.
 * Each inspection request can have multiple heat-TC mappings.
 */
@Entity
@Table(name = "rm_heat_tc_mapping", indexes = {
    @Index(name = "idx_inspection_request_id", columnList = "inspection_request_id"),
    @Index(name = "idx_heat_number", columnList = "heat_number"),
    @Index(name = "idx_tc_number", columnList = "tc_number")
})
@Data
@EqualsAndHashCode(exclude = "inspectionRequest")
@ToString(exclude = "inspectionRequest")
public class RmHeatTcMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Foreign Key to parent inspection request
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspection_request_id", nullable = false)
    @JsonIgnore
    private VendorInspectionRequest inspectionRequest;

    // Heat & TC Information
    @Column(name = "heat_number", nullable = false, length = 50)
    private String heatNumber;

    @Column(name = "tc_number", length = 50)
    private String tcNumber;

    @Column(name = "tc_date")
    private LocalDate tcDate;

    @Column(name = "manufacturer", length = 200)
    private String manufacturer;

    // Invoice Information
    @Column(name = "invoice_no", length = 50)
    private String invoiceNo;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    // Sub PO Information
    @Column(name = "sub_po_number", length = 50)
    private String subPoNumber;

    @Column(name = "sub_po_date")
    private LocalDate subPoDate;

    @Column(name = "sub_po_qty", length = 50)
    private String subPoQty;

    @Column(name = "sub_po_total_value", length = 50)
    private String subPoTotalValue;

    // TC Quantities
    @Column(name = "tc_qty", length = 50)
    private String tcQty;

    @Column(name = "tc_qty_remaining", length = 50)
    private String tcQtyRemaining;

    @Column(name = "offered_qty", length = 50)
    private String offeredQty;

    // Audit Fields
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}

