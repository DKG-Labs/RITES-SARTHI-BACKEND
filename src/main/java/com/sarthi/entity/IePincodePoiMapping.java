package com.sarthi.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ie_pincode_poi_mapping")
@Data
public class IePincodePoiMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_code", nullable = false, length = 50)
    private String employeeCode;

    @Column(name = "product", nullable = false, length = 50)
    private String product;

    @Column(name = "pin_code", nullable = false, length = 10)
    private String pinCode;

    @Column(name = "poi_code", nullable = false, length = 50)
    private String poiCode;

    @Column(name = "ie_type", nullable = false, length = 20)
    private String ieType; // PRIMARY / SECONDARY
}
