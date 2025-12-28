package com.sarthi.dto.vendorDtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PoResponseDto {

    private String poNumber;
    private LocalDate poDate;
    private String description;
    private Integer poQuantity;
    private String unit;
    private String status;

    private String amendmentNo;
    private LocalDate amendmentDate;

    private List<PoItemResponseDto> items;
}
