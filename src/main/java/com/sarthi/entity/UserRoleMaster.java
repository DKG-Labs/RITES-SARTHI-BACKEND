package com.sarthi.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "USER_ROLE_MASTER")
@Data
public class UserRoleMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERROLEID")
    private Integer userRoleId;

    @Column(name = "USERID", nullable = false)
    private Integer userId;

    @Column(name = "ROLEID", nullable = false)
    private Integer roleId;

    @Column(name = "READPERMISSION")
    private Boolean readPermission = true;

    @Column(name = "WRITEPERMISSION")
    private Boolean writePermission = true;

    @Column(name = "CREATEDBY")
    private String createdBy;

    @Column(name = "CREATEDDATE")
    private Date createdDate = new Date();
}

