package com.sarthi.controller.processmaterial;

import com.sarthi.dto.processmaterial.ProcessInitiationDataDto;
import com.sarthi.util.ResponseBuilder;
import com.sarthi.service.processmaterial.ProcessInitiationDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Process Material Inspection Initiation Data
 * Provides endpoints to fetch Section A, B, and C data for inspection initiation page
 */
@RestController
@RequestMapping("/api/process-material/initiation")
@CrossOrigin(origins = "*")
@Slf4j
public class ProcessInitiationDataController {

    @Autowired
    private ProcessInitiationDataService service;

    /**
     * Get inspection initiation data for a Process material call
     * GET /api/process-material/initiation/call/{callNo}
     * 
     * @param callNo - Process Inspection Call Number
     * @return ProcessInitiationDataDto with PO info, IC details, and RM IC heat numbers
     */
    @GetMapping("/call/{callNo}")
    public ResponseEntity<Object> getInitiationDataByCallNo(@PathVariable String callNo) {
        log.info("GET /api/process-material/initiation/call/{} - Fetching initiation data", callNo);
        ProcessInitiationDataDto data = service.getInitiationDataByCallNo(callNo);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(data), HttpStatus.OK);
    }
}

