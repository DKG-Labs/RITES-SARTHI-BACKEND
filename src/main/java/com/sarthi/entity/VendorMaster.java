package com.sarthi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "vendor_master")
@Data
public class VendorMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String vendorCode;   // IMMS_VENDOR_CODE

    private String vendorName;   // FIRM_DETAILS
    private String vendorDetails;

    private LocalDateTime createdDate;
}

