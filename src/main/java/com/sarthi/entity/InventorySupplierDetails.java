package com.sarthi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory_supplier_details")
public class InventorySupplierDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String role;
        private String product;
        private String cin;

        @Column(name = "company_name")
        private String companyName;

        @Column(name = "unit_name")
        private String unitName;

        private String address;
        private String district;
        private String state;
        private String pin;
        private String email;

        @Column(name = "mobile_number")
        private String mobileNumber;




}
