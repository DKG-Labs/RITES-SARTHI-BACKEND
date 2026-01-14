package com.sarthi.service.processmaterial;

import com.sarthi.dto.processmaterial.ProcessInitiationDataDto;

/**
 * Service interface for fetching Process Material Inspection Initiation Data
 * Provides Section A (PO Info) and Section B (IC Details) for inspection initiation page
 */
public interface ProcessInitiationDataService {

    /**
     * Get inspection initiation data for a Process material call
     * Fetches PO information, IC details, and linked RM IC heat numbers
     * 
     * @param callNo - Process Inspection Call Number
     * @return ProcessInitiationDataDto with all sections data
     */
    ProcessInitiationDataDto getInitiationDataByCallNo(String callNo);
}

