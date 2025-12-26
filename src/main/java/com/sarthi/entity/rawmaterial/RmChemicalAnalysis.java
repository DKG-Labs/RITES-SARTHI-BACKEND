package com.sarthi.entity.rawmaterial;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Entity: rm_chemical_analysis
 * Note: This table currently mirrors inspection_calls structure in the database.
 * Keeping basic entity for future chemical analysis implementation.
 *
 * Relationship:
 * - Many-to-One with RmHeatQuantity (for future use)
 */
@Entity
@Table(name = "rm_chemical_analysis")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "rmHeatQuantity")
@ToString(exclude = "rmHeatQuantity")
public class RmChemicalAnalysis {

    @Id
    @Column(name = "id")
    private Integer id;

    /* ==================== Parent Reference (for future) ==================== */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    @JsonIgnore
    private RmHeatQuantity rmHeatQuantity;

    /* ==================== Current DB fields match inspection_calls ==================== */

    @Column(name = "ic_number")
    private String icNumber;

    @Column(name = "po_no")
    private String poNo;

    @Column(name = "po_serial_no")
    private String poSerialNo;

    @Column(name = "type_of_call")
    private String typeOfCall;

    @Column(name = "status")
    private String status;

    @Column(name = "desired_inspection_date")
    private String desiredInspectionDate;

    @Column(name = "actual_inspection_date")
    private String actualInspectionDate;

    @Column(name = "place_of_inspection")
    private String placeOfInspection;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "unit_id")
    private Integer unitId;

    @Column(name = "unit_name")
    private String unitName;

    @Column(name = "unit_address")
    private String unitAddress;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;
}

