package com.sarthi.service.Impl;

import com.sarthi.constant.AppConstant;
import com.sarthi.dto.InspectionCallDetailsDto;
import com.sarthi.dto.MainPoInformationDto;
import com.sarthi.dto.SubPoDetailsDto;
import com.sarthi.entity.InspectionCallDetails;
import com.sarthi.entity.MainPoInformation;
import com.sarthi.entity.SubPoDetails;
import com.sarthi.exception.BusinessException;
import com.sarthi.exception.ErrorDetails;
import com.sarthi.exception.InvalidInputException;
import com.sarthi.repository.InspectionCallDetailsRepository;
import com.sarthi.repository.MainPoInformationRepository;
import com.sarthi.repository.SubPoDetailsRepository;
import com.sarthi.service.InspectionSectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for inspection section operations.
 * Handles Section A, B, C CRUD with proper validation and audit trail.
 */
@Service
@Transactional
public class InspectionSectionServiceImpl implements InspectionSectionService {

    private static final Logger logger = LoggerFactory.getLogger(InspectionSectionServiceImpl.class);

    @Autowired
    private MainPoInformationRepository mainPoRepo;

    @Autowired
    private InspectionCallDetailsRepository callDetailsRepo;

    @Autowired
    private SubPoDetailsRepository subPoRepo;

    /* ===== SECTION A: Main PO Information ===== */

    @Override
    public MainPoInformationDto saveMainPoInformation(MainPoInformationDto dto, String userId) {
        logger.info("Saving MainPoInformation for call: {} by user: {}", dto.getInspectionCallNo(), userId);
        validateCallNo(dto.getInspectionCallNo());

        if (mainPoRepo.existsByInspectionCallNo(dto.getInspectionCallNo())) {
            MainPoInformation existing = mainPoRepo.findByInspectionCallNo(dto.getInspectionCallNo())
                    .orElseThrow(() -> createNotFoundException("MainPoInformation"));
            return updateMainPoInformationEntity(existing, dto, userId);
        }

        MainPoInformation entity = mapToMainPoEntity(dto);
        entity.setCreatedBy(userId);
        MainPoInformation saved = mainPoRepo.save(entity);
        logger.info("Created MainPoInformation with ID: {}", saved.getId());
        return mapToMainPoDto(saved);
    }

    @Override
    public MainPoInformationDto updateMainPoInformation(Long id, MainPoInformationDto dto, String userId) {
        logger.info("Updating MainPoInformation ID: {} by user: {}", id, userId);
        MainPoInformation existing = mainPoRepo.findById(id)
                .orElseThrow(() -> createNotFoundException("MainPoInformation"));
        return updateMainPoInformationEntity(existing, dto, userId);
    }

