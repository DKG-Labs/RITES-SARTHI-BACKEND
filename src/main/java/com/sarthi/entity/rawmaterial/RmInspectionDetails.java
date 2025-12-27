package com.sarthi.entity.rawmaterial;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "rm_inspection_details")
@Data
public class RmInspectionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ---- RELATION ----
    @OneToOne
    @JoinColumn(name = "ic_id", nullable = false)
// <<<<<<< Updated upstream
// =======
//     @JsonIgnore  // Prevent circular reference during JSON serialization
// >>>>>>> Stashed changes
    private InspectionCall inspectionCall;

    // ---- ITEM DETAILS ----
    @Column(columnDefinition = "TEXT")
    private String itemDescription;

    private Integer itemQuantity;

    private String consigneeZonalRailway;

    @Column(columnDefinition = "TEXT")
    private String heatNumbers;

    private String tcNumber;
    private LocalDate tcDate;
    private BigDecimal tcQuantity;

    private String manufacturer;
    private String supplierName;

    @Column(columnDefinition = "TEXT")
    private String supplierAddress;

    private String invoiceNumber;
    private LocalDate invoiceDate;

    private String subPoNumber;
    private LocalDate subPoDate;
    private Integer subPoQty;

    private BigDecimal totalOfferedQtyMt;
    private Integer offeredQtyErc;

    private String unitOfMeasurement;

    private BigDecimal rateOfMaterial;
    private BigDecimal rateOfGst;
    private BigDecimal baseValuePo;
    private BigDecimal totalPo;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ---- CHILD TABLES ----
    @OneToMany(mappedBy = "rmInspectionDetails", cascade = CascadeType.ALL)
    private List<RmHeatQuantity> heatQuantities;

    @OneToMany(mappedBy = "rmInspectionDetails", cascade = CascadeType.ALL)
    private List<RmChemicalAnalysis> chemicalAnalysisList;
}

