package com.sarthi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ie_controlling_manager")
@Data
public class IeControllingManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ie_employee_code", nullable = false, length = 50)
    private String ieEmployeeCode;

    @Column(name = "cm_user_id", nullable = false)
    private Integer cmUserId;
}

