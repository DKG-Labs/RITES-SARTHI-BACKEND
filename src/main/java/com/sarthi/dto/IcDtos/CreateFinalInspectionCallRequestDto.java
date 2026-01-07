package com.sarthi.dto.IcDtos;

import lombok.Data;

import java.util.List;

/**
 * Wrapper DTO for creating a Final inspection call.
 * Combines InspectionCallRequestDto, FinalInspectionDetailsRequestDto, and lot details.
 */
@Data
public class CreateFinalInspectionCallRequestDto {

    private InspectionCallRequestDto inspectionCall;
    private FinalInspectionDetailsRequestDto finalInspectionDetails;
    private List<FinalInspectionLotDetailsRequestDto> finalLotDetails;

    @Override
    public String toString() {
        return "CreateFinalInspectionCallRequestDto{" +
                "inspectionCall=" + inspectionCall +
                ", finalInspectionDetails=" + finalInspectionDetails +
                ", finalLotDetails=" + finalLotDetails +
                '}';
    }
}

