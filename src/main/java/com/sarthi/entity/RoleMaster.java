package com.sarthi.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ROLE_MASTER")
@Data
public class RoleMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLEID")
    private Integer roleId;

    @Column(name = "ROLENAME", nullable = false)
    private String roleName;

    @Column(name = "CREATEDBY")
    private String createdBy;

    @Column(name = "CREATEDDATE")
    private LocalDateTime createdDate = LocalDateTime.now();
}
