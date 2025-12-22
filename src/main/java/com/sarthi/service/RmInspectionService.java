package com.sarthi.service;

import com.sarthi.dto.RmFinishInspectionDto;

/**
 * Service interface for Raw Material inspection operations.
 */
public interface RmInspectionService {

    /**
     * Save all Raw Material inspection data when inspector finishes inspection.
     * @param dto The complete inspection data payload
     * @return Success message
     */
    String finishInspection(RmFinishInspectionDto dto);

    /**
     * Get all inspection data by call number.
     * @param callNo The inspection call number
     * @return Complete inspection data
     */
    RmFinishInspectionDto getByCallNo(String callNo);
}

