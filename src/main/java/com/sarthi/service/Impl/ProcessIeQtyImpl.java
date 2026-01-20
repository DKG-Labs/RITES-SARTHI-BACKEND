package com.sarthi.service.Impl;

import com.sarthi.constant.AppConstant;
import com.sarthi.dto.InspectionQtySummaryResponse;
import com.sarthi.dto.InspectionQtySummaryView;
import com.sarthi.entity.ProcessIeQty;
import com.sarthi.entity.processmaterial.ProcessInspectionDetails;
import com.sarthi.entity.rawmaterial.InspectionCall;
import com.sarthi.exception.BusinessException;
import com.sarthi.exception.ErrorDetails;
import com.sarthi.repository.ProcessIeQtyRepository;
import com.sarthi.repository.processmaterial.ProcessInspectionDetailsRepository;
import com.sarthi.repository.rawmaterial.InspectionCallRepository;
import com.sarthi.service.ProcessIeQtyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessIeQtyImpl implements ProcessIeQtyService {

    @Autowired
    private ProcessIeQtyRepository processIeQtyRepository;
    @Autowired
    private InspectionCallRepository inspectionCallRepository;
    @Autowired
    private ProcessInspectionDetailsRepository processInspectionDetailsRepository;

//    @Override
//    public InspectionQtySummaryResponse getQtySummary(String requestId) {
//
//        InspectionQtySummaryView view =
//                processIeQtyRepository.getQtySummaryByRequestId(requestId);
//
//        if (view == null) {
//            return new InspectionQtySummaryResponse(0, 0, 0);
//        }
//
//        return new InspectionQtySummaryResponse(
//                view.getAcceptedQty(),
//                view.getTotalOfferedQty(),
//                view.getTotalManufactureQty()
//        );
//    }
@Override
public InspectionQtySummaryResponse getQtySummary(String requestId) {


    boolean hasProcessQty =
            processIeQtyRepository.existsByRequestId(requestId);


    if (hasProcessQty) {
        InspectionCall ic =
                inspectionCallRepository
                        .findByIcNumber(requestId)
                        .orElseThrow(() -> new BusinessException(
                                new ErrorDetails(
                                        AppConstant.ERROR_CODE_RESOURCE,
                                        AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                        AppConstant.ERROR_TYPE_VALIDATION,
                                        "Invalid Inspection Call: " + requestId
                                )
                        ));


        Integer totalOfferedQty =
                processInspectionDetailsRepository
                        .sumOfferedQtyByIcId(ic.getId());

        InspectionQtySummaryView view =
                processIeQtyRepository.getQtySummaryByRequestId(requestId);

        if (view == null) {
            return new InspectionQtySummaryResponse(0, 0, 0);
        }

        return new InspectionQtySummaryResponse(
                view.getAcceptedQty(),
                totalOfferedQty,
                view.getTotalManufactureQty()
        );
    }


    InspectionCall ic =
            inspectionCallRepository
                    .findByIcNumber(requestId)
                    .orElseThrow(() -> new BusinessException(
                            new ErrorDetails(
                                    AppConstant.ERROR_CODE_RESOURCE,
                                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                    AppConstant.ERROR_TYPE_VALIDATION,
                                    "Invalid Inspection Call: " + requestId
                            )
                    ));


    Integer totalOfferedQty =
            processInspectionDetailsRepository
                    .sumOfferedQtyByIcId(ic.getId());
    System.out.print("totsl"+totalOfferedQty);


    return new InspectionQtySummaryResponse(
            0,                  // acceptedQty
            totalOfferedQty,    // offeredQty from lots
            0                   // manufactureQty
    );
}




}
