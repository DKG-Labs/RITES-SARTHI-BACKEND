package com.sarthi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "FINAL_IE_MAPPING")
@Data
public class FinalIeMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "WORKFLOW_TRANSITION_ID")
    private Integer workflowTransitionId;

    @Column(name = "IE_USER_ID")
    private Integer ieUserId;

    @Column(name = "CREATED_ON")
    private Date createdOn = new Date();
}

