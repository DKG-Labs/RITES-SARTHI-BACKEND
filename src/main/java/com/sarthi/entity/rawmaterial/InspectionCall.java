package com.sarthi.entity.rawmaterial;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity: inspection_calls
 * Main table for all inspection calls - maps to existing database schema.
 *
 * Relationships:
 * - One InspectionCall can have one RmInspectionDetails (1:1 via ic_id)
 */
@Entity
@Table(name = "inspection_calls")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InspectionCall {

    @Id
    @Column(name = "id")
    private Integer id;

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

    /* ==================== Relationships ==================== */

    /** One-to-One: RM-specific inspection details */
    @OneToOne(mappedBy = "inspectionCall", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private RmInspectionDetails rmInspectionDetails;
}

