package com.sarthi.dto;

import lombok.Data;

/**
 * DTO for packing and storage checklist - per heat.
 * Each heat has its own packing & storage verification.
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

