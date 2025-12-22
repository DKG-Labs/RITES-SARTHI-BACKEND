package com.sarthi.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cluster_rio_user")
@Data
public class ClusterRioUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String clusterName;

   // private String regionName;

    private Integer rioUserId;

    private Date createdDate = new Date();

}
