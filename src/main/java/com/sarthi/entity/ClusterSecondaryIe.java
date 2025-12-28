package com.sarthi.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cluster_secondary_ie")
@Data
public class ClusterSecondaryIe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String clusterName;

    private Integer ieUserId;

    private Integer priorityOrder;

    private Date createdDate = new Date();

}
