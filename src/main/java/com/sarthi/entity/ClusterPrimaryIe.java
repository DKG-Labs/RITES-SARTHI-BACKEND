package com.sarthi.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cluster_primary_ie")
@Data
public class ClusterPrimaryIe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String clusterName;

    private Integer ieUserId;

    private Date createdDate = new Date();
}
