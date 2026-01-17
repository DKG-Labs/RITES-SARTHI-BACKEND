package com.sarthi.service.Impl;

import com.sarthi.dto.IcDtos.*;

import com.sarthi.entity.rawmaterial.InspectionCall;
import com.sarthi.entity.rawmaterial.RmChemicalAnalysis;
import com.sarthi.entity.rawmaterial.RmHeatQuantity;
import com.sarthi.entity.rawmaterial.RmInspectionDetails;
import com.sarthi.repository.rawmaterial.InspectionCallRepository;
import com.sarthi.service.InspectionCallService;
import com.sarthi.service.InventoryEntryService;
import com.sarthi.util.IcNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InspectionCallServiceImpl implements InspectionCallService {

    private static final Logger logger = LoggerFactory.getLogger(InspectionCallServiceImpl.class);

    private final InspectionCallRepository inspectionCallRepository;
    private final IcNumberGenerator icNumberGenerator;
    private final InventoryEntryService inventoryEntryService;

    @Autowired
    public InspectionCallServiceImpl(
            InspectionCallRepository inspectionCallRepository,
            IcNumberGenerator icNumberGenerator,
            InventoryEntryService inventoryEntryService) {
        this.inspectionCallRepository = inspectionCallRepository;
        this.icNumberGenerator = icNumberGenerator;
        this.inventoryEntryService = inventoryEntryService;
    }

    @Override
    public InspectionCall createInspectionCall(
            InspectionCallRequestDto icRequest,
            RmInspectionDetailsRequestDto rmRequest
    ) {
        logger.info("========== CREATE RAW MATERIAL INSPECTION CALL ==========");
        logger.info("IC Request: {}", icRequest);
        logger.info("RM Details: {}", rmRequest);

        InspectionCall inspectionCall = new InspectionCall();

        // Generate IC Number with daily sequence reset
        LocalDate today = LocalDate.now();
        long dailySequence = inspectionCallRepository.countByTypeOfCallAndCreatedDate("Raw Material", today) + 1;
        String icNumber = icNumberGenerator.generateIcNumber("Raw Material", dailySequence);
        logger.info("Generated IC Number: {} (Daily Sequence: {})", icNumber, dailySequence);

        inspectionCall.setIcNumber(icNumber);
        inspectionCall.setPoNo(icRequest.getPoNo());
        inspectionCall.setPoSerialNo(icRequest.getPoSerialNo());
        inspectionCall.setTypeOfCall(icRequest.getTypeOfCall());
        inspectionCall.setErcType(icRequest.getErcType());
        inspectionCall.setStatus(icRequest.getStatus());
        inspectionCall.setVendorId(icRequest.getVendorId());
        inspectionCall.setPlaceOfInspection(icRequest.getPlaceOfInspection());

        inspectionCall.setDesiredInspectionDate(
                LocalDate.parse(icRequest.getDesiredInspectionDate())
        );

        if (icRequest.getActualInspectionDate() != null) {
            inspectionCall.setActualInspectionDate(
                    LocalDate.parse(icRequest.getActualInspectionDate())
            );
        }

        inspectionCall.setCompanyId(icRequest.getCompanyId());
        inspectionCall.setCompanyName(icRequest.getCompanyName());
        inspectionCall.setUnitId(icRequest.getUnitId());
        inspectionCall.setUnitName(icRequest.getUnitName());
        inspectionCall.setUnitAddress(icRequest.getUnitAddress());
        inspectionCall.setRemarks(icRequest.getRemarks());

        inspectionCall.setCreatedBy(icRequest.getCreatedBy());
        inspectionCall.setUpdatedBy(icRequest.getUpdatedBy());
        inspectionCall.setCreatedAt(LocalDateTime.now());
        inspectionCall.setUpdatedAt(LocalDateTime.now());

        // ================== RM INSPECTION DETAILS ==================
        RmInspectionDetails rmDetails = new RmInspectionDetails();
        rmDetails.setInspectionCall(inspectionCall);
        inspectionCall.setRmInspectionDetails(rmDetails);

        rmDetails.setItemDescription(rmRequest.getItemDescription());
        rmDetails.setItemQuantity(rmRequest.getItemQuantity());
        rmDetails.setConsigneeZonalRailway(rmRequest.getConsigneeZonalRailway());
        rmDetails.setHeatNumbers(rmRequest.getHeatNumbers());
        rmDetails.setTcNumber(rmRequest.getTcNumber());

        if (rmRequest.getTcDate() != null) {
            rmDetails.setTcDate(LocalDate.parse(rmRequest.getTcDate()));
        }

        rmDetails.setTcQuantity(toBigDecimal(rmRequest.getTcQuantity()));
        rmDetails.setManufacturer(rmRequest.getManufacturer());
        rmDetails.setSupplierName(rmRequest.getSupplierName());
        rmDetails.setSupplierAddress(rmRequest.getSupplierAddress());

        rmDetails.setInvoiceNumber(rmRequest.getInvoiceNumber());
        if (rmRequest.getInvoiceDate() != null) {
            rmDetails.setInvoiceDate(LocalDate.parse(rmRequest.getInvoiceDate()));
        }

        rmDetails.setSubPoNumber(rmRequest.getSubPoNumber());
        if (rmRequest.getSubPoDate() != null) {
            rmDetails.setSubPoDate(LocalDate.parse(rmRequest.getSubPoDate()));
        }

        rmDetails.setSubPoQty(rmRequest.getSubPoQty());
        rmDetails.setTotalOfferedQtyMt(toBigDecimal(rmRequest.getTotalOfferedQtyMt()));
        rmDetails.setOfferedQtyErc(rmRequest.getOfferedQtyErc());
        rmDetails.setUnitOfMeasurement(rmRequest.getUnitOfMeasurement());

        rmDetails.setRateOfMaterial(toBigDecimal(rmRequest.getRateOfMaterial()));
        rmDetails.setRateOfGst(toBigDecimal(rmRequest.getRateOfGst()));
        rmDetails.setBaseValuePo(toBigDecimal(rmRequest.getBaseValuePo()));
        rmDetails.setTotalPo(toBigDecimal(rmRequest.getTotalPo()));

        rmDetails.setCreatedAt(LocalDateTime.now());
        rmDetails.setUpdatedAt(LocalDateTime.now());

        // ================== HEAT QUANTITIES ==================
        List<RmHeatQuantity> heatEntities = new ArrayList<>();

        if (rmRequest.getHeatQuantities() != null) {
            for (RmHeatQuantityRequestDto heatReq : rmRequest.getHeatQuantities()) {

                RmHeatQuantity heat = new RmHeatQuantity();
                heat.setRmInspectionDetails(rmDetails);

                heat.setHeatNumber(heatReq.getHeatNumber());
                heat.setManufacturer(heatReq.getManufacturer());
                heat.setOfferedQty(toBigDecimal(heatReq.getOfferedQty()));

                heat.setTcNumber(heatReq.getTcNumber());
                if (heatReq.getTcDate() != null) {
                    heat.setTcDate(LocalDate.parse(heatReq.getTcDate()));
                }

                heat.setTcQuantity(toBigDecimal(heatReq.getTcQuantity()));
                heat.setQtyLeft(toBigDecimal(heatReq.getQtyLeft()));
                heat.setQtyAccepted(toBigDecimal(heatReq.getQtyAccepted()));
                heat.setQtyRejected(toBigDecimal(heatReq.getQtyRejected()));
                heat.setRejectionReason(heatReq.getRejectionReason());

                heat.setCreatedAt(LocalDateTime.now());
                heat.setUpdatedAt(LocalDateTime.now());

                heatEntities.add(heat);

                // ================== UPDATE INVENTORY ==================
                // Update inventory offered quantity for this heat/TC combination
                // This runs in a separate transaction (REQUIRES_NEW) to avoid rollback issues
                try {
                    if (heatReq.getHeatNumber() != null && heatReq.getTcNumber() != null && heatReq.getOfferedQty() != null) {
                        logger.info("Updating inventory for Heat: {}, TC: {}, Offered: {}",
                                heatReq.getHeatNumber(), heatReq.getTcNumber(), heatReq.getOfferedQty());

                        inventoryEntryService.updateOfferedQuantity(
                                heatReq.getHeatNumber(),
                                heatReq.getTcNumber(),
                                toBigDecimal(heatReq.getOfferedQty())
                        );

                        logger.info("✅ Inventory updated successfully for Heat: {}", heatReq.getHeatNumber());
                    }
                } catch (Exception e) {
                    // Log error but don't fail the inspection call creation
                    // Inventory update runs in separate transaction, so this won't affect main transaction
                    logger.warn("⚠️ Failed to update inventory for Heat: {}, TC: {}. Error: {}",
                            heatReq.getHeatNumber(), heatReq.getTcNumber(), e.getMessage());
                    logger.warn("⚠️ This is expected if inventory entry doesn't exist yet. Inspection call will still be created.");
                }
            }
        }

        rmDetails.setHeatQuantities(heatEntities);

        // ================== CHEMICAL ANALYSIS ==================
        List<RmChemicalAnalysis> chemEntities = new ArrayList<>();

        if (rmRequest.getChemicalAnalysis() != null) {
            for (RmChemicalAnalysisRequestDto chemReq : rmRequest.getChemicalAnalysis()) {

                RmChemicalAnalysis chem = new RmChemicalAnalysis();
                chem.setRmInspectionDetails(rmDetails);

                chem.setHeatNumber(chemReq.getHeatNumber());
                chem.setCarbon(toBigDecimal(chemReq.getCarbon()));
                chem.setManganese(toBigDecimal(chemReq.getManganese()));
                chem.setSilicon(toBigDecimal(chemReq.getSilicon()));
                chem.setSulphur(toBigDecimal(chemReq.getSulphur()));
                chem.setPhosphorus(toBigDecimal(chemReq.getPhosphorus()));
                chem.setChromium(toBigDecimal(chemReq.getChromium()));

                chem.setCreatedAt(LocalDateTime.now());
                chem.setUpdatedAt(LocalDateTime.now());

                chemEntities.add(chem);
            }
        }

        rmDetails.setChemicalAnalysisList(chemEntities);


        return inspectionCallRepository.save(inspectionCall);
    }

    private BigDecimal toBigDecimal(Double value) {
        return value != null ? BigDecimal.valueOf(value) : null;
    }
}

