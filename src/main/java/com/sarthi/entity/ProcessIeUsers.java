package com.sarthi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "PROCESS_IE_USERS")
@Data
public class ProcessIeUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PROCESS_USER_ID", nullable = false)
    private Long processUserId;

    @Column(name = "IE_USER_ID", nullable = false)
    private Long ieUserId;

    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
}

