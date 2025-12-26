package com.sarthi.entity.rawmaterial;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity: rm_inspection_details
 * Raw Material specific inspection details - maps to existing database schema.
 *
 * Relationships:
 * - One-to-One with InspectionCall (child side via ic_id)
 * - One-to-Many with RmHeatQuantity (via rm_detail_id)
 */
@Entity
@Table(name = "rm_inspection_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"inspectionCall", "rmHeatQuantities"})
@ToString(exclude = {"inspectionCall", "rmHeatQuantities"})
public class RmInspectionDetails {

    @Id
    @Column(name = "id")
    private Integer id;

    /* ==================== Parent Reference ==================== */

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ic_id")
    @JsonIgnore
    private InspectionCall inspectionCall;

    /* ==================== Item Details ==================== */

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "item_quantity")
    private Integer itemQuantity;

    @Column(name = "consignee_zonal_railway")
    private String consigneeZonalRailway;

    @Column(name = "heat_numbers")
    private String heatNumbers;

    /* ==================== TC Details ==================== */

    @Column(name = "tc_number")
    private String tcNumber;

    @Column(name = "tc_date")
    private String tcDate;

    @Column(name = "tc_quantity")
    private Double tcQuantity;

    /* ==================== Manufacturer/Supplier ==================== */

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "supplier_address")
    private String supplierAddress;

    /* ==================== Invoice Details ==================== */

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "invoice_date")
    private String invoiceDate;

    /* ==================== Sub PO Details ==================== */

    @Column(name = "sub_po_number")
    private String subPoNumber;

    @Column(name = "sub_po_date")
    private String subPoDate;

    @Column(name = "sub_po_qty")
    private String subPoQty;

    /* ==================== Quantity Details ==================== */

    @Column(name = "total_offered_qty_mt")
    private Double totalOfferedQtyMt;

    @Column(name = "offered_qty_erc")
    private Integer offeredQtyErc;

    @Column(name = "unit_of_measurement")
    private String unitOfMeasurement;

    /* ==================== Rate Details ==================== */

    @Column(name = "rate_of_material")
    private String rateOfMaterial;

    @Column(name = "rate_of_gst")
    private String rateOfGst;

    @Column(name = "base_value_po")
    private String baseValuePo;

    @Column(name = "total_po")
    private String totalPo;

    /* ==================== Audit Fields ==================== */

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /* ==================== Relationships ==================== */

    /** One-to-Many: Heat-wise quantity breakdown */
    @OneToMany(mappedBy = "rmInspectionDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RmHeatQuantity> rmHeatQuantities = new ArrayList<>();
}

