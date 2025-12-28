package com.sarthi.service;

import com.sarthi.dto.InspectionCallDetailsDto;
import com.sarthi.dto.MainPoInformationDto;
import com.sarthi.dto.SubPoDetailsDto;

import java.util.List;

/**
 * Service interface for inspection section operations.
 * Handles Section A (MainPoInformation), Section B (InspectionCallDetails),
 * and Section C (SubPoDetails) CRUD operations.
 */
public interface InspectionSectionService {

    /* Section A: Main PO Information */
    MainPoInformationDto saveMainPoInformation(MainPoInformationDto dto, String userId);
    MainPoInformationDto updateMainPoInformation(Long id, MainPoInformationDto dto, String userId);
    MainPoInformationDto getMainPoInformationById(Long id);
    MainPoInformationDto getMainPoInformationByCallNo(String inspectionCallNo);
    List<MainPoInformationDto> getAllMainPoInformation();
    List<MainPoInformationDto> getMainPoInformationByStatus(String status);
    void deleteMainPoInformation(Long id);
    MainPoInformationDto approveMainPoInformation(String inspectionCallNo, String userId);
    MainPoInformationDto rejectMainPoInformation(String inspectionCallNo, String remarks, String userId);

    /* Section B: Inspection Call Details */
    InspectionCallDetailsDto saveInspectionCallDetails(InspectionCallDetailsDto dto, String userId);
    InspectionCallDetailsDto updateInspectionCallDetails(Long id, InspectionCallDetailsDto dto, String userId);
    InspectionCallDetailsDto getInspectionCallDetailsById(Long id);
    InspectionCallDetailsDto getInspectionCallDetailsByCallNo(String inspectionCallNo);
    InspectionCallDetailsDto getInspectionCallDetailsByCallNoWithSubPoDetails(String inspectionCallNo);
    List<InspectionCallDetailsDto> getAllInspectionCallDetails();
    List<InspectionCallDetailsDto> getInspectionCallDetailsByStatus(String status);
    void deleteInspectionCallDetails(Long id);
    InspectionCallDetailsDto approveInspectionCallDetails(String inspectionCallNo, String userId);
    InspectionCallDetailsDto rejectInspectionCallDetails(String inspectionCallNo, String remarks, String userId);

    /* Section C: Sub PO Details */
    SubPoDetailsDto saveSubPoDetails(SubPoDetailsDto dto, String userId);
    SubPoDetailsDto updateSubPoDetails(Long id, SubPoDetailsDto dto, String userId);
    List<SubPoDetailsDto> saveSubPoDetailsBatch(List<SubPoDetailsDto> dtos, String userId);
    SubPoDetailsDto getSubPoDetailsById(Long id);
    List<SubPoDetailsDto> getSubPoDetailsByCallNo(String inspectionCallNo);
    List<SubPoDetailsDto> getAllSubPoDetails();
    List<SubPoDetailsDto> getSubPoDetailsByStatus(String status);
    void deleteSubPoDetails(Long id);
    void deleteSubPoDetailsByCallNo(String inspectionCallNo);
    SubPoDetailsDto approveSubPoDetails(Long id, String userId);
    SubPoDetailsDto rejectSubPoDetails(Long id, String remarks, String userId);
    void approveAllSubPoDetailsByCallNo(String inspectionCallNo, String userId);
    void rejectAllSubPoDetailsByCallNo(String inspectionCallNo, String remarks, String userId);
}

