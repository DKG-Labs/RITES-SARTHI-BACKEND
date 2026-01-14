package com.sarthi.controller;

import com.sarthi.dto.InspectionCallDetailsDto;
import com.sarthi.dto.MainPoInformationDto;
import com.sarthi.dto.SubPoDetailsDto;
import com.sarthi.service.InspectionSectionService;
import com.sarthi.util.ResponseBuilder;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * REST Controller for inspection section operations.
 * JWT protected endpoints for Section A, B, C CRUD operations.
 */
@RestController
@RequestMapping("/api/inspection-sections")
@CrossOrigin(origins = "*")
public class InspectionSectionController {

    private static final Logger logger = LoggerFactory.getLogger(InspectionSectionController.class);

    @Autowired
    private InspectionSectionService service;

    /* ===== SECTION A: Main PO Information ===== */

    @PostMapping("/section-a")
    public ResponseEntity<Object> saveSectionA(@Valid @RequestBody MainPoInformationDto dto, Principal principal) {
        // Use createdBy from DTO if provided, otherwise fallback to principal
        String userId = dto.getCreatedBy() != null ? dto.getCreatedBy() :
                        (principal != null ? principal.getName() : "system");
        logger.info("POST /api/inspection-sections/section-a - Saving for call: {} by user: {}", dto.getInspectionCallNo(), userId);
        MainPoInformationDto saved = service.saveMainPoInformation(dto, userId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.CREATED);
    }

    @PutMapping("/section-a/{id}")
    public ResponseEntity<Object> updateSectionA(@PathVariable Long id, @Valid @RequestBody MainPoInformationDto dto, Principal principal) {
        String userId = principal != null ? principal.getName() : "system";
        logger.info("PUT /api/inspection-sections/section-a/{} by user: {}", id, userId);
        MainPoInformationDto updated = service.updateMainPoInformation(id, dto, userId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(updated), HttpStatus.OK);
    }

