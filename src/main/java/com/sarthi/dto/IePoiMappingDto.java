package com.sarthi.dto;

import lombok.Data;

import java.util.List;

@Data
public class IePoiMappingDto {
    private Long ieUserId;
    private List<String> poiCodes;
}

