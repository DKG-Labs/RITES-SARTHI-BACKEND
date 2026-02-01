package com.sarthi.service.Impl;

import com.sarthi.dto.PoInspection2ndLevelSerialStatusDto;
import com.sarthi.dto.reports.PoInspection1stLevelStatusDto;
import com.sarthi.dto.reports.PoInspection3rdLevelCallStatusDto;
import com.sarthi.entity.RmHeatFinalResult;
import com.sarthi.entity.WorkflowTransition;
import com.sarthi.entity.rawmaterial.InspectionCall;
import com.sarthi.repository.*;
import com.sarthi.repository.rawmaterial.InspectionCallRepository;
import com.sarthi.service.reports;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class reportsImpl implements reports {
    @Autowired
    private PoHeaderRepository poHeaderRepository;
    @Autowired
    private PoItemRepository poItemRepository;
    @Autowired
    private InspectionCallRepository inspectionCallRepository;
    @Autowired
    private RmHeatFinalResultRepository rmHeatFinalResultRepository;

    @Autowired
    private ProcessIeQtyRepository processIeQtyRepository;
    @Autowired
    private WorkflowTransitionRepository workflowTransitionRepository;
    @Autowired
    private InspectionCompleteDetailsRepository inspectionCompleteDetailsRepository;



  /*  @Override
    public List<PoInspection1stLevelStatusDto> getPoInspection1stLevelStatusList() {
        List<PoInspection1stLevelStatusDto> list =
                poHeaderRepository.fetchPoInspectionStatus();

        AtomicInteger counter = new AtomicInteger(1);
        list.forEach(dto -> dto.setSlNo(counter.getAndIncrement()));

        return list;
    }*/

    @Override
    public List<PoInspection1stLevelStatusDto> getPoInspection1stLevelStatusList() {

        List<PoInspection1stLevelStatusDto> list =
                poHeaderRepository.fetchPoInspectionStatus();

        AtomicInteger counter = new AtomicInteger(1);

        for (PoInspection1stLevelStatusDto dto : list) {

            // ================= Serial No =================
            dto.setSlNo(counter.getAndIncrement());


            // ================= RM Rejection % =================
            Double rmPct =
                    inspectionCallRepository.findRmRejectionPct(dto.getPoNo());

            dto.setRawMaterialRejectionPercentage(
                    rmPct != null ? rmPct : 0.0
            );


            // ================= Get Call Numbers =================
            List<String> callNos =
                    inspectionCallRepository.findCallNumbersByPo(dto.getPoNo());

            if (callNos == null || callNos.isEmpty()) {

                dto.setFinalQuantityAcceptedByRites(0);
                dto.setBalancePoQty(dto.getPoQty());
                dto.setProcessInspectionRejectionPercentage(0.0);

                continue;
            }


            // ================= Offered + Rejected =================
            List<Object[]> resultList =
                    rmHeatFinalResultRepository
                            .findOfferedAndRejectedByCallNos(callNos);

            double offered = 0.0;
            double rejected = 0.0;

            if (resultList != null && !resultList.isEmpty()) {

                Object[] result = resultList.get(0);

                if (result[0] != null)
                    offered = ((Number) result[0]).doubleValue();

                if (result[1] != null)
                    rejected = ((Number) result[1]).doubleValue();
            }


            // ================= Final Accepted =================
            int accepted = (int) Math.round(offered);
            dto.setFinalQuantityAcceptedByRites(accepted);


            // ================= Balance =================
            int balance = dto.getPoQty() - accepted;
            balance = Math.max(balance, 0); // safety

            dto.setBalancePoQty(balance);


            // ================= Process Rejection % =================
            Double processPct =
                    processIeQtyRepository
                            .findProcessRejectionPctByCallNos(callNos);

            dto.setProcessInspectionRejectionPercentage(
                    processPct != null ? processPct : 0.0
            );
        }

        return list;
    }



    @Override
    public List<PoInspection2ndLevelSerialStatusDto> getSerialStatusByPoNo(String poNo) {

        List<PoInspection2ndLevelSerialStatusDto> list =
                poItemRepository.fetchSerialStatusByPoNo(poNo);

        AtomicInteger counter = new AtomicInteger(1);

        for (PoInspection2ndLevelSerialStatusDto dto : list) {

            // ============ Sl No ============
            dto.setSlNo(counter.getAndIncrement());


            // ============ Get Call Numbers ============
            List<String> callNos =
                    inspectionCallRepository
                            .findCallNosByPoAndSerial(poNo, dto.getRlyPoSrNo());

            if (callNos == null || callNos.isEmpty()) {

                dto.setRawMaterialAcceptedMt(0.0);
                dto.setRawMaterialRejectionPercentage(0.0);

                dto.setProcessInspectionMaterialAcceptedNos(0);
                dto.setProcessInspectionMaterialRejectionPercentage(0.0);

                continue;
            }


            // ================= RM Summary =================
            List<Object[]> rmResultList =
                    rmHeatFinalResultRepository
                            .findRmSummaryByCallNos(callNos);

            double rmAccepted = 0.0;
            double rmRejected = 0.0;
            double rmOffered = 0.0;

            if (rmResultList != null && !rmResultList.isEmpty()) {

                Object[] row = rmResultList.get(0);

                if (row[0] != null)
                    rmAccepted = ((Number) row[0]).doubleValue();

                if (row[1] != null)
                    rmRejected = ((Number) row[1]).doubleValue();

                if (row[2] != null)
                    rmOffered = ((Number) row[2]).doubleValue();
            }


            // ================= Set RM =================
            dto.setRawMaterialAcceptedMt(rmAccepted);

            double rmRejectionPct = 0.0;

            if (rmOffered > 0) {
                rmRejectionPct = (rmRejected * 100.0) / rmOffered;
            }

            dto.setRawMaterialRejectionPercentage(rmRejectionPct);


            // ================= Process Summary =================
            List<Object[]> processResultList =
                    processIeQtyRepository
                            .findProcessSummaryByCallNos(callNos);

            int processAccepted = 0;
            double processRejected = 0.0;
            double processOffered = 0.0;

            if (processResultList != null && !processResultList.isEmpty()) {

                Object[] row = processResultList.get(0);

                if (row[0] != null)
                    processAccepted = ((Number) row[0]).intValue();

                if (row[1] != null)
                    processRejected = ((Number) row[1]).doubleValue();

                if (row[2] != null)
                    processOffered = ((Number) row[2]).doubleValue();
            }


            // ================= Set Process =================
            dto.setProcessInspectionMaterialAcceptedNos(processAccepted);

            double processRejectionPct = 0.0;

            if (processOffered > 0) {
                processRejectionPct =
                        (processRejected * 100.0) / processOffered;
            }

            dto.setProcessInspectionMaterialRejectionPercentage(processRejectionPct);
        }

        return list;
    }
/*
    @Override
    public List<PoInspection3rdLevelCallStatusDto> getCallWiseStatusBySerialNo(String serialNo) {

        List<InspectionCall> calls =
                inspectionCallRepository.findBySerialNo(serialNo);

        List<PoInspection3rdLevelCallStatusDto> result = new ArrayList<>();

        AtomicInteger counter = new AtomicInteger(1);

        for (InspectionCall call : calls) {

            String callNo = call.getIcNumber();


            // ============ Get Start & End Date (Single Query) ============
            List<Object[]> dateList =
                    workflowTransitionRepository
                            .findStartAndEndDateByRequestId(callNo);

            Date startDate = null;
            Date completionDate = null;

            if (dateList != null && !dateList.isEmpty()) {

                Object[] dates = dateList.get(0);

                if (dates[0] != null)
                    startDate = (Date) dates[0];

                if (dates[1] != null)
                    completionDate = (Date) dates[1];
            }

            // ============ Mandays ============
            Integer mandays = null;

            if (startDate != null && completionDate != null) {

                long diff =
                        completionDate.getTime() - startDate.getTime();

                mandays = (int) TimeUnit.MILLISECONDS.toDays(diff);

                if (mandays == 0) mandays = 1;
            }


            // ============ Build DTO ============
            PoInspection3rdLevelCallStatusDto dto =
                    new PoInspection3rdLevelCallStatusDto(

                            counter.getAndIncrement(),

                            serialNo,
                            callNo,
                            call.getTypeOfCall(),
                            call.getDesiredInspectionDate(),

                            startDate,
                            completionDate,

                            mandays,

                            null,
                            null,
                            null,

                            null,
                            call.getRemarks(),

                            callNo
                    );

            result.add(dto);
        }

        return result;
    }

 */


    @Override
    public List<PoInspection3rdLevelCallStatusDto> getCallWiseStatusBy(String serialNo) {

        List<InspectionCall> calls =
                inspectionCallRepository.findBySerialNo(serialNo);

        List<PoInspection3rdLevelCallStatusDto> result = new ArrayList<>();

        AtomicInteger counter = new AtomicInteger(1);

        for (InspectionCall call : calls) {

            String callNo = call.getIcNumber();
            String callType = call.getTypeOfCall();


            // ================= Workflow Dates =================
            List<Object[]> dateList =
                    workflowTransitionRepository
                            .findStartAndEndDateByRequestId(callNo);

            Date startDate = null;
            Date completionDate = null;

            if (dateList != null && !dateList.isEmpty()) {

                Object[] dates = dateList.get(0);

                if (dates[0] != null)
                    startDate = (Date) dates[0];

                if (dates[1] != null)
                    completionDate = (Date) dates[1];
            }

            String certificateNo =
                    inspectionCompleteDetailsRepository
                            .findCertificateNoByCallNo(callNo);

//            dto.setIcNumber(
//                    certificateNo != null ? certificateNo : "-"
//            );



            // ================= Mandays =================
            Integer mandays = null;

            if (startDate != null && completionDate != null) {

                long diff =
                        completionDate.getTime() - startDate.getTime();

                mandays = (int) TimeUnit.MILLISECONDS.toDays(diff);

                if (mandays == 0) mandays = 1;
            }


            // ================= Qty Variables =================
            Double offeredQty = null;
            Double acceptedQty = null;
            Double balanceQty = null;
            Double rejectionPct = null;


            // ================= RAW MATERIAL =================
            if (callType != null &&
                    (callType.toUpperCase().contains("RM")
                            || callType.toUpperCase().contains("RAW"))) {

                List<Object[]> rmList =
                        rmHeatFinalResultRepository
                                .findRmSummaryByCallNos(
                                        List.of(callNo));

                if (rmList != null && !rmList.isEmpty()) {

                    Object[] row = rmList.get(0);

                    double offered = 0;
                    double accepted = 0;
                    double rejected = 0;

                    if (row[2] != null)
                        offered = ((Number) row[2]).doubleValue();

                    if (row[0] != null)
                        accepted = ((Number) row[0]).doubleValue();

                    if (row[1] != null)
                        rejected = ((Number) row[1]).doubleValue();


                    offeredQty = offered;
                    acceptedQty = accepted;
                    balanceQty = offered - accepted;

                    if (offered > 0) {
                        rejectionPct = (rejected * 100.0) / offered;
                    }
                }
            }


            // ================= PROCESS =================
            else if (callType != null &&
                    callType.toUpperCase().contains("PROCESS")) {

                List<Object[]> processList =
                        processIeQtyRepository
                                .findProcessQtyByCallNo(callNo);

                if (processList != null && !processList.isEmpty()) {

                    Object[] row = processList.get(0);

                    double offered = 0;
                    int accepted = 0;
                    double rejected = 0;

                    if (row[0] != null)
                        offered = ((Number) row[0]).doubleValue();

                    if (row[1] != null)
                        accepted = ((Number) row[1]).intValue();

                    if (row[2] != null)
                        rejected = ((Number) row[2]).doubleValue();


                    offeredQty = offered;
                    acceptedQty = (double) accepted;
                    balanceQty = offered - accepted;

                    if (offered > 0) {
                        rejectionPct = (rejected * 100.0) / offered;
                    }
                }
            }

            // ================= FINAL =================
            // else → leave null


            // ================= Build DTO =================
            PoInspection3rdLevelCallStatusDto dto =
                    new PoInspection3rdLevelCallStatusDto(

                            counter.getAndIncrement(),

                            serialNo,
                            callNo,
                            callType,
                            call.getDesiredInspectionDate(),

                            startDate,
                            completionDate,

                            mandays,

                            offeredQty,
                            acceptedQty,
                            balanceQty,

                            rejectionPct,
                            call.getRemarks(),

                            certificateNo != null ? certificateNo : "-"
                    );

            result.add(dto);
        }

        return result;
    }

@Override
public Page<PoInspection3rdLevelCallStatusDto> getCallWiseStatusBySerialNo(
        String serialNo,
        int page,
        int size) {

    Pageable pageable = PageRequest.of(page, size);

    //  Get paginated calls
    Page<InspectionCall> callPage =
            inspectionCallRepository.findBySerialNo(serialNo, pageable);

    List<InspectionCall> calls = callPage.getContent();

    if (calls.isEmpty()) {
        return Page.empty(pageable);
    }

    //  Collect call numbers
    List<String> callNos = calls.stream()
            .map(InspectionCall::getIcNumber)
            .toList();

    //  Bulk fetch all related data

    Map<String, Object[]> workflowMap =
            workflowTransitionRepository
                    .findStartAndEndDateByRequestIds(callNos)
                    .stream()
                    .collect(Collectors.toMap(
                            r -> (String) r[0],
                            r -> r
                    ));

    Map<String, String> certificateMap =
            inspectionCompleteDetailsRepository
                    .findCertificateNosByCallNos(callNos)
                    .stream()
                    .collect(Collectors.toMap(
                            r -> (String) r[0],
                            r -> (String) r[1]
                    ));

    Map<String, Object[]> processMap =
            processIeQtyRepository
                    .findProcessQtyByCallNos(callNos)
                    .stream()
                    .collect(Collectors.toMap(
                            r -> (String) r[0],
                            r -> r
                    ));

    Map<String, Object[]> rmMap =
            rmHeatFinalResultRepository
                    .findRmSummaryByCallNos(callNos)
                    .stream()
                    .collect(Collectors.toMap(
                            r -> r[0].toString(),
                            r -> r
                    ));


    AtomicInteger counter =
            new AtomicInteger(page * size + 1);

    List<PoInspection3rdLevelCallStatusDto> dtoList =
            new ArrayList<>();

    // Build DTO
    for (InspectionCall call : calls) {

        String callNo = call.getIcNumber();
        String callType = call.getTypeOfCall();

        // ===== Workflow =====
        Date startDate = null;
        Date completionDate = null;

        Object[] wf = workflowMap.get(callNo);

        if (wf != null) {
            startDate = (Date) wf[1];
            completionDate = (Date) wf[2];
        }

        // ===== Certificate =====
        String certificateNo =
                certificateMap.getOrDefault(callNo, "-");

        // ===== Mandays =====
        Integer mandays = null;

        if (startDate != null && completionDate != null) {

            long diff =
                    completionDate.getTime() - startDate.getTime();

            mandays = (int) TimeUnit.MILLISECONDS.toDays(diff);

            if (mandays == 0) mandays = 1;
        }

        // ===== Qty =====
        Double offeredQty = null;
        Double acceptedQty = null;
        Double balanceQty = null;
        Double rejectionPct = null;

        // RAW MATERIAL
        if (callType != null &&
                (callType.toUpperCase().contains("RM")
                        || callType.toUpperCase().contains("RAW"))) {

            Object[] row = rmMap.get(callNo);

            if (row != null) {

                double accepted =
                        row[1] != null ? ((Number) row[1]).doubleValue() : 0;

                double rejected =
                        row[2] != null ? ((Number) row[2]).doubleValue() : 0;

                double offered =
                        row[3] != null ? ((Number) row[3]).doubleValue() : 0;

                offeredQty = offered;
                acceptedQty = accepted;
                balanceQty = offered - accepted;

                if (offered > 0) {
                    rejectionPct = (rejected * 100) / offered;
                }
            }
        }

        // PROCESS
        else if (callType != null &&
                callType.toUpperCase().contains("PROCESS")) {

            Object[] row = processMap.get(callNo);

            if (row != null) {

                double offered =
                        row[1] != null ? ((Number) row[1]).doubleValue() : 0;

                double accepted =
                        row[2] != null ? ((Number) row[2]).doubleValue() : 0;

                double rejected =
                        row[3] != null ? ((Number) row[3]).doubleValue() : 0;

                offeredQty = offered;
                acceptedQty = accepted;
                balanceQty = offered - accepted;

                if (offered > 0) {
                    rejectionPct = (rejected * 100) / offered;
                }
            }
        }

        // ===== DTO =====
        PoInspection3rdLevelCallStatusDto dto =
                new PoInspection3rdLevelCallStatusDto(

                        counter.getAndIncrement(),

                        serialNo,
                        callNo,
                        callType,
                        call.getDesiredInspectionDate(),

                        startDate,
                        completionDate,

                        mandays,

                        offeredQty,
                        acceptedQty,
                        balanceQty,

                        rejectionPct,
                        call.getRemarks(),

                        certificateNo
                );

        dtoList.add(dto);
    }

    return new PageImpl<>(
            dtoList,
            pageable,
            callPage.getTotalElements()
    );
}




}
