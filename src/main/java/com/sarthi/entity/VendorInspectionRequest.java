package com.sarthi.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing vendor inspection request data received from vendor API.
 * Contains PO information, inspection details, chemical composition, and company/unit info.
 */
@Entity
@Table(name = "vendor_inspection_request", indexes = {
    @Index(name = "idx_po_no", columnList = "po_no"),
    @Index(name = "idx_po_serial_no", columnList = "po_serial_no"),
    @Index(name = "idx_type_of_call", columnList = "type_of_call"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_vendor_code", columnList = "vendor_code")
})
@Data
public class VendorInspectionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // PO Information
    @Column(name = "po_no", nullable = false, length = 50)
    private String poNo;

    @Column(name = "po_serial_no", length = 50)
    private String poSerialNo;

    @Column(name = "po_date")
    private LocalDate poDate;

    @Column(name = "po_description", length = 255)
    private String poDescription;

    @Column(name = "po_qty")
    private Integer poQty;

    @Column(name = "po_unit", length = 20)
    private String poUnit;

    @Column(name = "amendment_no", length = 50)
    private String amendmentNo;

    @Column(name = "amendment_date")
    private LocalDate amendmentDate;

    // Vendor Information
    @Column(name = "vendor_code", length = 50)
    private String vendorCode;

    @Column(name = "vendor_contact_name", length = 100)
    private String vendorContactName;

    @Column(name = "vendor_contact_phone", length = 20)
    private String vendorContactPhone;

    // Inspection Call Info
    @Column(name = "type_of_call", length = 50)
    private String typeOfCall;

    @Column(name = "desired_inspection_date")
    private LocalDate desiredInspectionDate;

    // Already Inspected Quantities
    @Column(name = "qty_already_inspected_rm")
    private Integer qtyAlreadyInspectedRm = 0;

    @Column(name = "qty_already_inspected_process")
    private Integer qtyAlreadyInspectedProcess = 0;

    @Column(name = "qty_already_inspected_final")
    private Integer qtyAlreadyInspectedFinal = 0;

    // Raw Material Heat Numbers (comma separated)
    @Column(name = "rm_heat_numbers", length = 500)
    private String rmHeatNumbers;

    // Chemical Composition
    @Column(name = "rm_chemical_carbon", precision = 6, scale = 4)
    private BigDecimal rmChemicalCarbon;

    @Column(name = "rm_chemical_manganese", precision = 6, scale = 4)
    private BigDecimal rmChemicalManganese;

    @Column(name = "rm_chemical_silicon", precision = 6, scale = 4)
    private BigDecimal rmChemicalSilicon;

    @Column(name = "rm_chemical_sulphur", precision = 6, scale = 4)
    private BigDecimal rmChemicalSulphur;

    @Column(name = "rm_chemical_phosphorus", precision = 6, scale = 4)
    private BigDecimal rmChemicalPhosphorus;

    @Column(name = "rm_chemical_chromium", precision = 6, scale = 4)
    private BigDecimal rmChemicalChromium;

    // Offered Quantities
    @Column(name = "rm_total_offered_qty_mt", precision = 10, scale = 3)
    private BigDecimal rmTotalOfferedQtyMt;

    @Column(name = "rm_offered_qty_erc")
    private Integer rmOfferedQtyErc;

    // Company Information
    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "company_name", length = 200)
    private String companyName;

    @Column(name = "cin", length = 50)
    private String cin;

    // Unit Information
    @Column(name = "unit_id")
    private Integer unitId;

    @Column(name = "unit_name", length = 100)
    private String unitName;

    @Column(name = "unit_address", length = 500)
    private String unitAddress;

    @Column(name = "unit_gstin", length = 20)
    private String unitGstin;

    @Column(name = "unit_contact_person", length = 100)
    private String unitContactPerson;

    @Column(name = "unit_role", length = 50)
    private String unitRole;

    // PO Additional Information
    @Column(name = "purchasing_authority", length = 100)
    private String purchasingAuthority;

    @Column(name = "bpo", length = 100)
    private String bpo;

    @Column(name = "delivery_period", length = 50)
    private String deliveryPeriod;

    @Column(name = "inspection_fees_payment_details", length = 255)
    private String inspectionFeesPaymentDetails;

    // Remarks
    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    // Status and Audit Fields
    @Column(name = "status", length = 20)
    private String status = "PENDING";

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // One-to-Many relationship with Heat TC Mapping
    @OneToMany(mappedBy = "inspectionRequest", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RmHeatTcMapping> rmHeatTcMappings = new ArrayList<>();

    // Helper method to add heat TC mapping
    public void addHeatTcMapping(RmHeatTcMapping mapping) {
        rmHeatTcMappings.add(mapping);
        mapping.setInspectionRequest(this);
    }
}

