package com.sarthi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "region_sbu_head")
@Data
public class RegionSbuHead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String regionName;

    private Integer sbuHeadUserId;

    private Date createdDate = new Date();
}
