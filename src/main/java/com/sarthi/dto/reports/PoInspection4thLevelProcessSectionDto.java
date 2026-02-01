package com.sarthi.dto.reports;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PoInspection4thLevelProcessSectionDto {

    private LocalDate date;
    private String shift;
    private String rlyName;
    private String poSrNo;


    // ================= Shearing =================
    private Integer shearingProductionQty;
    private Integer shearingAcceptedQty;
    private Integer shearingRejLengthOfCutBar;
    private Integer shearingRejNoOfSharpEdges;


    // ================= Turning =================
    private Integer turningProductionQty;
    private Integer turningAcceptedQty;
    private Integer turningRejTurningLength;
    private Integer turningRejBarDia;


    // ================= MPI =================
    private Integer mpiProductionQty;
    private Integer mpiAcceptedQty;
    private Integer mpiRejectedQty;


    // ================= Forging =================
    private Integer forgingProductionQty;
    private Integer forgingAcceptedQty;
    private Integer forgingRejectedQty;


    // ================= Quenching =================
    private Integer quenchingProductionQty;
    private Integer quenchingAcceptedQty;
    private Integer quenchingRejectedHardness;


    // ================= Tempering =================
    private Integer temperingProductionQty;
    private Integer temperingAcceptedQty;
    private Integer temperingRejVisual;
    private Integer temperingRejDimensional;
    private Integer temperingRejHardness;
}
