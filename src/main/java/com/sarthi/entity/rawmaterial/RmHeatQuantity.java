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
@Table(name = "rm_heat_quantities")
@Data
public class RmHeatQuantity {

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
    private String manufacturer;

    private BigDecimal offeredQty;

    private String tcNumber;
    private LocalDate tcDate;
    private BigDecimal tcQuantity;

    private BigDecimal qtyLeft;
    private BigDecimal qtyAccepted;
    private BigDecimal qtyRejected;

    @Column(columnDefinition = "TEXT")
    private String rejectionReason;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


