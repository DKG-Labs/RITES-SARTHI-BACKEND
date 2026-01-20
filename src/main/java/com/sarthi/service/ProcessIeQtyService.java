package com.sarthi.service;

import com.sarthi.dto.InspectionQtySummaryResponse;
import org.springframework.stereotype.Service;

@Service
public interface ProcessIeQtyService {

    public InspectionQtySummaryResponse getQtySummary(String requestId);
}