    private MainPoInformationDto updateMainPoInformationEntity(MainPoInformation entity, MainPoInformationDto dto, String userId) {
        entity.setPoNo(dto.getPoNo());
        entity.setPoDate(dto.getPoDate());
        entity.setPoQty(dto.getPoQty());
        entity.setPlaceOfInspection(dto.getPlaceOfInspection());
        entity.setVendorName(dto.getVendorName());
        entity.setMaNo(dto.getMaNo());
        entity.setMaDate(dto.getMaDate());
        entity.setPurchasingAuthority(dto.getPurchasingAuthority());
        entity.setBillPayingOfficer(dto.getBillPayingOfficer());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : "pending");
        entity.setRejectionRemarks(dto.getRejectionRemarks());
        entity.setUpdatedBy(userId);
        MainPoInformation saved = mainPoRepo.save(entity);
        return mapToMainPoDto(saved);
    }

    @Override
    public MainPoInformationDto getMainPoInformationById(Long id) {
        return mainPoRepo.findById(id).map(this::mapToMainPoDto)
                .orElseThrow(() -> createNotFoundException("MainPoInformation"));
    }

    @Override
    public MainPoInformationDto getMainPoInformationByCallNo(String inspectionCallNo) {
        return mainPoRepo.findByInspectionCallNo(inspectionCallNo).map(this::mapToMainPoDto).orElse(null);
    }

    @Override
    public List<MainPoInformationDto> getAllMainPoInformation() {
        return mainPoRepo.findAll().stream().map(this::mapToMainPoDto).collect(Collectors.toList());
    }

    @Override
    public List<MainPoInformationDto> getMainPoInformationByStatus(String status) {
        return mainPoRepo.findByStatus(status).stream().map(this::mapToMainPoDto).collect(Collectors.toList());
    }

    @Override
    public void deleteMainPoInformation(Long id) {
        if (!mainPoRepo.existsById(id)) throw createNotFoundException("MainPoInformation");
        mainPoRepo.deleteById(id);
        logger.info("Deleted MainPoInformation ID: {}", id);
    }

    @Override
    public MainPoInformationDto approveMainPoInformation(String inspectionCallNo, String userId) {
        MainPoInformation entity = mainPoRepo.findByInspectionCallNo(inspectionCallNo)
                .orElseThrow(() -> createNotFoundException("MainPoInformation"));
        entity.setStatus("approved");
        entity.setUpdatedBy(userId);
        return mapToMainPoDto(mainPoRepo.save(entity));
    }

    @Override
    public MainPoInformationDto rejectMainPoInformation(String inspectionCallNo, String remarks, String userId) {
        MainPoInformation entity = mainPoRepo.findByInspectionCallNo(inspectionCallNo)
                .orElseThrow(() -> createNotFoundException("MainPoInformation"));
        entity.setStatus("rejected");
        entity.setRejectionRemarks(remarks);
        entity.setUpdatedBy(userId);
        return mapToMainPoDto(mainPoRepo.save(entity));
    }

    /* ===== SECTION B: Inspection Call Details ===== */

    @Override
    public InspectionCallDetailsDto saveInspectionCallDetails(InspectionCallDetailsDto dto, String userId) {
        logger.info("Saving InspectionCallDetails for call: {} by user: {}", dto.getInspectionCallNo(), userId);
        validateCallNo(dto.getInspectionCallNo());

        if (callDetailsRepo.existsByInspectionCallNo(dto.getInspectionCallNo())) {
            InspectionCallDetails existing = callDetailsRepo.findByInspectionCallNo(dto.getInspectionCallNo())
                    .orElseThrow(() -> createNotFoundException("InspectionCallDetails"));
            return updateCallDetailsEntity(existing, dto, userId);
        }

        InspectionCallDetails entity = mapToCallDetailsEntity(dto);
        entity.setCreatedBy(userId);

        if (dto.getMainPoId() != null) {
            MainPoInformation mainPo = mainPoRepo.findById(dto.getMainPoId())
                    .orElseThrow(() -> createNotFoundException("MainPoInformation"));
            entity.setMainPoInformation(mainPo);
        }

        InspectionCallDetails saved = callDetailsRepo.save(entity);
        logger.info("Created InspectionCallDetails with ID: {}", saved.getId());
        return mapToCallDetailsDto(saved);
    }

    @Override
    public InspectionCallDetailsDto updateInspectionCallDetails(Long id, InspectionCallDetailsDto dto, String userId) {
        logger.info("Updating InspectionCallDetails ID: {} by user: {}", id, userId);
        InspectionCallDetails existing = callDetailsRepo.findById(id)
                .orElseThrow(() -> createNotFoundException("InspectionCallDetails"));
        return updateCallDetailsEntity(existing, dto, userId);
    }

    private InspectionCallDetailsDto updateCallDetailsEntity(InspectionCallDetails entity, InspectionCallDetailsDto dto, String userId) {
        entity.setInspectionCallDate(dto.getInspectionCallDate());
        entity.setInspectionDesiredDate(dto.getInspectionDesiredDate());
        entity.setRlyPoNoSr(dto.getRlyPoNoSr());
        entity.setItemDesc(dto.getItemDesc());
        entity.setProductType(dto.getProductType());
        entity.setPoQty(dto.getPoQty());
        entity.setUnit(dto.getUnit());
        entity.setConsigneeRly(dto.getConsigneeRly());
        entity.setConsignee(dto.getConsignee());
        entity.setOrigDp(dto.getOrigDp());
        entity.setExtDp(dto.getExtDp());
        entity.setOrigDpStart(dto.getOrigDpStart());
        entity.setStageOfInspection(dto.getStageOfInspection());
        entity.setCallQty(dto.getCallQty());
        entity.setPlaceOfInspection(dto.getPlaceOfInspection());
        entity.setRmIcNumber(dto.getRmIcNumber());
        entity.setProcessIcNumber(dto.getProcessIcNumber());
        entity.setRemarks(dto.getRemarks());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : "pending");
        entity.setRejectionRemarks(dto.getRejectionRemarks());
        entity.setUpdatedBy(userId);
        InspectionCallDetails saved = callDetailsRepo.save(entity);
        return mapToCallDetailsDto(saved);
    }

    @Override
    public InspectionCallDetailsDto getInspectionCallDetailsById(Long id) {
        return callDetailsRepo.findById(id).map(this::mapToCallDetailsDto)
                .orElseThrow(() -> createNotFoundException("InspectionCallDetails"));
    }

    @Override
    public InspectionCallDetailsDto getInspectionCallDetailsByCallNo(String inspectionCallNo) {
        return callDetailsRepo.findByInspectionCallNo(inspectionCallNo).map(this::mapToCallDetailsDto).orElse(null);
    }

    @Override
    public InspectionCallDetailsDto getInspectionCallDetailsByCallNoWithSubPoDetails(String inspectionCallNo) {
        return callDetailsRepo.findByInspectionCallNoWithSubPoDetails(inspectionCallNo)
                .map(this::mapToCallDetailsDtoWithSubPo).orElse(null);
    }

    @Override
    public List<InspectionCallDetailsDto> getAllInspectionCallDetails() {
        return callDetailsRepo.findAll().stream().map(this::mapToCallDetailsDto).collect(Collectors.toList());
    }

    @Override
    public List<InspectionCallDetailsDto> getInspectionCallDetailsByStatus(String status) {
        return callDetailsRepo.findByStatus(status).stream().map(this::mapToCallDetailsDto).collect(Collectors.toList());
    }

    @Override
    public void deleteInspectionCallDetails(Long id) {
        if (!callDetailsRepo.existsById(id)) throw createNotFoundException("InspectionCallDetails");
        callDetailsRepo.deleteById(id);
        logger.info("Deleted InspectionCallDetails ID: {}", id);
    }

    @Override
    public InspectionCallDetailsDto approveInspectionCallDetails(String inspectionCallNo, String userId) {
        InspectionCallDetails entity = callDetailsRepo.findByInspectionCallNo(inspectionCallNo)
                .orElseThrow(() -> createNotFoundException("InspectionCallDetails"));
        entity.setStatus("approved");
        entity.setUpdatedBy(userId);
        return mapToCallDetailsDto(callDetailsRepo.save(entity));
    }

    @Override
    public InspectionCallDetailsDto rejectInspectionCallDetails(String inspectionCallNo, String remarks, String userId) {
        InspectionCallDetails entity = callDetailsRepo.findByInspectionCallNo(inspectionCallNo)
                .orElseThrow(() -> createNotFoundException("InspectionCallDetails"));
        entity.setStatus("rejected");
        entity.setRejectionRemarks(remarks);
        entity.setUpdatedBy(userId);
        return mapToCallDetailsDto(callDetailsRepo.save(entity));
    }

    /* ===== SECTION C: Sub PO Details ===== */

    @Override
    public SubPoDetailsDto saveSubPoDetails(SubPoDetailsDto dto, String userId) {
        logger.info("Saving SubPoDetails for call: {} by user: {}", dto.getInspectionCallNo(), userId);
        validateCallNo(dto.getInspectionCallNo());

        SubPoDetails entity = mapToSubPoEntity(dto);
        entity.setCreatedBy(userId);

        if (dto.getInspectionCallDetailsId() != null) {
            InspectionCallDetails callDetails = callDetailsRepo.findById(dto.getInspectionCallDetailsId())
                    .orElseThrow(() -> createNotFoundException("InspectionCallDetails"));
            entity.setInspectionCallDetails(callDetails);
        }

        SubPoDetails saved = subPoRepo.save(entity);
        logger.info("Created SubPoDetails with ID: {}", saved.getId());
        return mapToSubPoDto(saved);
    }

    @Override
    public SubPoDetailsDto updateSubPoDetails(Long id, SubPoDetailsDto dto, String userId) {
        logger.info("Updating SubPoDetails ID: {} by user: {}", id, userId);
        SubPoDetails existing = subPoRepo.findById(id)
                .orElseThrow(() -> createNotFoundException("SubPoDetails"));
        return updateSubPoEntity(existing, dto, userId);
    }

    private SubPoDetailsDto updateSubPoEntity(SubPoDetails entity, SubPoDetailsDto dto, String userId) {
        entity.setRawMaterialName(dto.getRawMaterialName());
        entity.setGradeSpec(dto.getGradeSpec());
        entity.setHeatNo(dto.getHeatNo());
        entity.setManufacturerSteelBars(dto.getManufacturerSteelBars());
        entity.setTcNo(dto.getTcNo());
        entity.setTcDate(dto.getTcDate());
        entity.setSubPoNo(dto.getSubPoNo());
        entity.setSubPoDate(dto.getSubPoDate());
        entity.setInvoiceNo(dto.getInvoiceNo());
        entity.setInvoiceDate(dto.getInvoiceDate());
        entity.setSubPoQty(dto.getSubPoQty());
        entity.setUnit(dto.getUnit());
        entity.setPlaceOfInspection(dto.getPlaceOfInspection());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : "pending");
        entity.setRejectionRemarks(dto.getRejectionRemarks());
        entity.setUpdatedBy(userId);
        SubPoDetails saved = subPoRepo.save(entity);
        return mapToSubPoDto(saved);
    }

    @Override
    public List<SubPoDetailsDto> saveSubPoDetailsBatch(List<SubPoDetailsDto> dtos, String userId) {
        logger.info("Saving batch of {} SubPoDetails by user: {}", dtos.size(), userId);
        List<SubPoDetailsDto> results = new ArrayList<>();
        for (SubPoDetailsDto dto : dtos) {
            results.add(saveSubPoDetails(dto, userId));
        }
        return results;
    }

    @Override
    public SubPoDetailsDto getSubPoDetailsById(Long id) {
        return subPoRepo.findById(id).map(this::mapToSubPoDto)
                .orElseThrow(() -> createNotFoundException("SubPoDetails"));
    }

    @Override
    public List<SubPoDetailsDto> getSubPoDetailsByCallNo(String inspectionCallNo) {
        return subPoRepo.findByInspectionCallNo(inspectionCallNo).stream()
                .map(this::mapToSubPoDto).collect(Collectors.toList());
    }

    @Override
    public List<SubPoDetailsDto> getAllSubPoDetails() {
        return subPoRepo.findAll().stream().map(this::mapToSubPoDto).collect(Collectors.toList());
    }

    @Override
    public List<SubPoDetailsDto> getSubPoDetailsByStatus(String status) {
        return subPoRepo.findByStatus(status).stream().map(this::mapToSubPoDto).collect(Collectors.toList());
    }

    @Override
    public void deleteSubPoDetails(Long id) {
        if (!subPoRepo.existsById(id)) throw createNotFoundException("SubPoDetails");
        subPoRepo.deleteById(id);
        logger.info("Deleted SubPoDetails ID: {}", id);
    }

    @Override
    public void deleteSubPoDetailsByCallNo(String inspectionCallNo) {
        subPoRepo.deleteByInspectionCallNo(inspectionCallNo);
        logger.info("Deleted all SubPoDetails for call: {}", inspectionCallNo);
    }

    @Override
    public SubPoDetailsDto approveSubPoDetails(Long id, String userId) {
        SubPoDetails entity = subPoRepo.findById(id).orElseThrow(() -> createNotFoundException("SubPoDetails"));
        entity.setStatus("approved");
        entity.setUpdatedBy(userId);
        return mapToSubPoDto(subPoRepo.save(entity));
    }

    @Override
    public SubPoDetailsDto rejectSubPoDetails(Long id, String remarks, String userId) {
        SubPoDetails entity = subPoRepo.findById(id).orElseThrow(() -> createNotFoundException("SubPoDetails"));
        entity.setStatus("rejected");
        entity.setRejectionRemarks(remarks);
        entity.setUpdatedBy(userId);
        return mapToSubPoDto(subPoRepo.save(entity));
    }

    @Override
    public void approveAllSubPoDetailsByCallNo(String inspectionCallNo, String userId) {
        List<SubPoDetails> entities = subPoRepo.findByInspectionCallNo(inspectionCallNo);
        entities.forEach(e -> { e.setStatus("approved"); e.setUpdatedBy(userId); });
        subPoRepo.saveAll(entities);
        logger.info("Approved all SubPoDetails for call: {}", inspectionCallNo);
    }

    @Override
    public void rejectAllSubPoDetailsByCallNo(String inspectionCallNo, String remarks, String userId) {
        List<SubPoDetails> entities = subPoRepo.findByInspectionCallNo(inspectionCallNo);
        entities.forEach(e -> { e.setStatus("rejected"); e.setRejectionRemarks(remarks); e.setUpdatedBy(userId); });
        subPoRepo.saveAll(entities);
        logger.info("Rejected all SubPoDetails for call: {}", inspectionCallNo);
    }

    /* Helper methods */
    private void validateCallNo(String callNo) {
        if (callNo == null || callNo.trim().isEmpty()) {
            throw new InvalidInputException(new ErrorDetails(AppConstant.INVALID_INPUT, AppConstant.ERROR_TYPE_CODE_VALIDATION, AppConstant.ERROR_TYPE_VALIDATION, "Inspection call number is required"));
        }
    }

    private BusinessException createNotFoundException(String entity) {
        return new BusinessException(new ErrorDetails(AppConstant.NO_RECORD_FOUND, AppConstant.ERROR_TYPE_CODE_DB, AppConstant.ERROR_TYPE_ERROR, entity + " not found"));
    }

    private MainPoInformation mapToMainPoEntity(MainPoInformationDto dto) {
        MainPoInformation e = new MainPoInformation();
        e.setInspectionCallNo(dto.getInspectionCallNo());
        e.setPoNo(dto.getPoNo());
        e.setPoDate(dto.getPoDate());
        e.setPoQty(dto.getPoQty());
        e.setPlaceOfInspection(dto.getPlaceOfInspection());
        e.setVendorName(dto.getVendorName());
        e.setMaNo(dto.getMaNo());
        e.setMaDate(dto.getMaDate());
        e.setPurchasingAuthority(dto.getPurchasingAuthority());
        e.setBillPayingOfficer(dto.getBillPayingOfficer());
        e.setStatus(dto.getStatus() != null ? dto.getStatus() : "pending");
        return e;
    }

    private MainPoInformationDto mapToMainPoDto(MainPoInformation e) {
        MainPoInformationDto d = new MainPoInformationDto();
        d.setId(e.getId());
        d.setInspectionCallNo(e.getInspectionCallNo());
        d.setPoNo(e.getPoNo());
        d.setPoDate(e.getPoDate());
        d.setPoQty(e.getPoQty());
        d.setPlaceOfInspection(e.getPlaceOfInspection());
        d.setVendorName(e.getVendorName());
        d.setMaNo(e.getMaNo());
        d.setMaDate(e.getMaDate());
        d.setPurchasingAuthority(e.getPurchasingAuthority());
        d.setBillPayingOfficer(e.getBillPayingOfficer());
        d.setStatus(e.getStatus());
        d.setRejectionRemarks(e.getRejectionRemarks());
        d.setCreatedBy(e.getCreatedBy());
        d.setCreatedDate(e.getCreatedDate());
        d.setUpdatedBy(e.getUpdatedBy());
        d.setUpdatedDate(e.getUpdatedDate());
        return d;
    }

    /* Section B mapping methods */
    private InspectionCallDetails mapToCallDetailsEntity(InspectionCallDetailsDto dto) {
        InspectionCallDetails e = new InspectionCallDetails();
        e.setInspectionCallNo(dto.getInspectionCallNo());
        e.setInspectionCallDate(dto.getInspectionCallDate());
        e.setInspectionDesiredDate(dto.getInspectionDesiredDate());
        e.setRlyPoNoSr(dto.getRlyPoNoSr());
        e.setItemDesc(dto.getItemDesc());
        e.setProductType(dto.getProductType());
        e.setTypeOfErc(dto.getTypeOfErc()); // Type of ERC: MK-III, MK-V, etc.
        e.setPoQty(dto.getPoQty());
        e.setUnit(dto.getUnit());
        e.setConsigneeRly(dto.getConsigneeRly());
        e.setConsignee(dto.getConsignee());
        e.setOrigDp(dto.getOrigDp());
        e.setExtDp(dto.getExtDp());
        e.setOrigDpStart(dto.getOrigDpStart());
        e.setStageOfInspection(dto.getStageOfInspection());
        e.setCallQty(dto.getCallQty());
        e.setPlaceOfInspection(dto.getPlaceOfInspection());
        e.setRmIcNumber(dto.getRmIcNumber());
        e.setProcessIcNumber(dto.getProcessIcNumber());
        e.setRemarks(dto.getRemarks());
        e.setStatus(dto.getStatus() != null ? dto.getStatus() : "pending");
        return e;
    }

    private InspectionCallDetailsDto mapToCallDetailsDto(InspectionCallDetails e) {
        InspectionCallDetailsDto d = new InspectionCallDetailsDto();
        d.setId(e.getId());
        d.setInspectionCallNo(e.getInspectionCallNo());
        d.setInspectionCallDate(e.getInspectionCallDate());
        d.setInspectionDesiredDate(e.getInspectionDesiredDate());
        d.setRlyPoNoSr(e.getRlyPoNoSr());
        d.setItemDesc(e.getItemDesc());
        d.setProductType(e.getProductType());
        d.setTypeOfErc(e.getTypeOfErc()); // Type of ERC: MK-III, MK-V, etc.
        d.setPoQty(e.getPoQty());
        d.setUnit(e.getUnit());
        d.setConsigneeRly(e.getConsigneeRly());
        d.setConsignee(e.getConsignee());
        d.setOrigDp(e.getOrigDp());
        d.setExtDp(e.getExtDp());
        d.setOrigDpStart(e.getOrigDpStart());
        d.setStageOfInspection(e.getStageOfInspection());
        d.setCallQty(e.getCallQty());
        d.setPlaceOfInspection(e.getPlaceOfInspection());
        d.setRmIcNumber(e.getRmIcNumber());
        d.setProcessIcNumber(e.getProcessIcNumber());
        d.setRemarks(e.getRemarks());
        d.setStatus(e.getStatus());
        d.setRejectionRemarks(e.getRejectionRemarks());
        if (e.getMainPoInformation() != null) {
            d.setMainPoId(e.getMainPoInformation().getId());
        }
        d.setCreatedBy(e.getCreatedBy());
        d.setCreatedDate(e.getCreatedDate());
        d.setUpdatedBy(e.getUpdatedBy());
        d.setUpdatedDate(e.getUpdatedDate());
        return d;
    }

    private InspectionCallDetailsDto mapToCallDetailsDtoWithSubPo(InspectionCallDetails e) {
        InspectionCallDetailsDto d = mapToCallDetailsDto(e);
        if (e.getSubPoDetails() != null) {
            d.setSubPoDetails(e.getSubPoDetails().stream()
                    .map(this::mapToSubPoDto).collect(Collectors.toList()));
        }
        return d;
    }

    /* Section C mapping methods */
    private SubPoDetails mapToSubPoEntity(SubPoDetailsDto dto) {
        SubPoDetails e = new SubPoDetails();
        e.setInspectionCallNo(dto.getInspectionCallNo());
        e.setRawMaterialName(dto.getRawMaterialName());
        e.setGradeSpec(dto.getGradeSpec());
        e.setHeatNo(dto.getHeatNo());
        e.setManufacturerSteelBars(dto.getManufacturerSteelBars());
        e.setTcNo(dto.getTcNo());
        e.setTcDate(dto.getTcDate());
        e.setSubPoNo(dto.getSubPoNo());
        e.setSubPoDate(dto.getSubPoDate());
        e.setInvoiceNo(dto.getInvoiceNo());
        e.setInvoiceDate(dto.getInvoiceDate());
        e.setSubPoQty(dto.getSubPoQty());
        e.setUnit(dto.getUnit());
        e.setPlaceOfInspection(dto.getPlaceOfInspection());
        e.setStatus(dto.getStatus() != null ? dto.getStatus() : "pending");
        return e;
    }

    private SubPoDetailsDto mapToSubPoDto(SubPoDetails e) {
        SubPoDetailsDto d = new SubPoDetailsDto();
        d.setId(e.getId());
        d.setInspectionCallNo(e.getInspectionCallNo());
        d.setRawMaterialName(e.getRawMaterialName());
        d.setGradeSpec(e.getGradeSpec());
        d.setHeatNo(e.getHeatNo());
        d.setManufacturerSteelBars(e.getManufacturerSteelBars());
        d.setTcNo(e.getTcNo());
        d.setTcDate(e.getTcDate());
        d.setSubPoNo(e.getSubPoNo());
        d.setSubPoDate(e.getSubPoDate());
        d.setInvoiceNo(e.getInvoiceNo());
        d.setInvoiceDate(e.getInvoiceDate());
        d.setSubPoQty(e.getSubPoQty());
        d.setUnit(e.getUnit());
        d.setPlaceOfInspection(e.getPlaceOfInspection());
        d.setStatus(e.getStatus());
        d.setRejectionRemarks(e.getRejectionRemarks());
        if (e.getInspectionCallDetails() != null) {
            d.setInspectionCallDetailsId(e.getInspectionCallDetails().getId());
        }
        d.setCreatedBy(e.getCreatedBy());
        d.setCreatedDate(e.getCreatedDate());
        d.setUpdatedBy(e.getUpdatedBy());
        d.setUpdatedDate(e.getUpdatedDate());
        return d;
    }
}

