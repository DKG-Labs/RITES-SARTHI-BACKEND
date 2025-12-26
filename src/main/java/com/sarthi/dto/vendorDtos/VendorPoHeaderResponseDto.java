package com.sarthi.dto.vendorDtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class VendorPoHeaderResponseDto {

    private String poNo;
    private String poDate;
    private String poDes;
    private BigDecimal qty;
    private String unit;

   private List<VendorPoItemsResponseDto> poItem;

}
