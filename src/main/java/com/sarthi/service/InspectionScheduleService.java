package com.sarthi.service;

import com.sarthi.dto.InspectionScheduleDto;

import java.util.List;

/**
 * Service interface for Inspection Schedule operations.
 */
public interface InspectionScheduleService {

    /**
     * Schedule an inspection call.
     */
    InspectionScheduleDto scheduleInspection(InspectionScheduleDto scheduleDto);

    /**
     * Reschedule an existing inspection call.
     */
    InspectionScheduleDto rescheduleInspection(InspectionScheduleDto scheduleDto);

    /**
     * Get schedule by call number.
     */
    InspectionScheduleDto getScheduleByCallNo(String callNo);

    /**
     * Get all schedules.
     */
    List<InspectionScheduleDto> getAllSchedules();

    /**
     * Get schedule count for a specific date.
     */
    long getScheduleCountByDate(String date);

    /**
     * Delete schedule by call number.
     */
    void deleteScheduleByCallNo(String callNo);
}

