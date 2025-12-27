package com.sarthi.dto.IcDtos;

/**
 * Wrapper DTO for creating an inspection call with RM details.
 * Combines InspectionCallRequestDto and RmInspectionDetailsRequestDto.
 */
public class CreateInspectionCallRequestDto {

    private InspectionCallRequestDto inspectionCall;
    private RmInspectionDetailsRequestDto rmInspectionDetails;

    public InspectionCallRequestDto getInspectionCall() {
        return inspectionCall;
    }

    public void setInspectionCall(InspectionCallRequestDto inspectionCall) {
        this.inspectionCall = inspectionCall;
    }

    public RmInspectionDetailsRequestDto getRmInspectionDetails() {
        return rmInspectionDetails;
    }

    public void setRmInspectionDetails(RmInspectionDetailsRequestDto rmInspectionDetails) {
        this.rmInspectionDetails = rmInspectionDetails;
    }

    @Override
    public String toString() {
        return "CreateInspectionCallRequestDto{" +
                "inspectionCall=" + inspectionCall +
                ", rmInspectionDetails=" + rmInspectionDetails +
                '}';
    }
}

