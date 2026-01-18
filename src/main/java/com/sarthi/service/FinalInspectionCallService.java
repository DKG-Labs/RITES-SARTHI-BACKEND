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

    /**
     * Get Process IC certificate numbers for Final Inspection Call dropdown
     * Filters by vendor and EP prefix
     * @param vendorId - Vendor ID to filter
     * @return List of certificate numbers
     */
    List<String> getProcessIcCertificateNumbers(String vendorId);

    /**
     * Get RM IC numbers by Process IC certificate number
     * @param certificateNo - Process IC certificate number
     * @return List of RM IC numbers
     */
    List<String> getRmIcNumbersByCertificateNo(String certificateNo);

    /**
     * Get Lot numbers by RM IC number
     * @param rmIcNumber - RM IC number
     * @return List of lot numbers
     */
    List<String> getLotNumbersByRmIcNumber(String rmIcNumber);
}

