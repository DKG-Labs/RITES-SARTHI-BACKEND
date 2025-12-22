package com.sarthi.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "region_cluster")
@Data
public class RegionCluster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String clusterName;

    private String regionName;

    private Date createdDate = new Date();
}
