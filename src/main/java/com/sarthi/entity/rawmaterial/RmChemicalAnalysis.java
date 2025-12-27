package com.sarthi.entity.rawmaterial;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "rm_chemical_analysis")
@Data
public class RmChemicalAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ---- RELATION ----
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rm_detail_id", nullable = false)
<<<<<<< Updated upstream
=======
    @JsonIgnore  // Prevent circular reference during JSON serialization
>>>>>>> Stashed changes
    private RmInspectionDetails rmInspectionDetails;

    private String heatNumber;

    private BigDecimal carbon;
    private BigDecimal manganese;
    private BigDecimal silicon;
    private BigDecimal sulphur;
    private BigDecimal phosphorus;
    private BigDecimal chromium;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

