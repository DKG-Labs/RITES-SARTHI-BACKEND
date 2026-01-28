package com.sarthi.service;

import com.sarthi.dto.InspectionQtySummaryResponse;
import com.sarthi.dto.TotalManufaturedQtyOfPoDto;
import org.springframework.stereotype.Service;

@Service
public interface ProcessIeQtyService {

    public InspectionQtySummaryResponse getQtySummary(String requestId);


    public String getpoNumberByCallNo(String requestedId);


    public TotalManufaturedQtyOfPoDto getTotalManufaturedQtyPo(String heatNo, String poNo);

}
