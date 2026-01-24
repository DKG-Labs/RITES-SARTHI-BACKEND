package com.sarthi.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
@Entity
@Table(name = "rm_packing_storage", indexes = {
        @Index(name = "idx_rm_pack_call_no", columnList = "inspection_call_no"),
        @Index(name = "idx_rm_pack_heat_no", columnList = "heat_no")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_rm_pack_call_heat", columnNames = {"inspection_call_no", "heat_no"})
})
@Data
public class RmPackingStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inspection_call_no", nullable = false, length = 50)
    private String inspectionCallNo;

    @Column(name = "heat_no", length = 50)
    private String heatNo;

    @Column(name = "heat_index")
    private Integer heatIndex;

    @Column(name = "bundling_secure", length = 10)
    private String bundlingSecure;

    @Column(name = "tags_attached", length = 10)
    private String tagsAttached;

    @Column(name = "labels_correct", length = 10)
    private String labelsCorrect;

    @Column(name = "protection_adequate", length = 10)
    private String protectionAdequate;

    @Column(name = "storage_condition", length = 10)
    private String storageCondition;

    @Column(name = "moisture_protection", length = 10)
    private String moistureProtection;

    @Column(name = "stacking_proper", length = 10)
    private String stackingProper;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    // Audit Fields
    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_by", length = 100)
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}


