package com.sarthi.entity.rawmaterial;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inspection_calls")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class InspectionCall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String vendorId;

    private String icNumber;
    private String poNo;
    private String poSerialNo;


    private String typeOfCall;

    private String status;

    private LocalDate desiredInspectionDate;
    private LocalDate actualInspectionDate;

    // ---- LOCATION ----
    private String placeOfInspection;

    private Integer companyId;
    private String companyName;

    private Integer unitId;
    private String unitName;

    @Column(columnDefinition = "TEXT")
    private String unitAddress;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    private String createdBy;
    private String updatedBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    // ---- RELATION ----
    @OneToOne(mappedBy = "inspectionCall", cascade = CascadeType.ALL)
    @ToString.Exclude
    private RmInspectionDetails rmInspectionDetails;
}

