package com.sarthi.service.rawmaterial.impl;

import com.sarthi.constant.AppConstant;
import com.sarthi.dto.rawmaterial.*;
import com.sarthi.entity.rawmaterial.*;
import com.sarthi.exception.BusinessException;
import com.sarthi.exception.ErrorDetails;
import com.sarthi.repository.rawmaterial.*;
import com.sarthi.service.rawmaterial.RawMaterialInspectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for Raw Material Inspection operations.
 * Handles data retrieval from 4 RM tables with proper entity-to-DTO mapping.
 */
@Service
@Transactional(readOnly = true)
public class RawMaterialInspectionServiceImpl implements RawMaterialInspectionService {

    private static final Logger logger = LoggerFactory.getLogger(RawMaterialInspectionServiceImpl.class);

    private final InspectionCallRepository inspectionCallRepository;
    private final RmInspectionDetailsRepository rmDetailsRepository;
    private final RmHeatQuantityRepository heatQuantityRepository;

    @Autowired
    public RawMaterialInspectionServiceImpl(
            InspectionCallRepository inspectionCallRepository,
            RmInspectionDetailsRepository rmDetailsRepository,
            RmHeatQuantityRepository heatQuantityRepository) {
        this.inspectionCallRepository = inspectionCallRepository;
        this.rmDetailsRepository = rmDetailsRepository;
        this.heatQuantityRepository = heatQuantityRepository;
    }

    /* ==================== Inspection Call Operations ==================== */

