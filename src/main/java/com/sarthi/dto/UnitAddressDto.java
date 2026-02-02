package com.sarthi.dto;

import lombok.Data;

@Data
public class UnitAddressDto {
    private String unitName;
    private String address;

    public UnitAddressDto(String unitName, String address) {
        this.unitName = unitName;
        this.address = address;
    }
}
