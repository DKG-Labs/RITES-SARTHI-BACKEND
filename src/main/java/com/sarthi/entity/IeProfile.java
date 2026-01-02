package com.sarthi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ie_profile")
@Data
public class IeProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_code", nullable = false)
    private String employeeCode;

    @Column(name = "rio")
    private String rio; // NRIO / ERIO

    @Column(name = "current_city_of_posting")
    private String currentCityOfPosting;

    @Column(name = "metal_stamp_no")
    private Integer metalStampNo;
}

