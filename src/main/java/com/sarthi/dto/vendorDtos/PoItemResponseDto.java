package com.sarthi.dto.vendorDtos;

import lombok.Data;

@Data
public class PoItemResponseDto {

    private String poSerialNo;
    private String consignee;
    private Integer orderedQuantity;
    private String deliveryPeriod;
    private String status;
}
