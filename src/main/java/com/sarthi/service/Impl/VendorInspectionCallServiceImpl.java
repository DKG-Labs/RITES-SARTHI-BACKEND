package com.sarthi.service.Impl;

import com.sarthi.dto.VendorInspectionCallStatusDto;
import com.sarthi.entity.WorkflowTransition;
import com.sarthi.entity.rawmaterial.InspectionCall;
import com.sarthi.entity.rawmaterial.RmInspectionDetails;
import com.sarthi.entity.processmaterial.ProcessInspectionDetails;
import com.sarthi.entity.finalmaterial.FinalInspectionDetails;
import com.sarthi.repository.WorkflowTransitionRepository;
import com.sarthi.repository.rawmaterial.InspectionCallRepository;
import com.sarthi.repository.rawmaterial.RmInspectionDetailsRepository;
import com.sarthi.repository.processmaterial.ProcessInspectionDetailsRepository;
import com.sarthi.repository.finalmaterial.FinalInspectionDetailsRepository;
import com.sarthi.service.VendorInspectionCallService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Service implementation for Vendor Inspection Call operations.
 */
@Service
public class VendorInspectionCallServiceImpl implements VendorInspectionCallService {

    private static final Logger logger = LoggerFactory.getLogger(VendorInspectionCallServiceImpl.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private InspectionCallRepository inspectionCallRepository;

    @Autowired
    private WorkflowTransitionRepository workflowTransitionRepository;

    @Autowired
    private RmInspectionDetailsRepository rmInspectionDetailsRepository;

    @Autowired
    private ProcessInspectionDetailsRepository processInspectionDetailsRepository;

    @Autowired
    private FinalInspectionDetailsRepository finalInspectionDetailsRepository;

    @Override
    @Transactional(readOnly = true)
    public List<VendorInspectionCallStatusDto> getVendorInspectionCallsWithStatus(String vendorId) {
        logger.info("Fetching inspection calls with workflow status for vendor: {}", vendorId);

        Long t1 = System.currentTimeMillis();

        // 1. Fetch recent inspection calls for the vendor (Limit to 1000 to accommodate
        // larger datasets)
        List<InspectionCall> inspectionCalls = inspectionCallRepository.findByVendorIdOrderByCreatedAtDesc(vendorId,
                PageRequest.of(0, 1000));

        if (inspectionCalls.isEmpty()) {
            return new ArrayList<>();
        }

        logger.info("Found {} inspection calls for vendor: {}", inspectionCalls.size(), vendorId);

        // 2. Collect IDs for bulk fetching
        List<String> requestIds = new ArrayList<>();
        List<Long> rmIcIds = new ArrayList<>();
        List<Long> processIcIds = new ArrayList<>();
        List<Long> finalIcIds = new ArrayList<>();

        for (InspectionCall ic : inspectionCalls) {
            requestIds.add(ic.getIcNumber());

            if ("Raw Material".equalsIgnoreCase(ic.getTypeOfCall())) {
                rmIcIds.add(ic.getId());
            } else if ("Process".equalsIgnoreCase(ic.getTypeOfCall())) {
                processIcIds.add(ic.getId());
            } else if ("Final".equalsIgnoreCase(ic.getTypeOfCall())) {
                finalIcIds.add(ic.getId());
            }
        }

        // 3. Bulk Fetch Workflow Transitions
        Map<String, WorkflowTransition> transitionMap = new HashMap<>();
        if (!requestIds.isEmpty()) {
            // Fetch in chunks if too many, but for now assuming reasonable size (< 2000)
            List<WorkflowTransition> transitions = workflowTransitionRepository
                    .findLatestTransitionsForRequestIds(requestIds);
            for (WorkflowTransition wt : transitions) {
                transitionMap.put(wt.getRequestId(), wt);
            }
        }

        // 4. Bulk Fetch Details by Type
        Map<Long, RmInspectionDetails> rmDetailsMap = new HashMap<>();
        if (!rmIcIds.isEmpty()) {
            List<RmInspectionDetails> details = rmInspectionDetailsRepository.findByInspectionCallIdIn(rmIcIds);
            for (RmInspectionDetails d : details) {
                rmDetailsMap.put(d.getInspectionCall().getId(), d);
            }
        }

        Map<Long, List<ProcessInspectionDetails>> processDetailsGrouped = new HashMap<>();
        if (!processIcIds.isEmpty()) {
            List<ProcessInspectionDetails> pDetails = processInspectionDetailsRepository
                    .findByInspectionCallIdIn(processIcIds);
            processDetailsGrouped = pDetails.stream()
                    .collect(Collectors.groupingBy(d -> d.getInspectionCall().getId()));
        }

        Map<Long, FinalInspectionDetails> finalDetailsMap = new HashMap<>();
        if (!finalIcIds.isEmpty()) {
            List<FinalInspectionDetails> fDetails = finalInspectionDetailsRepository
                    .findByInspectionCallIdIn(finalIcIds);
            for (FinalInspectionDetails d : fDetails) {
                finalDetailsMap.put(d.getInspectionCall().getId(), d);
            }
        }

        Long t2 = System.currentTimeMillis();
        logger.info("Bulk fetching took {} ms", (t2 - t1));

        // 5. Map to DTOs
        List<VendorInspectionCallStatusDto> dtos = new ArrayList<>();
        for (InspectionCall ic : inspectionCalls) {
            WorkflowTransition latestTransition = transitionMap.get(ic.getIcNumber());

            // Resolve Item Name and Qty
            String itemName = "N/A";
            Integer quantityOffered = 0;

            if ("Raw Material".equalsIgnoreCase(ic.getTypeOfCall())) {
                RmInspectionDetails rm = rmDetailsMap.get(ic.getId());
                if (rm != null) {
                    itemName = rm.getItemDescription();
                    quantityOffered = rm.getOfferedQtyErc();
                }
            } else if ("Process".equalsIgnoreCase(ic.getTypeOfCall())) {
                List<ProcessInspectionDetails> pList = processDetailsGrouped.get(ic.getId());
                if (pList != null && !pList.isEmpty()) {
                    itemName = "Process Inspection - Lot: " + pList.get(0).getLotNumber();
                    quantityOffered = pList.stream().mapToInt(ProcessInspectionDetails::getOfferedQty).sum();
                    // Or pList.get(0).getOfferedQty() if typical logic matches existing code
                    // Existing code: return processList.get(0).getOfferedQty();
                    // But sumOfferedQtyByIcId exists in repo.
                    // Let's match existing logic: processList.get(0).getOfferedQty()
                    quantityOffered = pList.get(0).getOfferedQty();
                }
            } else if ("Final".equalsIgnoreCase(ic.getTypeOfCall())) {
                FinalInspectionDetails fd = finalDetailsMap.get(ic.getId());
                if (fd != null) {
                    itemName = "Final Inspection - " + fd.getTotalLots() + " lots";
                    quantityOffered = fd.getTotalOfferedQty();
                }
            }

            dtos.add(VendorInspectionCallStatusDto.builder()
                    .icNumber(ic.getIcNumber())
                    .poNo(ic.getPoNo())
                    .poSerialNo(ic.getPoSerialNo())
                    .typeOfCall(ic.getTypeOfCall())
                    .desiredInspectionDate(
                            ic.getDesiredInspectionDate() != null ? ic.getDesiredInspectionDate().format(DATE_FORMATTER)
                                    : null)
                    .placeOfInspection(ic.getPlaceOfInspection())
                    .itemName(itemName)
                    .quantityOffered(quantityOffered)
                    .workflowStatus(latestTransition != null ? latestTransition.getStatus() : ic.getStatus())
                    .currentRoleName(latestTransition != null ? latestTransition.getCurrentRoleName() : null)
                    .nextRoleName(latestTransition != null ? latestTransition.getNextRoleName() : null)
                    .jobStatus(latestTransition != null ? latestTransition.getJobStatus() : null)
                    .companyName(ic.getCompanyName())
                    .unitName(ic.getUnitName())
                    .createdAt(ic.getCreatedAt() != null ? ic.getCreatedAt().format(DATE_FORMATTER) : null)
                    .updatedAt(ic.getUpdatedAt() != null ? ic.getUpdatedAt().format(DATE_FORMATTER) : null)
                    .build());
        }

        return dtos;
    }
}
