package com.sarthi.controller;

import com.sarthi.dto.InspectionInitiationDto;
import com.sarthi.dto.WorkflowDtos.TransitionActionReqDto;
import com.sarthi.service.InspectionInitiationService;
import com.sarthi.service.WorkflowService;
import com.sarthi.util.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for inspection initiation operations.
 * JWT protected endpoints for IE to save shift, date, and verification data.
 */
@RestController
@RequestMapping("/api/inspection-initiation")
@CrossOrigin(origins = "*")
public class InspectionInitiationController {

    private static final Logger logger = LoggerFactory.getLogger(InspectionInitiationController.class);

    @Autowired
    private InspectionInitiationService service;
    @Autowired
    private WorkflowService workflowService;

    /**
     * Create or update inspection initiation
     */
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody InspectionInitiationDto dto) {
        logger.info("POST /api/inspection-initiation - Creating initiation for call: {}", dto.getCallNo());
        InspectionInitiationDto created = service.createInitiation(dto);

        String callNumber = dto.getCallNo();
        String productType = null;

        if (callNumber.startsWith("EP")) {
            productType = "Process";
        }


        if(productType.equalsIgnoreCase("Process")){        TransitionActionReqDto req = new TransitionActionReqDto();

        req.setWorkflowTransitionId(dto.getWorkflowTransitionId());
        req.setRequestId(dto.getCallNo());
        req.setAction("ENTRY_INSPECTION_RESULTS");
        req.setActionBy(dto.getActionBy());
        req.setRemarks(dto.getRemarks());

        System.out.print(req +""+ dto.getWorkflowTransitionId());
        workflowService.performTransitionAction(req);

        }

        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(created), HttpStatus.CREATED);
    }

    /**
     * Update existing initiation
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody InspectionInitiationDto dto) {
        logger.info("PUT /api/inspection-initiation/{} - Updating initiation", id);
        InspectionInitiationDto updated = service.updateInitiation(id, dto);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(updated), HttpStatus.OK);
    }

    /**
     * Get initiation by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        logger.info("GET /api/inspection-initiation/{}", id);
        InspectionInitiationDto dto = service.getById(id);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(dto), HttpStatus.OK);
    }

    /**
     * Get initiation by call number
     */
    @GetMapping("/call/{callNo}")
    public ResponseEntity<Object> getByCallNo(@PathVariable String callNo) {
        logger.info("GET /api/inspection-initiation/call/{}", callNo);
        InspectionInitiationDto dto = service.getByCallNo(callNo);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(dto), HttpStatus.OK);
    }

    /**
     * Get all initiations
     */
    @GetMapping
    public ResponseEntity<Object> getAll() {
        logger.info("GET /api/inspection-initiation");
        List<InspectionInitiationDto> list = service.getAll();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(list), HttpStatus.OK);
    }

    /**
     * Get initiations by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<Object> getByStatus(@PathVariable String status) {
        logger.info("GET /api/inspection-initiation/status/{}", status);
        List<InspectionInitiationDto> list = service.getByStatus(status);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(list), HttpStatus.OK);
    }

    /**
     * Delete initiation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        logger.info("DELETE /api/inspection-initiation/{}", id);
        service.deleteById(id);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Deleted successfully"), HttpStatus.OK);
    }
}

