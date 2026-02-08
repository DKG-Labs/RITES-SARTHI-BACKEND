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

    private String storedHeatWise;
    private String suppliedInBundles;
    private String heatNumberEnds;
    private String packingStripWidth;
    private String bundleTiedLocations;
    private String identificationTagBundle;
    private String metalTagInformation;
    private String remarks;
    private String shift;
}

