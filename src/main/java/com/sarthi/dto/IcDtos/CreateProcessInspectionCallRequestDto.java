package com.sarthi.dto.IcDtos;

import lombok.Data;

import java.util.List;

/**
 * Wrapper DTO for creating a Process inspection call.
 * Combines InspectionCallRequestDto and ProcessInspectionDetailsRequestDto.
 */
@Data
public class CreateProcessInspectionCallRequestDto {

    private InspectionCallRequestDto inspectionCall;
    private List<ProcessInspectionDetailsRequestDto> processInspectionDetails;

    @Override
    public String toString() {
        return "CreateProcessInspectionCallRequestDto{" +
                "inspectionCall=" + inspectionCall +
                ", processInspectionDetails=" + processInspectionDetails +
                '}';
    }
}

