package com.sarthi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "IE_POI_MAPPING")
@Data
public class IePoiMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "IE_USER_ID", nullable = false)
    private Long ieUserId;

    @Column(name = "POI_CODE", nullable = false)
    private String poiCode;

    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
}

