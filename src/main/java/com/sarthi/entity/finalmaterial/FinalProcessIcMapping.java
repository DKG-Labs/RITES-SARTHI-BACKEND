package com.sarthi.entity.finalmaterial;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity for Final to Process IC Mapping
 * Tracks the relationship between Final ICs and their parent Process ICs
 */
@Entity
@Table(name = "final_process_ic_mapping")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class FinalProcessIcMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    // ---- FINAL IC REFERENCE ----
    private Long finalIcId;

    // ---- PROCESS IC REFERENCE ----
    private Long processIcId;
    private String processIcNumber;

    // ---- LOT AND HEAT INFORMATION ----
    private String lotNumber;
    private String heatNumber;
    private String manufacturer;

    // ---- PROCESS IC DETAILS ----
    private Integer processQtyAccepted;
    private LocalDate processIcDate;

    // ---- TIMESTAMPS ----
    private LocalDateTime createdAt;
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

