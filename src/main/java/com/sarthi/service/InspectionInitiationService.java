package com.sarthi.service;

import com.sarthi.dto.InspectionInitiationDto;

import java.util.List;

/**
 * Service interface for inspection initiation operations.
 */
public interface InspectionInitiationService {

    InspectionInitiationDto createInitiation(InspectionInitiationDto dto);

    InspectionInitiationDto updateInitiation(Long id, InspectionInitiationDto dto);

    InspectionInitiationDto getById(Long id);

    InspectionInitiationDto getByCallNo(String callNo);

    InspectionInitiationDto getByInspectionRequestId(Long inspectionRequestId);

    List<InspectionInitiationDto> getAll();

    List<InspectionInitiationDto> getByStatus(String status);

    void deleteById(Long id);
}

