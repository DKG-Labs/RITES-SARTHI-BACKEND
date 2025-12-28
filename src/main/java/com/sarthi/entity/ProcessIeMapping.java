package com.sarthi.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "process_ie_mapping")
@Data
public class ProcessIeMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // FK to Process IE user
    @Column(name = "process_ie_user_id", nullable = false)
    private Integer processIeUserId;

    // Mapped IE user
    @Column(name = "ie_user_id", nullable = false)
    private Integer ieUserId;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Date createdDate = new Date();


}
