package com.sarthi.dto.reports;

import lombok.Data;

@Data
public class ProcessQtyDto {

    private Integer shearingProductionQty;
    private Integer shearingRejectionQty;

    private Integer turningProductionQty;
    private Integer turningRejectionQty;

    private Integer mpiProductionQty;
    private Integer mpiRejectionQty;

    private Integer forgingProductionQty;
    private Integer forgingRejectionQty;

    private Integer quenchingProductionQty;
    private Integer quenchingRejectionQty;

    private Integer temperingProductionQty;
    private Integer temperingRejectionQty;
}
