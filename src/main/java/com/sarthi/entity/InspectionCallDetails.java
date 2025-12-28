package com.sarthi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing Section B: Inspection Call Details.
 * Stores inspection call specific information verified by IE.
 * Related to MainPoInformation (1:1) and SubPoDetails (1:N) via inspection_call_no.
 */
@Entity
@Table(name = "inspection_call_details", indexes = {
    @Index(name = "idx_call_details_call_no", columnList = "inspection_call_no"),
    @Index(name = "idx_call_details_status", columnList = "status")
})
@Data
@EqualsAndHashCode(callSuper = true)
public class InspectionCallDetails extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inspection_call_no", nullable = false, length = 50, unique = true)
    private String inspectionCallNo;

    @Column(name = "inspection_call_date")
    private LocalDate inspectionCallDate;

    @Column(name = "inspection_desired_date")
    private LocalDate inspectionDesiredDate;

    /* RLY + PO_NO + PO_SR combined reference */
    @Column(name = "rly_po_no_sr", length = 100)
    private String rlyPoNoSr;

    @Column(name = "item_desc", columnDefinition = "TEXT")
    private String itemDesc;

    @Column(name = "product_type", length = 50)
    private String productType;

    @Column(name = "po_qty", precision = 15, scale = 3)
    private BigDecimal poQty;

    @Column(name = "unit", length = 20)
    private String unit;

    @Column(name = "consignee_rly", length = 100)
    private String consigneeRly;

    @Column(name = "consignee", length = 200)
    private String consignee;

    @Column(name = "orig_dp", length = 50)
    private String origDp;

    @Column(name = "ext_dp", length = 50)
    private String extDp;

    @Column(name = "orig_dp_start")
    private LocalDate origDpStart;

    @Column(name = "stage_of_inspection", length = 100)
    private String stageOfInspection;

    @Column(name = "call_qty", precision = 15, scale = 3)
    private BigDecimal callQty;

    @Column(name = "place_of_inspection", length = 200)
    private String placeOfInspection;

    /* RM IC Number - Only for Process & Final Inspection */
    @Column(name = "rm_ic_number", length = 100)
    private String rmIcNumber;

    /* Process IC Number - Only for Final Inspection */
    @Column(name = "process_ic_number", length = 100)
    private String processIcNumber;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    /* Approval status: pending, approved, rejected */
    @Column(name = "status", length = 20)
    private String status = "pending";

    @Column(name = "rejection_remarks", columnDefinition = "TEXT")
    private String rejectionRemarks;

    /* Many-to-One relationship with MainPoInformation */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_po_id", referencedColumnName = "id")
    private MainPoInformation mainPoInformation;

    /* One-to-Many relationship with SubPoDetails */
    @OneToMany(mappedBy = "inspectionCallDetails", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SubPoDetails> subPoDetails = new ArrayList<>();

    /* Helper method to add SubPoDetails */
    public void addSubPoDetail(SubPoDetails subPo) {
        subPoDetails.add(subPo);
        subPo.setInspectionCallDetails(this);
    }

    /* Helper method to remove SubPoDetails */
    public void removeSubPoDetail(SubPoDetails subPo) {
        subPoDetails.remove(subPo);
        subPo.setInspectionCallDetails(null);
    }
}

