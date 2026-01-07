package com.sarthi.controller.finalmaterial;

import com.sarthi.dto.IcDtos.CreateFinalInspectionCallRequestDto;
import com.sarthi.entity.rawmaterial.InspectionCall;
import com.sarthi.util.ResponseBuilder;
import com.sarthi.service.FinalInspectionCallService;
import com.sarthi.service.WorkflowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Final Inspection Call operations
 */
@RestController
@RequestMapping("/api/final-material")
@CrossOrigin(origins = "*")
@Tag(name = "Final Inspection Call", description = "APIs for Final Inspection Call management")
public class FinalInspectionCallController {

    private static final Logger logger = LoggerFactory.getLogger(FinalInspectionCallController.class);

    @Autowired
    private FinalInspectionCallService finalInspectionCallService;

    @Autowired
    private WorkflowService workflowService;

    /**
     * Create a new Final Inspection Call with lot details
     * POST /api/final-material/inspectionCall
     */
    @PostMapping("/inspectionCall")
    @Operation(summary = "Create Final inspection call", description = "Creates a new Final inspection call with lot details")
    public ResponseEntity<Object> createFinalInspectionCall(
            @RequestBody CreateFinalInspectionCallRequestDto request) {

        logger.info("========== CREATE FINAL INSPECTION CALL REQUEST ==========");
        logger.info("Request object: {}", request);
        logger.info("Inspection Call: {}", request.getInspectionCall());
        logger.info("Final Details: {}", request.getFinalInspectionDetails());
        logger.info("Lot Details: {}", request.getFinalLotDetails());
        logger.info("====================================================");

        // 1️⃣ Save Final inspection call
        InspectionCall ic = finalInspectionCallService.createFinalInspectionCall(
                request.getInspectionCall(),
                request.getFinalInspectionDetails(),
                request.getFinalLotDetails()
        );

        // 2️⃣ Trigger workflow ONLY on success
        String workflowName = "INSPECTION CALL";
        try {
            // Try to parse createdBy as Integer, if it fails, use a default value or skip workflow
            Integer createdByUserId = null;
            try {
                createdByUserId = Integer.valueOf(ic.getCreatedBy());
            } catch (NumberFormatException e) {
                logger.warn("⚠️ createdBy is not a valid integer: {}. Skipping workflow initiation.", ic.getCreatedBy());
            }

            if (createdByUserId != null) {
                workflowService.initiateWorkflow(
                        ic.getIcNumber(),
                        createdByUserId,
                        workflowName,
                        "560001"
                );
                logger.info("✅ Workflow initiated for IC: {}", ic.getIcNumber());
            }
        } catch (Exception workflowEx) {
            logger.error("⚠️ Workflow initiation failed but IC was created: {}", workflowEx.getMessage());
            // Don't fail the entire request if workflow fails
        }

        // 3️⃣ Return success response
        InspectionCallResponse responseData = new InspectionCallResponse(
                ic.getId(),
                ic.getIcNumber(),
                "Final Inspection Call created successfully"
        );

        logger.info("✅ Final Inspection Call created successfully: {}", ic.getIcNumber());
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(responseData), HttpStatus.CREATED);
    }

    /**
     * Inner class for response
     */
    private static class InspectionCallResponse {
        public Long inspection_call_id;
        public String ic_number;
        public String message;

        public InspectionCallResponse(Long id, String icNumber, String message) {
            this.inspection_call_id = id;
            this.ic_number = icNumber;
            this.message = message;
        }
    }
}

