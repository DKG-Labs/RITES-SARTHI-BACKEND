package com.sarthi.entity.finalmaterial;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity for Final Inspection Summary
 * Stores final inspection results and packing verification data
 */
@Entity
@Table(name = "final_inspection_summary", indexes = {
    @Index(name = "idx_final_summary_call_no", columnList = "inspection_call_no"),
    @Index(name = "idx_final_summary_status", columnList = "inspection_status")
})
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class FinalInspectionSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    private String inspectionCallNo;

    // ---- PACKING VERIFICATION ----
    private Boolean packedInHdpe = false;
    private Boolean cleanedWithCoating = false;

    // ---- STATUS ----
    private String inspectionStatus = "PENDING";

    // ---- AUDIT FIELDS ----
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

