package com.sarthi.service;

import com.sarthi.dto.vendorDtos.VendorPoHeaderResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VendorPoService {

    public List<VendorPoHeaderResponseDto> getPoListByVendorCode(String vendorCode);


}
