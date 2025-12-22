package com.sarthi.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "process_ie_master")
@Data
public class ProcessIeMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Process IE is also a user, so store the userId
    @Column(name = "process_ie_user_id", nullable = false)
    private Integer processIeUserId;

    @Column(name = "cluster_name", nullable = false)
    private String clusterName;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Date createdDate = new Date();
}
