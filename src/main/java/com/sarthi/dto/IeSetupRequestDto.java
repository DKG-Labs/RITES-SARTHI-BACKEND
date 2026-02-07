package com.sarthi.dto;

import lombok.Data;

import java.util.List;

@Data
public class IeSetupRequestDto {

    private String rio;
    private String currentCityOfPosting;
    private Integer metalStampNo;

    private List<IePinPoiDto> iePinPoiList;

    private Integer controllingManagerUserId;
}
