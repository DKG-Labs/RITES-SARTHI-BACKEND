package com.sarthi.service;

import com.sarthi.dto.IcDtos.FinalInspectionDetailsRequestDto;
import com.sarthi.dto.IcDtos.FinalInspectionLotDetailsRequestDto;
import com.sarthi.dto.IcDtos.InspectionCallRequestDto;
import com.sarthi.entity.rawmaterial.InspectionCall;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface for Final Inspection Call operations
 */
@Service
public interface FinalInspectionCallService {

    /**
     * Create a new Final Inspection Call
     * @param icRequest - Inspection Call basic details
     * @param finalDetails - Final Inspection summary details
     * @param lotDetailsList - List of Final Inspection Lot Details
     * @return Created InspectionCall entity
     */
    InspectionCall createFinalInspectionCall(
            InspectionCallRequestDto icRequest,
            FinalInspectionDetailsRequestDto finalDetails,
            List<FinalInspectionLotDetailsRequestDto> lotDetailsList
    );
}

