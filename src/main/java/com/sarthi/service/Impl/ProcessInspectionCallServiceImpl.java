package com.sarthi.service.Impl;

import com.sarthi.dto.IcDtos.InspectionCallRequestDto;
import com.sarthi.dto.IcDtos.ProcessInspectionDetailsRequestDto;
import com.sarthi.entity.processmaterial.ProcessInspectionDetails;
import com.sarthi.entity.processmaterial.ProcessRmIcMapping;
import com.sarthi.entity.rawmaterial.InspectionCall;
import com.sarthi.repository.processmaterial.ProcessInspectionDetailsRepository;
import com.sarthi.repository.processmaterial.ProcessRmIcMappingRepository;
import com.sarthi.repository.rawmaterial.InspectionCallRepository;
import com.sarthi.service.ProcessInspectionCallService;
import com.sarthi.util.IcNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ProcessInspectionCallServiceImpl implements ProcessInspectionCallService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessInspectionCallServiceImpl.class);

    private final InspectionCallRepository inspectionCallRepository;
    private final ProcessInspectionDetailsRepository processDetailsRepository;
    private final ProcessRmIcMappingRepository processMappingRepository;
    private final IcNumberGenerator icNumberGenerator;

    @Autowired
    public ProcessInspectionCallServiceImpl(
            InspectionCallRepository inspectionCallRepository,
            ProcessInspectionDetailsRepository processDetailsRepository,
            ProcessRmIcMappingRepository processMappingRepository,
            IcNumberGenerator icNumberGenerator
    ) {
        this.inspectionCallRepository = inspectionCallRepository;
        this.processDetailsRepository = processDetailsRepository;
        this.processMappingRepository = processMappingRepository;
        this.icNumberGenerator = icNumberGenerator;
    }

    @Override
    public InspectionCall createProcessInspectionCall(
            InspectionCallRequestDto icRequest,
            List<ProcessInspectionDetailsRequestDto> processDetailsList
    ) {
        logger.info("========== CREATE PROCESS INSPECTION CALL ==========");
        logger.info("IC Request: {}", icRequest);
        logger.info("Process Details Count: {}", processDetailsList != null ? processDetailsList.size() : 0);

        // ================== 1. CREATE INSPECTION CALL ==================
        InspectionCall inspectionCall = new InspectionCall();

        // Generate IC Number with daily sequence reset
        LocalDate today = LocalDate.now();
        long dailySequence = inspectionCallRepository.countByTypeOfCallAndCreatedDate("Process", today) + 1;
        String icNumber = icNumberGenerator.generateIcNumber("Process", dailySequence);
        logger.info("Generated IC Number: {} (Daily Sequence: {})", icNumber, dailySequence);

        inspectionCall.setIcNumber(icNumber);
        inspectionCall.setPoNo(icRequest.getPoNo());
        inspectionCall.setPoSerialNo(icRequest.getPoSerialNo());
        inspectionCall.setTypeOfCall(icRequest.getTypeOfCall());
        inspectionCall.setStatus(icRequest.getStatus());

        inspectionCall.setDesiredInspectionDate(
                LocalDate.parse(icRequest.getDesiredInspectionDate())
        );

        if (icRequest.getActualInspectionDate() != null) {
            inspectionCall.setActualInspectionDate(
                    LocalDate.parse(icRequest.getActualInspectionDate())
            );
        }

        inspectionCall.setCompanyId(icRequest.getCompanyId());
        inspectionCall.setCompanyName(icRequest.getCompanyName());
        inspectionCall.setUnitId(icRequest.getUnitId());
        inspectionCall.setUnitName(icRequest.getUnitName());
        inspectionCall.setUnitAddress(icRequest.getUnitAddress());
        inspectionCall.setRemarks(icRequest.getRemarks());

        inspectionCall.setCreatedBy(icRequest.getCreatedBy());
        inspectionCall.setUpdatedBy(icRequest.getUpdatedBy());
        inspectionCall.setCreatedAt(LocalDateTime.now());
        inspectionCall.setUpdatedAt(LocalDateTime.now());

        // Save inspection call first to get the ID
        inspectionCall = inspectionCallRepository.save(inspectionCall);
        logger.info("✅ Inspection Call saved with ID: {}", inspectionCall.getId());

        // ================== 2. CREATE PROCESS INSPECTION DETAILS ==================
        if (processDetailsList != null && !processDetailsList.isEmpty()) {
            // For now, we'll use the first entry as the main process details
            // (Based on the schema, process_inspection_details has a UNIQUE constraint on ic_id)
            ProcessInspectionDetailsRequestDto firstDetail = processDetailsList.get(0);

            ProcessInspectionDetails processDetails = new ProcessInspectionDetails();
            processDetails.setInspectionCall(inspectionCall);

            // Get RM IC details from the request
            processDetails.setRmIcNumber(firstDetail.getRmIcNumber());

            // Fetch RM IC to get rmIcId (optional - may not exist yet)
            InspectionCall rmIc = null;
            if (firstDetail.getRmIcNumber() != null && !firstDetail.getRmIcNumber().isEmpty()) {
                rmIc = inspectionCallRepository.findByIcNumber(firstDetail.getRmIcNumber()).orElse(null);
                if (rmIc != null) {
                    processDetails.setRmIcId(rmIc.getId());
                    logger.info("✅ Found RM IC: {} with ID: {}", firstDetail.getRmIcNumber(), rmIc.getId());
                } else {
                    logger.warn("⚠️ RM IC not found: {}. Proceeding without RM IC reference.", firstDetail.getRmIcNumber());
                }
            }

            // Set lot and heat information
            processDetails.setLotNumber(firstDetail.getLotNumber());
            processDetails.setHeatNumber(firstDetail.getHeatNumber());
            processDetails.setManufacturer(firstDetail.getManufacturer());
            processDetails.setManufacturerHeat(firstDetail.getManufacturerHeat());

            // Set quantity information
            processDetails.setOfferedQty(firstDetail.getOfferedQty());
            processDetails.setTotalAcceptedQtyRm(firstDetail.getTotalAcceptedQtyRm());

            // Set place of inspection (from request or from RM IC if available)
            processDetails.setCompanyId(firstDetail.getCompanyId() != null ? firstDetail.getCompanyId() : (rmIc != null ? rmIc.getCompanyId() : null));
            processDetails.setCompanyName(firstDetail.getCompanyName() != null ? firstDetail.getCompanyName() : (rmIc != null ? rmIc.getCompanyName() : null));
            processDetails.setUnitId(firstDetail.getUnitId() != null ? firstDetail.getUnitId() : (rmIc != null ? rmIc.getUnitId() : null));
            processDetails.setUnitName(firstDetail.getUnitName() != null ? firstDetail.getUnitName() : (rmIc != null ? rmIc.getUnitName() : null));
            processDetails.setUnitAddress(firstDetail.getUnitAddress() != null ? firstDetail.getUnitAddress() : (rmIc != null ? rmIc.getUnitAddress() : null));

            // Save process inspection details
            processDetails = processDetailsRepository.save(processDetails);
            logger.info("✅ Process Inspection Details saved with ID: {}", processDetails.getId());

            // ================== 3. CREATE PROCESS RM IC MAPPING ==================
            // Create mapping entries for each lot-heat combination (only if RM IC exists)
            if (rmIc != null) {
                for (ProcessInspectionDetailsRequestDto detail : processDetailsList) {
                    ProcessRmIcMapping mapping = new ProcessRmIcMapping();

                    mapping.setProcessIcId(inspectionCall.getId());
                    mapping.setRmIcId(rmIc.getId());
                    mapping.setRmIcNumber(detail.getRmIcNumber());
                    mapping.setHeatNumber(detail.getHeatNumber());
                    mapping.setManufacturer(detail.getManufacturer());
                    mapping.setRmQtyAccepted(detail.getTotalAcceptedQtyRm() != null ? detail.getTotalAcceptedQtyRm() : 0);
                    mapping.setRmIcDate(rmIc.getDesiredInspectionDate());

                    processMappingRepository.save(mapping);
                    logger.info("✅ Process RM IC Mapping saved for heat: {}", detail.getHeatNumber());
                }
            } else {
                logger.info("⚠️ Skipping Process RM IC Mapping creation - RM IC not found");
            }
        }

        logger.info("========== PROCESS INSPECTION CALL CREATED SUCCESSFULLY ==========");
        return inspectionCall;
    }
}

