package com.sarthi.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TotalManufaturedQtyOfPoDto {

    private BigDecimal manufaturedQty;
    private BigDecimal rejectedQty;
    private BigDecimal rmAcceptedQty;
    private BigDecimal acceptedQty;
    private String heatNo;
    private BigDecimal weightAcceptedMt;

    public TotalManufaturedQtyOfPoDto(
            BigDecimal manufaturedQty,
            BigDecimal rejectedQty,
            BigDecimal rmAcceptedQty,
            BigDecimal acceptedQty,
            String heatNo
           // BigDecimal weightAcceptedMt
    ) {
        this.manufaturedQty = manufaturedQty;
        this.rejectedQty = rejectedQty;
        this.rmAcceptedQty = rmAcceptedQty;
        this.acceptedQty = acceptedQty;
        this.heatNo = heatNo;
    }


    public TotalManufaturedQtyOfPoDto() {
    }

}
