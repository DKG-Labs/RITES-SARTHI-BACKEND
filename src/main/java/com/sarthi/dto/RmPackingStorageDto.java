package com.sarthi.dto;

import lombok.Data;

/**
 * DTO for packing and storage checklist.
 */
@Data
public class RmPackingStorageDto {

    private String inspectionCallNo;
    private String bundlingSecure;
    private String tagsAttached;
    private String labelsCorrect;
    private String protectionAdequate;
    private String storageCondition;
    private String moistureProtection;
    private String stackingProper;
    private String remarks;
}

