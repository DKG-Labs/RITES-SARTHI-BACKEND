package com.sarthi.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cluster_cm_user")
@Data
public class ClusterCmUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String clusterName;

    private Integer cmUserId;

    private Date createdDate = new Date();
}
