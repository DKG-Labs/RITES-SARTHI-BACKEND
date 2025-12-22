package com.sarthi.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity representing calibration and documents verification for Raw Material.
 * Stores RDSO approval details and ladle analysis values per heat.
 */
@Entity
@Table(name = "rm_calibration_documents", indexes = {
    @Index(name = "idx_rm_cal_call_no", columnList = "inspection_call_no"),
    @Index(name = "idx_rm_cal_heat_no", columnList = "heat_no")
})
@Data
public class RmCalibrationDocuments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inspection_call_no", nullable = false, length = 50)
    private String inspectionCallNo;

    @Column(name = "heat_no", nullable = false, length = 50)
    private String heatNo;

    @Column(name = "heat_index")
    private Integer heatIndex;

    // RDSO Approval Details
    @Column(name = "rdso_approval_id", length = 50)
    private String rdsoApprovalId;

    @Column(name = "rdso_valid_from")
    private LocalDate rdsoValidFrom;

    @Column(name = "rdso_valid_to")
    private LocalDate rdsoValidTo;

    @Column(name = "gauges_available")
    private Boolean gaugesAvailable = false;

    // Ladle Chemical Composition (Product Values)
    @Column(name = "ladle_carbon_percent", precision = 6, scale = 4)
    private BigDecimal ladleCarbonPercent;

    @Column(name = "ladle_silicon_percent", precision = 6, scale = 4)
    private BigDecimal ladleSiliconPercent;

    @Column(name = "ladle_manganese_percent", precision = 6, scale = 4)
    private BigDecimal ladleManganesePercent;

    @Column(name = "ladle_phosphorus_percent", precision = 6, scale = 4)
    private BigDecimal ladlePhosphorusPercent;

    @Column(name = "ladle_sulphur_percent", precision = 6, scale = 4)
    private BigDecimal ladleSulphurPercent;

    // Vendor Verification
    @Column(name = "vendor_verified")
    private Boolean vendorVerified = false;

    @Column(name = "verified_by", length = 100)
    private String verifiedBy;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}

