package com.sarthi.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProcessIeMappingRequestDto {

    private List<IePoiMappingDto> iePoiMappings;
}
