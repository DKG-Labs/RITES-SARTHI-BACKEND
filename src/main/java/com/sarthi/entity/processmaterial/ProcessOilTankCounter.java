package com.sarthi.entity.processmaterial;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Entity for Oil Tank Counter tracking in Process Material Inspection.
 * Tracks ERC quenched count since last cleaning.
 */
@Entity
@Table(name = "process_oil_tank_counter", indexes = {
    @Index(name = "idx_proc_oil_call_no", columnList = "inspection_call_no"),
    @Index(name = "idx_proc_oil_po_no", columnList = "po_no")
})
@Data
public class ProcessOilTankCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inspection_call_no", nullable = false, length = 50)
    private String inspectionCallNo;

    @Column(name = "po_no", nullable = false, length = 50)
    private String poNo;

    @Column(name = "line_no", nullable = false, length = 20)
    private String lineNo;

    // Oil tank counter data
    @Column(name = "oil_tank_counter")
    private Integer oilTankCounter = 0;

    @Column(name = "cleaning_done")
    private Boolean cleaningDone = false;

    @Column(name = "cleaning_done_at")
    private LocalDateTime cleaningDoneAt;

    @Column(name = "is_locked")
    private Boolean isLocked = false;

    @Column(name = "counter_status", length = 20)
    private String counterStatus;

    @Column(name = "remarks", length = 500)
    private String remarks;

    // Audit fields
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "updated_by", length = 100)
    private String updatedBy;

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

