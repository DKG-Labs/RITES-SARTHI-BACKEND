package com.sarthi.dto.reports;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class PoInspection3rdLevelCallStatusDto {

    private Integer slNo;

    // Rly - PO - Sr No
    private String rlyPoSrNo;

    // Inspection Call
    private String inspectionCallNumber;

    // Stage
    private String stageOfInspection;

    // Dates
    private LocalDate desiredDateOfInspection;
    private Date inspectionStartDate;
    private Date inspectionCompletionDate;

    // Visits / Mandays
    private Integer noOfVisitsOrMandays;

    // Quantity
    private Double offeredOrManufacturedQty;
    private Double acceptedQuantity;
    private Double balanceQty;

    // Rejection
    private Double rejectionPercentage;
    private String mainReasonForRejection;

    // IC
    private String icNumber;


    //Constructor for JPQL Projection
    public PoInspection3rdLevelCallStatusDto(
            Integer slNo,
            String rlyPoSrNo,
            String inspectionCallNumber,
            String stageOfInspection,
            LocalDate desiredDateOfInspection,
            Date inspectionStartDate,
            Date inspectionCompletionDate,
            Integer noOfVisitsOrMandays,
            Double offeredOrManufacturedQty,
            Double acceptedQuantity,
            Double balanceQty,
            Double rejectionPercentage,
            String mainReasonForRejection,
            String icNumber
    ) {
        this.slNo = slNo;
        this.rlyPoSrNo = rlyPoSrNo;
        this.inspectionCallNumber = inspectionCallNumber;
        this.stageOfInspection = stageOfInspection;
        this.desiredDateOfInspection = desiredDateOfInspection;
        this.inspectionStartDate = inspectionStartDate;
        this.inspectionCompletionDate = inspectionCompletionDate;
        this.noOfVisitsOrMandays = noOfVisitsOrMandays;
        this.offeredOrManufacturedQty = offeredOrManufacturedQty;
        this.acceptedQuantity = acceptedQuantity;
        this.balanceQty = balanceQty;
        this.rejectionPercentage = rejectionPercentage;
        this.mainReasonForRejection = mainReasonForRejection;
        this.icNumber = icNumber;
    }


}
