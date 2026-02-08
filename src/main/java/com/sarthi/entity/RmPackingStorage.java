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

    @Column(name = "stored_heat_wise", length = 10)
    private String storedHeatWise;

    @Column(name = "supplied_in_bundles", length = 10)
    private String suppliedInBundles;

    @Column(name = "heat_number_ends", length = 10)
    private String heatNumberEnds;

    @Column(name = "packing_strip_width", length = 10)
    private String packingStripWidth;

    @Column(name = "bundle_tied_locations", length = 10)
    private String bundleTiedLocations;

    @Column(name = "id_tag_bundle", length = 10)
    private String identificationTagBundle;

    @Column(name = "metal_tag_info", length = 10)
    private String metalTagInformation;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    @Column(name = "shift", length = 20)
    private String shift;

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


