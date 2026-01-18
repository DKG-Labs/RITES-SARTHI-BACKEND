package com.sarthi.service.Impl;

import com.sarthi.dto.InspectionQtySummaryResponse;
import com.sarthi.dto.InspectionQtySummaryView;
import com.sarthi.entity.ProcessIeQty;
import com.sarthi.repository.ProcessIeQtyRepository;
import com.sarthi.service.ProcessIeQtyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessIeQtyImpl implements ProcessIeQtyService {

    @Autowired
    private ProcessIeQtyRepository processIeQtyRepository;

    @Override
    public InspectionQtySummaryResponse getQtySummary(String requestId) {

        InspectionQtySummaryView view =
                processIeQtyRepository.getQtySummaryByRequestId(requestId);

        if (view == null) {
            return new InspectionQtySummaryResponse(0, 0, 0);
        }

        return new InspectionQtySummaryResponse(
                view.getAcceptedQty(),
                view.getTotalOfferedQty(),
                view.getTotalManufactureQty()
        );
    }



}
