package com.sarthi.controller.finalmaterial;

import com.sarthi.dto.IcDtos.CreateFinalInspectionCallRequestDto;
import com.sarthi.entity.rawmaterial.InspectionCall;
import com.sarthi.util.ResponseBuilder;
import com.sarthi.exception.ErrorDetails;
import com.sarthi.constant.AppConstant;
import com.sarthi.service.FinalInspectionCallService;
import com.sarthi.service.WorkflowService;
import com.sarthi.entity.rawmaterial.InspectionCall;
import com.sarthi.entity.finalmaterial.FinalInspectionDetails;
import com.sarthi.entity.finalmaterial.FinalInspectionLotDetails;
import com.sarthi.entity.finalmaterial.FinalProcessIcMapping;
import com.sarthi.repository.rawmaterial.InspectionCallRepository;
import com.sarthi.repository.finalmaterial.FinalInspectionDetailsRepository;
import com.sarthi.repository.finalmaterial.FinalInspectionLotDetailsRepository;
import com.sarthi.repository.finalmaterial.FinalProcessIcMappingRepository;
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

    @Autowired
    private InspectionCallRepository inspectionCallRepository;

    @Autowired
    private FinalInspectionDetailsRepository finalInspectionDetailsRepository;

    @Autowired
    private FinalInspectionLotDetailsRepository finalInspectionLotDetailsRepository;

    @Autowired
    private FinalProcessIcMappingRepository finalProcessIcMappingRepository;

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
     * Get final inspection initiation data by call number
     * GET /api/final-material/inspection/{callNo}
     */
    @GetMapping("/inspection/{callNo}")
    @Operation(summary = "Get Final inspection initiation data", description = "Returns inspection call, final details, lot details and process mappings for a call number")
    public ResponseEntity<Object> getFinalInspectionByCallNo(@PathVariable String callNo) {
        logger.info("GET /api/final-material/inspection/{} - Fetching final initiation data", callNo);

        InspectionCall ic = inspectionCallRepository.findByIcNumber(callNo)
                .orElse(null);

        if (ic == null) {
            logger.warn("Final initiation: Inspection call not found for callNo: {}", callNo);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Inspection call not found for callNo: " + callNo
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.NOT_FOUND);
        }

        FinalInspectionDetails finalDetails = finalInspectionDetailsRepository.findByIcId(ic.getId().longValue())
                .orElse(null);

        java.util.List<FinalInspectionLotDetails> lotDetails = new java.util.ArrayList<>();
        java.util.List<FinalProcessIcMapping> mappings = new java.util.ArrayList<>();

        if (finalDetails != null) {
            lotDetails = finalInspectionLotDetailsRepository.findByFinalDetailId(finalDetails.getId());
        }

        mappings = finalProcessIcMappingRepository.findByFinalIcId(ic.getId().longValue());

        java.util.Map<String, Object> resp = new java.util.HashMap<>();
        resp.put("inspectionCall", ic);
        resp.put("finalInspectionDetails", finalDetails);
        resp.put("finalLotDetails", lotDetails);
        resp.put("finalProcessMappings", mappings);

        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(resp), HttpStatus.OK);
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

