package com.sarthi.dto;

import lombok.Data;

/**
<<<<<<< HEAD
 * DTO for packing and storage checklist.
=======
 * DTO for packing and storage checklist - per heat.
 * Each heat has its own packing & storage verification.
>>>>>>> b25fb8a13f91b1c2c4f3aa1c8ed8ab67a7380ee8
 */
@Data
public class RmPackingStorageDto {

    private String inspectionCallNo;
    private String heatNo;
    private Integer heatIndex;

    private String bundlingSecure;
    private String tagsAttached;
    private String labelsCorrect;
    private String protectionAdequate;
    private String storageCondition;
    private String moistureProtection;
    private String stackingProper;
    private String remarks;
}

