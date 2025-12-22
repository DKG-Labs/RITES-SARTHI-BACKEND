package com.sarthi.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarthi.constant.AppConstant;
import com.sarthi.dto.InspectionInitiationDto;
import com.sarthi.entity.InspectionInitiation;
import com.sarthi.exception.BusinessException;
import com.sarthi.exception.ErrorDetails;
import com.sarthi.exception.InvalidInputException;
import com.sarthi.repository.InspectionInitiationRepository;
import com.sarthi.service.InspectionInitiationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for inspection initiation operations.
 */
@Service
@Transactional
public class InspectionInitiationServiceImpl implements InspectionInitiationService {

    private static final Logger logger = LoggerFactory.getLogger(InspectionInitiationServiceImpl.class);

    @Autowired
    private InspectionInitiationRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public InspectionInitiationDto createInitiation(InspectionInitiationDto dto) {
        logger.info("Creating inspection initiation for call: {}", dto.getCallNo());

        if (dto.getCallNo() == null || dto.getCallNo().isEmpty()) {
            throw new InvalidInputException(new ErrorDetails(
                AppConstant.INVALID_INPUT,
                AppConstant.ERROR_TYPE_CODE_VALIDATION,
                AppConstant.ERROR_TYPE_VALIDATION,
                "Call number is required"
            ));
        }

        // Check if already exists - update instead of creating new
        if (repository.existsByCallNo(dto.getCallNo())) {
            InspectionInitiation existing = repository.findByCallNo(dto.getCallNo())
                    .orElseThrow(() -> new BusinessException(new ErrorDetails(
                        AppConstant.NO_RECORD_FOUND,
                        AppConstant.ERROR_TYPE_CODE_DB,
                        AppConstant.ERROR_TYPE_ERROR,
                        "Record not found"
                    )));
            return updateExisting(existing, dto);
        }

        InspectionInitiation entity = mapToEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setInitiatedAt(LocalDateTime.now());

        InspectionInitiation saved = repository.save(entity);
        logger.info("Created inspection initiation with ID: {}", saved.getId());

        return mapToDto(saved);
    }

    @Override
    public InspectionInitiationDto updateInitiation(Long id, InspectionInitiationDto dto) {
        logger.info("Updating inspection initiation ID: {}", id);

        InspectionInitiation existing = repository.findById(id)
                .orElseThrow(() -> new BusinessException(new ErrorDetails(
                    AppConstant.NO_RECORD_FOUND,
                    AppConstant.ERROR_TYPE_CODE_DB,
                    AppConstant.ERROR_TYPE_ERROR,
                    "Initiation not found"
                )));

        return updateExisting(existing, dto);
    }

    private InspectionInitiationDto updateExisting(InspectionInitiation entity, InspectionInitiationDto dto) {
        entity.setShiftOfInspection(dto.getShiftOfInspection());
        entity.setDateOfInspection(dto.getDateOfInspection());
        entity.setOfferedQty(dto.getOfferedQty());
        entity.setCmApproval(dto.getCmApproval());
        entity.setSectionAVerified(dto.getSectionAVerified());
        entity.setSectionBVerified(dto.getSectionBVerified());
        entity.setSectionCVerified(dto.getSectionCVerified());
        entity.setSectionDVerified(dto.getSectionDVerified());
        entity.setMultipleLinesActive(dto.getMultipleLinesActive());
        entity.setProductType(dto.getProductType());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : "INITIATED");
        entity.setUpdatedAt(LocalDateTime.now());

        // Handle withheld/cancelled action fields
        if (dto.getActionType() != null) {
            entity.setActionType(dto.getActionType());
            entity.setActionReason(dto.getReason());
            entity.setActionRemarks(dto.getRemarks());
            entity.setActionDate(dto.getActionDate() != null ? dto.getActionDate() : LocalDateTime.now());
        }

        // Convert production lines to JSON
        if (dto.getProductionLines() != null) {
            try {
                entity.setProductionLinesJson(objectMapper.writeValueAsString(dto.getProductionLines()));
            } catch (Exception e) {
                logger.error("Error serializing production lines", e);
            }
        }

