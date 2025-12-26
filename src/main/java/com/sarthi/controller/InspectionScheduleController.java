package com.sarthi.controller;

import com.sarthi.dto.InspectionScheduleDto;
import com.sarthi.service.InspectionScheduleService;
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
 * REST Controller for Inspection Schedule operations.
 * All endpoints are JWT protected (configured in SecurityConfig).
 */
@RestController
@RequestMapping("/api/inspection-schedule")
public class InspectionScheduleController {

    private static final Logger logger = LoggerFactory.getLogger(InspectionScheduleController.class);

    @Autowired
    private InspectionScheduleService scheduleService;
    @Autowired
    private WorkflowService workflowService;

    /**
     * Schedule an inspection call.
     * POST /api/inspection-schedule/schedule
     */
    @PostMapping("/schedule")
    public ResponseEntity<Object> scheduleInspection(@RequestBody InspectionScheduleDto scheduleDto) {
        logger.info("Received request to schedule inspection for call: {}", scheduleDto.getCallNo());
        InspectionScheduleDto result = scheduleService.scheduleInspection(scheduleDto);

        String workflowname = "IE INSPECTION";
        String pincode ="560001";
        workflowService.initiateWorkflow(result.getCallNo(), Integer.valueOf(result.getCreatedBy()), workflowname,pincode);

        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(result), HttpStatus.CREATED);
    }

    /**
     * Reschedule an existing inspection call.
     * PUT /api/inspection-schedule/reschedule
     */
    @PutMapping("/reschedule")
    public ResponseEntity<Object> rescheduleInspection(@RequestBody InspectionScheduleDto scheduleDto) {
        logger.info("Received request to reschedule inspection for call: {}", scheduleDto.getCallNo());
        InspectionScheduleDto result = scheduleService.rescheduleInspection(scheduleDto);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(result), HttpStatus.OK);
    }

    /**
     * Get schedule by call number.
     * GET /api/inspection-schedule/{callNo}
     */
    @GetMapping("/{callNo}")
    public ResponseEntity<Object> getScheduleByCallNo(@PathVariable String callNo) {
        logger.info("Fetching schedule for call: {}", callNo);
        InspectionScheduleDto result = scheduleService.getScheduleByCallNo(callNo);
        if (result == null) {
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(null), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(result), HttpStatus.OK);
    }

    /**
     * Get all schedules.
     * GET /api/inspection-schedule
     */
    @GetMapping
    public ResponseEntity<Object> getAllSchedules() {
        logger.info("Fetching all schedules");
        List<InspectionScheduleDto> results = scheduleService.getAllSchedules();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(results), HttpStatus.OK);
    }

    /**
     * Get schedule count for a specific date.
     * GET /api/inspection-schedule/count/{date}
     */
    @GetMapping("/count/{date}")
    public ResponseEntity<Object> getScheduleCountByDate(@PathVariable String date) {
        logger.info("Getting schedule count for date: {}", date);
        long count = scheduleService.getScheduleCountByDate(date);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(count), HttpStatus.OK);
    }

    /**
     * Delete schedule by call number.
     * DELETE /api/inspection-schedule/{callNo}
     */
    @DeleteMapping("/{callNo}")
    public ResponseEntity<Object> deleteSchedule(@PathVariable String callNo) {
        logger.info("Deleting schedule for call: {}", callNo);
        scheduleService.deleteScheduleByCallNo(callNo);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Schedule deleted successfully"), HttpStatus.OK);
    }
}

