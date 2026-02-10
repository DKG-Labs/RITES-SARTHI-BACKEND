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

    public ProcessQtyDto() {

        this.shearingProductionQty = 0;
        this.shearingRejectionQty = 0;

        this.turningProductionQty = 0;
        this.turningRejectionQty = 0;

        this.mpiProductionQty = 0;
        this.mpiRejectionQty = 0;

        this.forgingProductionQty = 0;
        this.forgingRejectionQty = 0;

        this.quenchingProductionQty = 0;
        this.quenchingRejectionQty = 0;

        this.temperingProductionQty = 0;
        this.temperingRejectionQty = 0;
    }
}
