package com.sarthi.dto.IcDtos;

import lombok.Data;

@Data
public class RmHeatQuantityRequestDto {

    private String heatNumber;
    private String manufacturer;

    private Double offeredQty;

    private String tcNumber;
    private String tcDate;      // yyyy-MM-dd
    private Double tcQuantity;

    private Double qtyLeft;
    private Double qtyAccepted;
    private Double qtyRejected;

    private String rejectionReason;
}