    @GetMapping("/section-a/{id}")
    public ResponseEntity<Object> getSectionAById(@PathVariable Long id) {
        logger.info("GET /api/inspection-sections/section-a/{}", id);
        MainPoInformationDto dto = service.getMainPoInformationById(id);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(dto), HttpStatus.OK);
    }

    @GetMapping("/section-a/call/{callNo}")
    public ResponseEntity<Object> getSectionAByCallNo(@PathVariable String callNo) {
        logger.info("GET /api/inspection-sections/section-a/call/{}", callNo);
        MainPoInformationDto dto = service.getMainPoInformationByCallNo(callNo);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(dto), HttpStatus.OK);
    }

    @GetMapping("/section-a")
    public ResponseEntity<Object> getAllSectionA() {
        logger.info("GET /api/inspection-sections/section-a");
        List<MainPoInformationDto> list = service.getAllMainPoInformation();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(list), HttpStatus.OK);
    }

    @GetMapping("/section-a/status/{status}")
    public ResponseEntity<Object> getSectionAByStatus(@PathVariable String status) {
        logger.info("GET /api/inspection-sections/section-a/status/{}", status);
        List<MainPoInformationDto> list = service.getMainPoInformationByStatus(status);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(list), HttpStatus.OK);
    }

    @DeleteMapping("/section-a/{id}")
    public ResponseEntity<Object> deleteSectionA(@PathVariable Long id) {
        logger.info("DELETE /api/inspection-sections/section-a/{}", id);
        service.deleteMainPoInformation(id);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Deleted successfully"), HttpStatus.OK);
    }

    @PostMapping("/section-a/approve/{callNo}")
    public ResponseEntity<Object> approveSectionA(@PathVariable String callNo, Principal principal) {
        String userId = principal != null ? principal.getName() : "system";
        logger.info("POST /api/inspection-sections/section-a/approve/{} by user: {}", callNo, userId);
        MainPoInformationDto dto = service.approveMainPoInformation(callNo, userId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(dto), HttpStatus.OK);
    }

    @PostMapping("/section-a/reject/{callNo}")
    public ResponseEntity<Object> rejectSectionA(@PathVariable String callNo, @RequestParam String remarks, Principal principal) {
        String userId = principal != null ? principal.getName() : "system";
        logger.info("POST /api/inspection-sections/section-a/reject/{} by user: {}", callNo, userId);
        MainPoInformationDto dto = service.rejectMainPoInformation(callNo, remarks, userId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(dto), HttpStatus.OK);
    }

    /* ===== SECTION B: Inspection Call Details ===== */

    @PostMapping("/section-b")
    public ResponseEntity<Object> saveSectionB(@Valid @RequestBody InspectionCallDetailsDto dto, Principal principal) {
        // Use createdBy from DTO if provided, otherwise fallback to principal
        String userId = (dto.getCreatedBy() != null && !dto.getCreatedBy().isEmpty()) ? dto.getCreatedBy() :
                        (principal != null ? principal.getName() : "system");
        logger.info("POST /api/inspection-sections/section-b - Saving for call: {} by user: {}", dto.getInspectionCallNo(), userId);
        InspectionCallDetailsDto saved = service.saveInspectionCallDetails(dto, userId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.CREATED);
    }

    @PutMapping("/section-b/{id}")
    public ResponseEntity<Object> updateSectionB(@PathVariable Long id, @Valid @RequestBody InspectionCallDetailsDto dto, Principal principal) {
        String userId = principal != null ? principal.getName() : "system";
        logger.info("PUT /api/inspection-sections/section-b/{} by user: {}", id, userId);
        InspectionCallDetailsDto updated = service.updateInspectionCallDetails(id, dto, userId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(updated), HttpStatus.OK);
    }

    @GetMapping("/section-b/{id}")
    public ResponseEntity<Object> getSectionBById(@PathVariable Long id) {
        logger.info("GET /api/inspection-sections/section-b/{}", id);
        InspectionCallDetailsDto dto = service.getInspectionCallDetailsById(id);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(dto), HttpStatus.OK);
    }

    @GetMapping("/section-b/call/{callNo}")
    public ResponseEntity<Object> getSectionBByCallNo(@PathVariable String callNo) {
        logger.info("GET /api/inspection-sections/section-b/call/{}", callNo);
        InspectionCallDetailsDto dto = service.getInspectionCallDetailsByCallNo(callNo);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(dto), HttpStatus.OK);
    }

    @GetMapping("/section-b/call/{callNo}/with-sub-po")
    public ResponseEntity<Object> getSectionBByCallNoWithSubPo(@PathVariable String callNo) {
        logger.info("GET /api/inspection-sections/section-b/call/{}/with-sub-po", callNo);
        InspectionCallDetailsDto dto = service.getInspectionCallDetailsByCallNoWithSubPoDetails(callNo);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(dto), HttpStatus.OK);
    }

    @GetMapping("/section-b")
    public ResponseEntity<Object> getAllSectionB() {
        logger.info("GET /api/inspection-sections/section-b");
        List<InspectionCallDetailsDto> list = service.getAllInspectionCallDetails();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(list), HttpStatus.OK);
    }

    @GetMapping("/section-b/status/{status}")
    public ResponseEntity<Object> getSectionBByStatus(@PathVariable String status) {
        logger.info("GET /api/inspection-sections/section-b/status/{}", status);
        List<InspectionCallDetailsDto> list = service.getInspectionCallDetailsByStatus(status);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(list), HttpStatus.OK);
    }

    @DeleteMapping("/section-b/{id}")
    public ResponseEntity<Object> deleteSectionB(@PathVariable Long id) {
        logger.info("DELETE /api/inspection-sections/section-b/{}", id);
        service.deleteInspectionCallDetails(id);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Deleted successfully"), HttpStatus.OK);
    }

    @PostMapping("/section-b/approve/{callNo}")
    public ResponseEntity<Object> approveSectionB(@PathVariable String callNo, Principal principal) {
        String userId = principal != null ? principal.getName() : "system";
        logger.info("POST /api/inspection-sections/section-b/approve/{} by user: {}", callNo, userId);
        InspectionCallDetailsDto dto = service.approveInspectionCallDetails(callNo, userId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(dto), HttpStatus.OK);
    }

    @PostMapping("/section-b/reject/{callNo}")
    public ResponseEntity<Object> rejectSectionB(@PathVariable String callNo, @RequestParam String remarks, Principal principal) {
        String userId = principal != null ? principal.getName() : "system";
        logger.info("POST /api/inspection-sections/section-b/reject/{} by user: {}", callNo, userId);
        InspectionCallDetailsDto dto = service.rejectInspectionCallDetails(callNo, remarks, userId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(dto), HttpStatus.OK);
    }

    /* ===== SECTION C: Sub PO Details ===== */

    @PostMapping("/section-c")
    public ResponseEntity<Object> saveSectionC(@Valid @RequestBody SubPoDetailsDto dto, Principal principal) {
        // Use createdBy from DTO if provided, otherwise fallback to principal
        String userId = (dto.getCreatedBy() != null && !dto.getCreatedBy().isEmpty()) ? dto.getCreatedBy() :
                        (principal != null ? principal.getName() : "system");
        logger.info("POST /api/inspection-sections/section-c - Saving for call: {} by user: {}", dto.getInspectionCallNo(), userId);
        SubPoDetailsDto saved = service.saveSubPoDetails(dto, userId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.CREATED);
    }

    @PostMapping("/section-c/batch")
    public ResponseEntity<Object> saveSectionCBatch(@Valid @RequestBody List<SubPoDetailsDto> dtos, Principal principal) {
        // Use createdBy from first DTO if provided, otherwise fallback to principal
        String userId = (!dtos.isEmpty() && dtos.get(0).getCreatedBy() != null && !dtos.get(0).getCreatedBy().isEmpty())
                        ? dtos.get(0).getCreatedBy()
                        : (principal != null ? principal.getName() : "system");
        logger.info("POST /api/inspection-sections/section-c/batch - Saving {} records by user: {}", dtos.size(), userId);
        List<SubPoDetailsDto> saved = service.saveSubPoDetailsBatch(dtos, userId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.CREATED);
    }

    @PutMapping("/section-c/{id}")
    public ResponseEntity<Object> updateSectionC(@PathVariable Long id, @Valid @RequestBody SubPoDetailsDto dto, Principal principal) {
        String userId = principal != null ? principal.getName() : "system";
        logger.info("PUT /api/inspection-sections/section-c/{} by user: {}", id, userId);
        SubPoDetailsDto updated = service.updateSubPoDetails(id, dto, userId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(updated), HttpStatus.OK);
    }

    @GetMapping("/section-c/{id}")
    public ResponseEntity<Object> getSectionCById(@PathVariable Long id) {
        logger.info("GET /api/inspection-sections/section-c/{}", id);
        SubPoDetailsDto dto = service.getSubPoDetailsById(id);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(dto), HttpStatus.OK);
    }

    @GetMapping("/section-c/call/{callNo}")
    public ResponseEntity<Object> getSectionCByCallNo(@PathVariable String callNo) {
        logger.info("GET /api/inspection-sections/section-c/call/{}", callNo);
        List<SubPoDetailsDto> list = service.getSubPoDetailsByCallNo(callNo);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(list), HttpStatus.OK);
    }

    @GetMapping("/section-c")
    public ResponseEntity<Object> getAllSectionC() {
        logger.info("GET /api/inspection-sections/section-c");
        List<SubPoDetailsDto> list = service.getAllSubPoDetails();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(list), HttpStatus.OK);
    }

    @GetMapping("/section-c/status/{status}")
    public ResponseEntity<Object> getSectionCByStatus(@PathVariable String status) {
        logger.info("GET /api/inspection-sections/section-c/status/{}", status);
        List<SubPoDetailsDto> list = service.getSubPoDetailsByStatus(status);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(list), HttpStatus.OK);
    }

    @DeleteMapping("/section-c/{id}")
    public ResponseEntity<Object> deleteSectionC(@PathVariable Long id) {
        logger.info("DELETE /api/inspection-sections/section-c/{}", id);
        service.deleteSubPoDetails(id);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Deleted successfully"), HttpStatus.OK);
    }

    @DeleteMapping("/section-c/call/{callNo}")
    public ResponseEntity<Object> deleteSectionCByCallNo(@PathVariable String callNo) {
        logger.info("DELETE /api/inspection-sections/section-c/call/{}", callNo);
        service.deleteSubPoDetailsByCallNo(callNo);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Deleted successfully"), HttpStatus.OK);
    }

    @PostMapping("/section-c/approve/{id}")
    public ResponseEntity<Object> approveSectionC(@PathVariable Long id, Principal principal) {
        String userId = principal != null ? principal.getName() : "system";
        logger.info("POST /api/inspection-sections/section-c/approve/{} by user: {}", id, userId);
        SubPoDetailsDto dto = service.approveSubPoDetails(id, userId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(dto), HttpStatus.OK);
    }

    @PostMapping("/section-c/reject/{id}")
    public ResponseEntity<Object> rejectSectionC(@PathVariable Long id, @RequestParam String remarks, Principal principal) {
        String userId = principal != null ? principal.getName() : "system";
        logger.info("POST /api/inspection-sections/section-c/reject/{} by user: {}", id, userId);
        SubPoDetailsDto dto = service.rejectSubPoDetails(id, remarks, userId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(dto), HttpStatus.OK);
    }

    @PostMapping("/section-c/approve-all/{callNo}")
    public ResponseEntity<Object> approveAllSectionC(@PathVariable String callNo, Principal principal) {
        String userId = principal != null ? principal.getName() : "system";
        logger.info("POST /api/inspection-sections/section-c/approve-all/{} by user: {}", callNo, userId);
        service.approveAllSubPoDetailsByCallNo(callNo, userId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("All approved successfully"), HttpStatus.OK);
    }

    @PostMapping("/section-c/reject-all/{callNo}")
    public ResponseEntity<Object> rejectAllSectionC(@PathVariable String callNo, @RequestParam String remarks, Principal principal) {
        String userId = principal != null ? principal.getName() : "system";
        logger.info("POST /api/inspection-sections/section-c/reject-all/{} by user: {}", callNo, userId);
        service.rejectAllSubPoDetailsByCallNo(callNo, remarks, userId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("All rejected successfully"), HttpStatus.OK);
    }
}

