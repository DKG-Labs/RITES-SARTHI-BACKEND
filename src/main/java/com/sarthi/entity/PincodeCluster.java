package com.sarthi.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pincode_cluster")
@Data
public class PincodeCluster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String pincode;

    private String clusterName;

    private Date createdDate = new Date();
}
