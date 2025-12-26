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
 * Entity: rm_heat_quantities
 * Heat-wise quantity breakdown for Raw Material inspection - maps to existing database.
 *
 * Relationships:
 * - Many-to-One with RmInspectionDetails (child side via rm_detail_id)
 * - One-to-Many with RmChemicalAnalysis (parent side)
 */
@Entity
@Table(name = "rm_heat_quantities")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"rmInspectionDetails", "chemicalAnalyses"})
@ToString(exclude = {"rmInspectionDetails", "chemicalAnalyses"})
public class RmHeatQuantity {

    @Id
    @Column(name = "id")
    private Integer id;

    /* ==================== Parent Reference ==================== */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rm_detail_id")
    @JsonIgnore
    private RmInspectionDetails rmInspectionDetails;

    /* ==================== Heat Information ==================== */

    @Column(name = "heat_number")
    private String heatNumber;

    @Column(name = "manufacturer")
    private String manufacturer;

    /* ==================== Quantity Details ==================== */

    @Column(name = "offered_qty")
    private Double offeredQty;

    /* ==================== TC Details ==================== */

    @Column(name = "tc_number")
    private String tcNumber;

    @Column(name = "tc_date")
    private String tcDate;

    @Column(name = "tc_quantity")
    private Double tcQuantity;

    /* ==================== Inspection Results ==================== */

    @Column(name = "qty_left")
    private String qtyLeft;

    @Column(name = "qty_accepted")
    private String qtyAccepted;

    @Column(name = "qty_rejected")
    private String qtyRejected;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    /* ==================== Audit Fields ==================== */

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /* ==================== Relationships ==================== */

    /** One-to-Many: Chemical composition analysis per heat */
    @OneToMany(mappedBy = "rmHeatQuantity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RmChemicalAnalysis> chemicalAnalyses = new ArrayList<>();
}

