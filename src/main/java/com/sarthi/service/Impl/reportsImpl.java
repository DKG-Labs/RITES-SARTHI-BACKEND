package com.sarthi.service.Impl;

import com.sarthi.dto.PoInspection2ndLevelSerialStatusDto;
import com.sarthi.dto.reports.*;
import com.sarthi.entity.RmHeatFinalResult;
import com.sarthi.entity.WorkflowTransition;
import com.sarthi.entity.processmaterial.*;
import com.sarthi.entity.rawmaterial.InspectionCall;
import com.sarthi.repository.*;
import com.sarthi.repository.processmaterial.*;
import com.sarthi.repository.rawmaterial.InspectionCallRepository;
import com.sarthi.service.reports;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.*;
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
    @Autowired
    private ProcessLineFinalResultRepository processLineFinalResultRepository;
    @Autowired
    private ProcessShearingDataRepository processShearingDataRepository;

    @Autowired
    private ProcessTurningDataRepository processTurningDataRepository;

    @Autowired
    private ProcessForgingDataRepository processForgingDataRepository;
    @Autowired
    private ProcessFinalCheckDataRepository processFinalCheckDataRepository;
    @Autowired
    private ProcessTestingFinishingDataRepository processTestingFinishingDataRepository;
    @Autowired
    private ProcessQuenchingDataRepository processQuenchingDataRepository;



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
    public List<PoInspection3rdLevelCallStatusDto> getCallWiseStatusBy(String poNo, String serialNo) {

        List<InspectionCall> calls =
                inspectionCallRepository.findByPoNoAndSerialNo(poNo, serialNo);

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
        String poNo,
        String serialNo,
        int page,
        int size) {

    Pageable pageable = PageRequest.of(page, size);

    //  Get paginated calls
    Page<InspectionCall> callPage =
            inspectionCallRepository.findByPoNoAndSerialNo(poNo, serialNo, pageable);

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


/*

    public List<FourthLevelInspectionDto> getFourthLevelReport(String callId) {

        InspectionCall call = inspectionCallRepository.findByIcNumber(callId)
                .orElseThrow(() -> new RuntimeException("Call not found"));

        FourthLevelInspectionDto dto = new FourthLevelInspectionDto();

        List<ProcessLineFinalResult> processList =
                processLineFinalResultRepository.findByInspectionCallNo(callId);

        BasicDetailsDto basic = new BasicDetailsDto();

        basic.setDate(call.getDate());
        basic.setShift(call.getShift());
        basic.setRlyName(call.getRlyName());
        basic.setPoSrNo(call.getPoSrNo());
        basic.setLotNumber(call.getLotNumber());
        basic.setTotalAcceptedQty(call.getAcceptedQty());
        basic.setTotalRejectionQty(call.getRejectedQty());

        dto.setBasicDetails(basic);


        // ================= PROCESS QTY =================
        ProcessQtyDto process = new ProcessQtyDto();

        process.setShearingProductionQty(call.getShearingProd());
        process.setShearingRejectionQty(call.getShearingRej());

        process.setTurningProductionQty(call.getTurningProd());
        process.setTurningRejectionQty(call.getTurningRej());

        process.setMpiProductionQty(call.getMpiProd());
        process.setMpiRejectionQty(call.getMpiRej());

        process.setForgingProductionQty(call.getForgingProd());
        process.setForgingRejectionQty(call.getForgingRej());

        process.setQuenchingProductionQty(call.getQuenchingProd());
        process.setQuenchingRejectionQty(call.getQuenchingRej());

        process.setTemperingProductionQty(call.getTemperingProd());
        process.setTemperingRejectionQty(call.getTemperingRej());

        dto.setProcessQty(process);


        // ================= SHEARING DEFECTS =================
        ShearingDefectsDto shearing = new ShearingDefectsDto();

        shearing.setLengthOfCutBar(call.getLengthCut());
        shearing.setOvalityImproperDiaAtEnd(call.getOvality());
        shearing.setSharpEdges(call.getSharpEdges());
        shearing.setCrackedEdges(call.getCrackedEdges());

        dto.setShearingDefects(shearing);


        // ================= TURNING DEFECTS =================
        TurningDefectsDto turning = new TurningDefectsDto();

        turning.setParallelLength(call.getParallelLength());
        turning.setFullTurningLength(call.getFullTurning());
        turning.setTurningDia(call.getTurningDia());

        dto.setTurningDefects(turning);


        // ================= FORGING DEFECTS =================
        ForgingDefectsDto forging = new ForgingDefectsDto();

        forging.setForgingTemperature(call.getForgingTemp());
        forging.setForgingStabilisationRejection(call.getForgingStable());
        forging.setImproperForging(call.getImproperForging());
        forging.setForgingMarksNotches(call.getMarks());

        dto.setForgingDefects(forging);


        // ================= DIMENSIONAL =================
        DimensionalDefectsDto dimensional = new DimensionalDefectsDto();

        dimensional.setBoxGauge(call.getBoxGauge());
        dimensional.setFlatBearingArea(call.getFlatArea());
        dimensional.setFallingGauge(call.getFallingGauge());

        dto.setDimensionalDefects(dimensional);


        // ================= VISUAL =================
        VisualDefectsDto visual = new VisualDefectsDto();

        visual.setSurfaceDefect(call.getSurfaceDefect());
        visual.setEmbossingDefect(call.getEmbossing());
        visual.setMarking(call.getMarking());

        dto.setVisualDefects(visual);


        // ================= TESTING =================
        TestingDefectsDto testing = new TestingDefectsDto();

        testing.setTemperingHardness(call.getTemperingHardness());
        testing.setToeLoad(call.getToeLoad());
        testing.setWeight(call.getWeight());

        dto.setTestingDefects(testing);


        // ================= FINISHING =================
        FinishingDefectsDto finishing = new FinishingDefectsDto();

        finishing.setPaintIdentification(call.getPaintId());
        finishing.setErcCoating(call.getErcCoating());

        dto.setFinishingDefects(finishing);


        return dto;
    }

*/
public List<FourthLevelInspectionDto> getFourthLevelReport(String callId) {

    // Get call master
    InspectionCall call = inspectionCallRepository
            .findByIcNumber(callId)
            .orElseThrow(() -> new RuntimeException("Call not found"));


    // Get all process rows
    List<ProcessLineFinalResult> processList =
            processLineFinalResultRepository
                    .findByInspectionCallNo(callId);


    List<FourthLevelInspectionDto> result = new ArrayList<>();


    // Each process row → one DTO
    for (ProcessLineFinalResult p : processList) {

        FourthLevelInspectionDto dto =
                new FourthLevelInspectionDto();

        if (p.getLotNumber() == null || p.getShift()== null) {
          //  log.warn("Skipping record. lotNo={}, shift={}", lotNo, shift);
            continue;
        }
        // ================= BASIC =================
        BasicDetailsDto basic = new BasicDetailsDto();

        basic.setDate(p.getCreatedAt().toLocalDate());
        basic.setShift(p.getShift());
        basic.setRlyName("");
        basic.setPoSrNo(call.getPoSerialNo());
        basic.setLotNumber(p.getLotNumber());
        basic.setTotalAcceptedQty(p.getTotalAccepted());
        basic.setTotalRejectionQty(p.getTotalRejected());

        dto.setBasicDetails(basic);


        // ================= PROCESS =================
        ProcessQtyDto process = new ProcessQtyDto();

        process.setShearingProductionQty(p.getShearingManufactured());
        process.setShearingRejectionQty(p.getShearingRejected());

        process.setTurningProductionQty(p.getTurningManufactured());
        process.setTurningRejectionQty(p.getTurningRejected());

        process.setMpiProductionQty(p.getMpiManufactured());
        process.setMpiRejectionQty(p.getMpiRejected());

        process.setForgingProductionQty(p.getForgingManufactured());
        process.setForgingRejectionQty(p.getForgingRejected());

        process.setQuenchingProductionQty(p.getQuenchingManufactured());
        process.setQuenchingRejectionQty(p.getQuenchingRejected());

        process.setTemperingProductionQty(p.getTemperingManufactured());
        process.setTemperingRejectionQty(p.getTemperingRejected());

        dto.setProcessQty(process);


        // ================= SHEARING DEFECTS =================

        Object[] sums =
                processShearingDataRepository
                        .getShearingSumByDate(
                                callId,
                                p.getLotNumber(),
                                p.getShift(),
                                p.getCreatedAt().toLocalDate()
                        );

        ShearingDefectsDto shearing = new ShearingDefectsDto();

        if (sums != null && sums.length == 4) {

            shearing.setLengthOfCutBar(
                    ((Number) sums[0]).intValue());

            shearing.setOvalityImproperDiaAtEnd(
                    ((Number) sums[1]).intValue());

            shearing.setSharpEdges(
                    ((Number) sums[2]).intValue());

            shearing.setCrackedEdges(
                    ((Number) sums[3]).intValue());
        }

        dto.setShearingDefects(shearing);




        // ================= TURNING DEFECTS =================
        Object[] tSums =
                processTurningDataRepository
                        .getTurningSumByDate(
                                callId,
                                p.getLotNumber(),
                                p.getShift(),
                                p.getCreatedAt().toLocalDate()
                        );

        TurningDefectsDto turning = new TurningDefectsDto();

        if (tSums != null && tSums.length == 3) {

            turning.setParallelLength(
                    ((Number) tSums[0]).intValue());

            turning.setFullTurningLength(
                    ((Number) tSums[1]).intValue());

            turning.setTurningDia(
                    ((Number) tSums[2]).intValue());
        }

        dto.setTurningDefects(turning);


// ================= FORGING DEFECTS =================

        Object[] fSums =
                processForgingDataRepository
                        .getForgingSumByDate(
                                callId,
                                p.getLotNumber(),
                                p.getShift(),
                                p.getCreatedAt().toLocalDate()
                        );

        ForgingDefectsDto forging = new ForgingDefectsDto();

        if (fSums != null && fSums.length == 4) {

            forging.setForgingTemperature(
                    ((Number) fSums[0]).intValue());

            forging.setForgingStabilisationRejection(
                    ((Number) fSums[1]).intValue());

            forging.setImproperForging(
                    ((Number) fSums[2]).intValue());

            forging.setForgingMarksNotches(
                    ((Number) fSums[3]).intValue());
        }

        dto.setForgingDefects(forging);

        Object[] visualSums =
                processFinalCheckDataRepository
                        .getVisualDefectsSumByDate(
                                callId,
                                p.getLotNumber(),
                                p.getShift(),
                                p.getCreatedAt().toLocalDate()
                        );

        VisualDefectsDto visual = new VisualDefectsDto();

        if (visualSums != null && visualSums.length == 2) {

            visual.setSurfaceDefect(
                    ((Number) visualSums[0]).intValue());

            visual.setMarking(
                    ((Number) visualSums[1]).intValue());
        }

        Integer forgingEmbossing =
                processForgingDataRepository
                        .getForgingEmbossingSumByDate(
                                callId,
                                p.getLotNumber(),
                                p.getShift(),
                                p.getCreatedAt().toLocalDate()
                        );

        Integer finalEmbossing =
                processFinalCheckDataRepository
                        .getFinalEmbossingSumByDate(
                                callId,
                                p.getLotNumber(),
                                p.getShift(),
                                p.getCreatedAt().toLocalDate()
                        );


        int totalEmbossing =
                (forgingEmbossing != null ? forgingEmbossing : 0)
                        + (finalEmbossing != null ? finalEmbossing : 0);


        visual.setEmbossingDefect(totalEmbossing);

        dto.setVisualDefects(visual);

        // Tempering hardness (Final Check)
        Integer temperingHardness =
                processFinalCheckDataRepository
                        .getTemperingHardnessSumByDate(
                                callId,
                                p.getLotNumber(),
                                p.getShift(),
                                p.getCreatedAt().toLocalDate()
                        );

// Testing + Finishing
        Object[] tfSums =
                processTestingFinishingDataRepository
                        .getTestingFinishingSumByDate(
                                callId,
                                p.getLotNumber(),
                                p.getShift(),
                                p.getCreatedAt().toLocalDate()
                        );

// ========== Testing ==========
        TestingDefectsDto testing = new TestingDefectsDto();

        testing.setTemperingHardness(
                temperingHardness != null ? temperingHardness : 0);

        if (tfSums != null && tfSums.length == 4) {

            testing.setToeLoad(
                    ((Number) tfSums[0]).intValue());

            testing.setWeight(
                    ((Number) tfSums[1]).intValue());
        }

        dto.setTestingDefects(testing);

        FinishingDefectsDto finishing = new FinishingDefectsDto();

        if (tfSums != null && tfSums.length == 4) {

            finishing.setPaintIdentification(
                    ((Number) tfSums[2]).intValue());

            finishing.setErcCoating(
                    ((Number) tfSums[3]).intValue());
        }

        dto.setFinishingDefects(finishing);
// ===== BOX GAUGE =====
        Integer quenchingBox =
                processQuenchingDataRepository
                        .getQuenchingBoxGaugeSum(
                                callId,
                                p.getLotNumber(),
                                p.getShift(),
                                p.getCreatedAt().toLocalDate()
                        );

        Integer finalBox =
                processFinalCheckDataRepository
                        .getFinalBoxGaugeSum(
                                callId,
                                p.getLotNumber(),
                                p.getShift(),
                                p.getCreatedAt().toLocalDate()
                        );

        int totalBoxGauge =
                (quenchingBox != null ? quenchingBox : 0)
                        + (finalBox != null ? finalBox : 0);


        Integer quenchFlat =
                processQuenchingDataRepository
                        .getQuenchingFlatBearingSum(
                                callId,
                                p.getLotNumber(),
                                p.getShift(),
                                p.getCreatedAt().toLocalDate()
                        );

        Integer quenchFall =
                processQuenchingDataRepository
                        .getQuenchingFallingGaugeSum(
                                callId,
                                p.getLotNumber(),
                                p.getShift(),
                                p.getCreatedAt().toLocalDate()
                        );

        Integer finalFlat =
                processFinalCheckDataRepository
                        .getFinalFlatBearingSum(
                                callId,
                                p.getLotNumber(),
                                p.getShift(),
                                p.getCreatedAt().toLocalDate()
                        );

        Integer finalFall =
                processFinalCheckDataRepository
                        .getFinalFallingGaugeSum(
                                callId,
                                p.getLotNumber(),
                                p.getShift(),
                                p.getCreatedAt().toLocalDate()
                        );

// Safe sum
        int flatBearing =
                (quenchFlat != null ? quenchFlat : 0)
                        + (finalFlat != null ? finalFlat : 0);

        int fallingGauge =
                (quenchFall != null ? quenchFall : 0)
                        + (finalFall != null ? finalFall : 0);




        DimensionalDefectsDto dimensional = new DimensionalDefectsDto();

        dimensional.setBoxGauge(totalBoxGauge);
        dimensional.setFlatBearingArea(flatBearing);
        dimensional.setFallingGauge(fallingGauge);

        dto.setDimensionalDefects(dimensional);



/*


        DimensionalDefectsDto dimensional = new DimensionalDefectsDto();
        dimensional.setBoxGauge(call.getBoxGauge());
        dimensional.setFlatBearingArea(call.getFlatArea());
        dimensional.setFallingGauge(call.getFallingGauge());
        dto.setDimensionalDefects(dimensional);


        VisualDefectsDto visual = new VisualDefectsDto();

        visual.setEmbossingDefect(call.getEmbossing());
        visual.setMarking(call.getMarking());
        dto.setVisualDefects(visual);


        TestingDefectsDto testing = new TestingDefectsDto();
        testing.setTemperingHardness(call.getTemperingHardness());
        testing.setToeLoad(call.getToeLoad());
        testing.setWeight(call.getWeight());
        dto.setTestingDefects(testing);


        FinishingDefectsDto finishing = new FinishingDefectsDto();
        finishing.setPaintIdentification(call.getPaintId());
        finishing.setErcCoating(call.getErcCoating());
        dto.setFinishingDefects(finishing);
*/

        result.add(dto);
    }


    return result;
}



}
