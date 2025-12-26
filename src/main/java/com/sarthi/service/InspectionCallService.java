package com.sarthi.service;

import com.sarthi.dto.IcDtos.InspectionCallRequestDto;
import com.sarthi.dto.IcDtos.RmInspectionDetailsRequestDto;
import com.sarthi.entity.rawmaterial.InspectionCall;
import org.springframework.stereotype.Service;

@Service
public interface InspectionCallService {

    public InspectionCall createInspectionCall(
            InspectionCallRequestDto icRequest,
            RmInspectionDetailsRequestDto rmRequest
    );
}