    @Override
    public List<InspectionCallDto> getAllRawMaterialCalls() {
        logger.info("Fetching all Raw Material inspection calls");
        return inspectionCallRepository.findAllRawMaterialCallsWithDetails()
                .stream()
                .map(this::mapToCallDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<InspectionCallDto> getRawMaterialCallsByStatus(String status) {
        logger.info("Fetching Raw Material calls by status: {}", status);
        return inspectionCallRepository.findRawMaterialCallsByStatusWithDetails(status)
                .stream()
                .map(this::mapToCallDto)
                .collect(Collectors.toList());
    }

    @Override
    public InspectionCallDto getInspectionCallById(Integer id) {
        logger.info("Fetching inspection call by ID: {}", id);
        InspectionCall call = inspectionCallRepository.findById(id)
                .orElseThrow(() -> createNotFoundException("Inspection call not found with ID: " + id));
        return mapToCallDtoWithDetails(call);
    }

    @Override
    public InspectionCallDto getInspectionCallByIcNumber(String icNumber) {
        logger.info("Fetching inspection call by IC number: {}", icNumber);
        InspectionCall call = inspectionCallRepository.findByIcNumber(icNumber)
                .orElseThrow(() -> createNotFoundException("Inspection call not found: " + icNumber));
        return mapToCallDtoWithDetails(call);
    }

    /* ==================== RM Inspection Details Operations ==================== */

    @Override
    public RmInspectionDetailsDto getRmDetailsByCallId(Integer inspectionCallId) {
        logger.info("Fetching RM details for call ID: {}", inspectionCallId);
        return rmDetailsRepository.findByIcId(Long.valueOf(inspectionCallId))
                .map(this::mapToDetailsDto)
                .orElse(null);
    }

    /* ==================== Heat Quantity Operations ==================== */

    @Override
    public List<RmHeatQuantityDto> getHeatQuantitiesByRmDetailId(Integer rmDetailId) {
        logger.info("Fetching heat quantities for RM detail ID: {}", rmDetailId);
        return heatQuantityRepository.findByRmDetailId(rmDetailId)
                .stream()
                .map(this::mapToHeatDto)
                .collect(Collectors.toList());
    }

    @Override
    public RmHeatQuantityDto getHeatQuantityById(Integer heatId) {
        logger.info("Fetching heat quantity by ID: {}", heatId);
        RmHeatQuantity heat = heatQuantityRepository.findById(heatId)
                .orElseThrow(() -> createNotFoundException("Heat quantity not found with ID: " + heatId));
        return mapToHeatDto(heat);
    }

    /* ==================== Private Mapping Methods ==================== */

    /**
     * Maps InspectionCall entity to DTO (basic fields only)
     */
    private InspectionCallDto mapToCallDto(InspectionCall entity) {
        InspectionCallDto dto = InspectionCallDto.builder()
                .id(Math.toIntExact(entity.getId()))
                .icNumber(entity.getIcNumber())
                .poNo(entity.getPoNo())
                .poSerialNo(entity.getPoSerialNo())
                .typeOfCall(entity.getTypeOfCall())
                .status(entity.getStatus())
                .desiredInspectionDate(String.valueOf(entity.getDesiredInspectionDate()))
                .actualInspectionDate(String.valueOf(entity.getActualInspectionDate()))
                .placeOfInspection(entity.getPlaceOfInspection())
                .companyId(entity.getCompanyId())
                .companyName(entity.getCompanyName())
                .unitId(entity.getUnitId())
                .unitName(entity.getUnitName())
                .unitAddress(entity.getUnitAddress())
                .remarks(entity.getRemarks())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .createdAt(String.valueOf(entity.getCreatedAt()))
                .updatedAt(String.valueOf(entity.getUpdatedAt()))
                .build();

        // Add RM inspection details if available
        if (entity.getRmInspectionDetails() != null) {
            dto.setRmInspectionDetails(mapToDetailsDto(entity.getRmInspectionDetails()));
        }

        return dto;
    }

    /**
     * Maps InspectionCall entity to DTO with all related data
     */
    private InspectionCallDto mapToCallDtoWithDetails(InspectionCall entity) {
        InspectionCallDto dto = mapToCallDto(entity);

        // Add RM details and heat quantities if available
        if (entity.getRmInspectionDetails() != null) {
            RmInspectionDetails rmDetails = entity.getRmInspectionDetails();
            if (rmDetails.getHeatQuantities() != null) {
                List<RmHeatQuantityDto> heatDtos = rmDetails.getHeatQuantities()
                        .stream()
                        .map(this::mapToHeatDto)
                        .collect(Collectors.toList());
                dto.setRmHeatQuantities(heatDtos);
            }
        }

        return dto;
    }

    /**
     * Maps RmInspectionDetails entity to DTO
     */
    private RmInspectionDetailsDto mapToDetailsDto(RmInspectionDetails entity) {
        return RmInspectionDetailsDto.builder()
                .id(Math.toIntExact(entity.getId()))
                .icId(Math.toIntExact(entity.getInspectionCall() != null ? entity.getInspectionCall().getId() : null))
                .itemDescription(entity.getItemDescription())
                .itemQuantity(entity.getItemQuantity() != null ? entity.getItemQuantity().toString() : null)
                .consigneeZonalRailway(entity.getConsigneeZonalRailway())
                .heatNumbers(entity.getHeatNumbers())
                .tcNumber(entity.getTcNumber())
                .tcDate(String.valueOf(entity.getTcDate()))
                .tcQuantity(entity.getTcQuantity() != null ? entity.getTcQuantity().toString() : null)
                .manufacturer(entity.getManufacturer())
                .supplierName(entity.getSupplierName())
                .supplierAddress(entity.getSupplierAddress())
                .invoiceNumber(entity.getInvoiceNumber())
                .invoiceDate(String.valueOf(entity.getInvoiceDate()))
                .subPoNumber(entity.getSubPoNumber())
                .subPoDate(String.valueOf(entity.getSubPoDate()))
                .subPoQty(String.valueOf(entity.getSubPoQty()))
                .totalOfferedQtyMt(entity.getTotalOfferedQtyMt() != null ? entity.getTotalOfferedQtyMt().toString() : null)
                .offeredQtyErc(entity.getOfferedQtyErc() != null ? entity.getOfferedQtyErc().toString() : null)
                .unitOfMeasurement(entity.getUnitOfMeasurement())
                .rateOfMaterial(String.valueOf(entity.getRateOfMaterial()))
                .rateOfGst(String.valueOf(entity.getRateOfGst()))
                .baseValuePo(String.valueOf(entity.getBaseValuePo()))
                .totalPo(String.valueOf(entity.getTotalPo()))
                .createdAt(String.valueOf(entity.getCreatedAt()))
                .updatedAt(String.valueOf(entity.getUpdatedAt()))
                .build();
    }

    /**
     * Maps RmHeatQuantity entity to DTO
     */
    private RmHeatQuantityDto mapToHeatDto(RmHeatQuantity entity) {
        return RmHeatQuantityDto.builder()
                .id(Math.toIntExact(entity.getId()))
                .rmDetailId(Math.toIntExact(entity.getRmInspectionDetails() != null ? entity.getRmInspectionDetails().getId() : null))
                .heatNumber(entity.getHeatNumber())
                .manufacturer(entity.getManufacturer())
                //.offeredQty(entity.getOfferedQty())
                .tcNumber(entity.getTcNumber())
                .tcDate(String.valueOf(entity.getTcDate()))
               // .tcQuantity(entity.getTcQuantity())
                .qtyLeft(String.valueOf(entity.getQtyLeft()))
                .qtyAccepted(String.valueOf(entity.getQtyAccepted()))
                .qtyRejected(String.valueOf(entity.getQtyRejected()))
                .rejectionReason(entity.getRejectionReason())
                .createdAt(String.valueOf(entity.getCreatedAt()))
                .updatedAt(String.valueOf(entity.getUpdatedAt()))
                .build();
    }

    /**
     * Creates a BusinessException for resource not found
     */
    private BusinessException createNotFoundException(String message) {
        return new BusinessException(new ErrorDetails(
                AppConstant.ERROR_CODE_RESOURCE,
                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                AppConstant.ERROR_TYPE_RESOURCE,
                message
        ));
    }
}