        InspectionInitiation saved = repository.save(entity);
        return mapToDto(saved);
    }

    @Override
    public InspectionInitiationDto getById(Long id) {
        return repository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new BusinessException(new ErrorDetails(
                    AppConstant.NO_RECORD_FOUND,
                    AppConstant.ERROR_TYPE_CODE_DB,
                    AppConstant.ERROR_TYPE_ERROR,
                    "Initiation not found"
                )));
    }

    @Override
    public InspectionInitiationDto getByCallNo(String callNo) {
        return repository.findByCallNo(callNo)
                .map(this::mapToDto)
                .orElse(null);
    }

    @Override
    public InspectionInitiationDto getByInspectionRequestId(Long inspectionRequestId) {
        return repository.findByInspectionRequestId(inspectionRequestId)
                .map(this::mapToDto)
                .orElse(null);
    }

    @Override
    public List<InspectionInitiationDto> getAll() {
        return repository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<InspectionInitiationDto> getByStatus(String status) {
        return repository.findByStatus(status).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(new ErrorDetails(
                AppConstant.NO_RECORD_FOUND,
                AppConstant.ERROR_TYPE_CODE_DB,
                AppConstant.ERROR_TYPE_ERROR,
                "Initiation not found"
            ));
        }
        repository.deleteById(id);
        logger.info("Deleted inspection initiation ID: {}", id);
    }

    private InspectionInitiation mapToEntity(InspectionInitiationDto dto) {
        InspectionInitiation entity = new InspectionInitiation();
        entity.setInspectionRequestId(dto.getInspectionRequestId());
        entity.setCallNo(dto.getCallNo());
        entity.setPoNo(dto.getPoNo());
        entity.setShiftOfInspection(dto.getShiftOfInspection());
        entity.setDateOfInspection(dto.getDateOfInspection());
        entity.setOfferedQty(dto.getOfferedQty());
        entity.setCmApproval(dto.getCmApproval() != null ? dto.getCmApproval() : false);
        entity.setSectionAVerified(dto.getSectionAVerified() != null ? dto.getSectionAVerified() : false);
        entity.setSectionBVerified(dto.getSectionBVerified() != null ? dto.getSectionBVerified() : false);
        entity.setSectionCVerified(dto.getSectionCVerified() != null ? dto.getSectionCVerified() : false);
        entity.setSectionDVerified(dto.getSectionDVerified() != null ? dto.getSectionDVerified() : false);
        entity.setMultipleLinesActive(dto.getMultipleLinesActive() != null ? dto.getMultipleLinesActive() : false);
        entity.setProductType(dto.getProductType());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : "INITIATED");
        entity.setInitiatedBy(dto.getInitiatedBy());

        // Set action fields for withheld/cancelled
        entity.setActionType(dto.getActionType());
        entity.setActionReason(dto.getReason());
        entity.setActionRemarks(dto.getRemarks());
        entity.setActionDate(dto.getActionDate());

        return entity;
    }

    private InspectionInitiationDto mapToDto(InspectionInitiation entity) {
        InspectionInitiationDto dto = new InspectionInitiationDto();
        dto.setId(entity.getId());
        dto.setInspectionRequestId(entity.getInspectionRequestId());
        dto.setCallNo(entity.getCallNo());
        dto.setPoNo(entity.getPoNo());
        dto.setShiftOfInspection(entity.getShiftOfInspection());
        dto.setDateOfInspection(entity.getDateOfInspection());
        dto.setOfferedQty(entity.getOfferedQty());
        dto.setCmApproval(entity.getCmApproval());
        dto.setSectionAVerified(entity.getSectionAVerified());
        dto.setSectionBVerified(entity.getSectionBVerified());
        dto.setSectionCVerified(entity.getSectionCVerified());
        dto.setSectionDVerified(entity.getSectionDVerified());
        dto.setMultipleLinesActive(entity.getMultipleLinesActive());
        dto.setProductType(entity.getProductType());
        dto.setStatus(entity.getStatus());
        dto.setInitiatedBy(entity.getInitiatedBy());
        dto.setInitiatedAt(entity.getInitiatedAt());

        // Map action fields
        dto.setActionType(entity.getActionType());
        dto.setReason(entity.getActionReason());
        dto.setRemarks(entity.getActionRemarks());
        dto.setActionDate(entity.getActionDate());

        return dto;
    }
}

