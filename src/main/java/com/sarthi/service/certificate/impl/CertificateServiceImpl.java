package com.sarthi.service.certificate.impl;

import com.sarthi.dto.certificate.RawMaterialCertificateDto;
import com.sarthi.entity.InspectionCompleteDetails;
import com.sarthi.entity.MainPoInformation;
import com.sarthi.entity.PoHeader;
import com.sarthi.entity.PoItem;
import com.sarthi.entity.rawmaterial.InspectionCall;
import com.sarthi.entity.rawmaterial.RmHeatQuantity;
import com.sarthi.entity.rawmaterial.RmInspectionDetails;
import com.sarthi.entity.RmHeatFinalResult;
import com.sarthi.repository.InspectionCompleteDetailsRepository;
import com.sarthi.repository.MainPoInformationRepository;
import com.sarthi.repository.PoHeaderRepository;
import com.sarthi.repository.PoItemRepository;
import com.sarthi.repository.RmHeatFinalResultRepository;
import com.sarthi.repository.rawmaterial.InspectionCallRepository;
import com.sarthi.repository.rawmaterial.RmHeatQuantityRepository;
import com.sarthi.repository.rawmaterial.RmInspectionDetailsRepository;
import com.sarthi.service.certificate.CertificateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service implementation for generating Inspection Certificates.
 * Aggregates data from multiple tables to create certificate data.
 */
@Service
@Transactional(readOnly = true)
public class CertificateServiceImpl implements CertificateService {

    private static final Logger logger = LoggerFactory.getLogger(CertificateServiceImpl.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Autowired
    private InspectionCallRepository inspectionCallRepository;

    @Autowired
    private RmInspectionDetailsRepository rmInspectionDetailsRepository;

    @Autowired
    private RmHeatQuantityRepository rmHeatQuantityRepository;

    @Autowired
    private RmHeatFinalResultRepository rmHeatFinalResultRepository;

    @Autowired
    private PoHeaderRepository poHeaderRepository;

    @Autowired
    private PoItemRepository poItemRepository;

    @Autowired
    private MainPoInformationRepository mainPoInformationRepository;

    @Autowired
    private InspectionCompleteDetailsRepository inspectionCompleteDetailsRepository;

    @Override
    public RawMaterialCertificateDto generateRawMaterialCertificate(String icNumber) {
        logger.info("Generating Raw Material Certificate for IC Number: {}", icNumber);

        // 1. Fetch Inspection Call
        InspectionCall inspectionCall = inspectionCallRepository.findByIcNumber(icNumber)
                .orElseThrow(() -> new IllegalArgumentException("Inspection call not found: " + icNumber));

        return buildCertificateDto(inspectionCall);
    }

    @Override
    public RawMaterialCertificateDto generateRawMaterialCertificateById(Long callId) {
        logger.info("Generating Raw Material Certificate for Call ID: {}", callId);

        // 1. Fetch Inspection Call
        InspectionCall inspectionCall = inspectionCallRepository.findById(Math.toIntExact(callId))
                .orElseThrow(() -> new IllegalArgumentException("Inspection call not found with ID: " + callId));

        return buildCertificateDto(inspectionCall);
    }

    /**
     * Build the complete certificate DTO from inspection call data
     */
    private RawMaterialCertificateDto buildCertificateDto(InspectionCall inspectionCall) {
        logger.info("Building certificate DTO for IC: {}", inspectionCall.getIcNumber());

        // 2. Fetch RM Inspection Details
        RmInspectionDetails rmDetails = rmInspectionDetailsRepository.findByIcId(inspectionCall.getId())
                .orElse(null);

        // 3. Fetch Heat Quantities
        List<RmHeatQuantity> heatQuantities = new ArrayList<>();
        if (rmDetails != null) {
            heatQuantities = rmHeatQuantityRepository.findByRmDetailId(Math.toIntExact(rmDetails.getId()));
        }

        // 4. Fetch Heat Final Results
        List<RmHeatFinalResult> heatResults = rmHeatFinalResultRepository
                .findByInspectionCallNo(inspectionCall.getIcNumber());

        // 5. Fetch PO Header and Items
        PoHeader poHeader = poHeaderRepository.findByPoNo(inspectionCall.getPoNo()).orElse(null);
        List<PoItem> poItems = new ArrayList<>();
        if (poHeader != null) {
			poItems = poItemRepository.findByPoHeader_Id(poHeader.getId());
        }

        // 6. Fetch Section A data (Main PO Information) for Bill Paying Officer
        MainPoInformation mainPoInfo = mainPoInformationRepository
                .findByInspectionCallNo(inspectionCall.getIcNumber())
                .orElse(null);

        if (mainPoInfo != null) {
            logger.info("✅ Section A data found for IC: {}", inspectionCall.getIcNumber());
            logger.info("   Bill Paying Officer: {}", mainPoInfo.getBillPayingOfficer());
            logger.info("   Purchasing Authority: {}", mainPoInfo.getPurchasingAuthority());
        } else {
            logger.warn("⚠️ Section A data NOT found for IC: {}", inspectionCall.getIcNumber());
        }

        // 7. Build Certificate DTO
        return RawMaterialCertificateDto.builder()
                .certificateNo(generateCertificateNumber(inspectionCall))
                .certificateDate(formatDate(LocalDate.now()))
                .offeredInstNo(calculateOfferedInstallment(inspectionCall))
                .passedInstNo(calculatePassedInstallment(inspectionCall))
                .contractor(buildContractorInfo(poHeader))
                .manufacturer(buildManufacturerInfo(heatQuantities))
                .placeOfInspection(inspectionCall.getPlaceOfInspection())
                .contractRef(buildContractRef(poHeader))
                .contractorPo(buildContractorPo(rmDetails))
                .billPayingOfficer(buildBillPayingOfficer(mainPoInfo))
                .consigneeRailway(buildConsigneeRailway(poItems))
                .consigneeManufacturer(buildConsigneeManufacturer(poHeader))
                .purchasingAuthority(buildPurchasingAuthority(poHeader, mainPoInfo))
                .description(buildDescription(inspectionCall))
                .drgNo("") // Keep blank
                .specNo("IRS T-31-2025")
                .qapNo("Clause No.4.11.2 & 4.11.3 of Indian Railway Standard Specification for Elastic Rail Clip, IRS T-31-2025")
                .inspectionType("Visual/Physical/Chemical/Metallurgical/Dimensional")
                .chpClause("Clause No.4.11.2 & 4.11.3 of Indian Railway Standard Specification for Elastic Rail Clip, IRS T-31-2025")
                .contractChpReq("Visual, Dimensional, Mechanical & Chemical")
                .detailsOfInspection("Visual, Dimensional, Mechanical & Chemical")
                .result(buildResult(heatResults))
                .qtyCleared(buildQtyCleared(heatResults))
                .qtyRejected(buildQtyRejected(heatResults))
                .remarks(buildRemarks(inspectionCall))
                .dateOfCall(buildDateOfCall(inspectionCall))
                .noOfVisits("") // Keep blank for now
                .dateOfInspection(buildDateOfInspection(heatResults))
                .sealingPattern(buildSealingPattern())
                .sealFacsimile("") // Blank for stamp
                .inspectingEngineer("") // Keep blank for now (DSC signature)
                .heatDetails(buildHeatDetails(heatQuantities, heatResults))
                .build();
    }

    /* ==================== Helper Methods for Building Certificate Fields ==================== */

    /**
     * Generate Certificate Number
     * Fetches from inspection_complete_details table if available
     * Format: {RIO_First_Letter}/{IC_Number}/{IE_Short_Name}
     * Example: N/RM-IC-1767618858167/RAJK
     *
     * Falls back to IC number if not found in inspection_complete_details
     */
    private String generateCertificateNumber(InspectionCall inspectionCall) {
        // Try to fetch from inspection_complete_details table
        return inspectionCompleteDetailsRepository.findByCallNo(inspectionCall.getIcNumber())
                .map(InspectionCompleteDetails::getCertificateNo)
                .orElse(inspectionCall.getIcNumber() != null ? inspectionCall.getIcNumber() : "");
    }

    /**
     * Calculate Offered Installment Number
     * Count of all inspection calls for this PO
     */
    private String calculateOfferedInstallment(InspectionCall inspectionCall) {
        long count = inspectionCallRepository.findByPoNoOrderByCreatedAtDesc(inspectionCall.getPoNo()).size();
        return String.valueOf(count);
    }

    /**
     * Calculate Passed Installment Number
     * Count of accepted ICs for this PO
     */
    private String calculatePassedInstallment(InspectionCall inspectionCall) {
        long count = inspectionCallRepository.findByPoNoOrderByCreatedAtDesc(inspectionCall.getPoNo())
                .stream()
                .filter(ic -> "ACCEPTED".equalsIgnoreCase(ic.getStatus()) || "COMPLETED".equalsIgnoreCase(ic.getStatus()))
                .count();
        return String.valueOf(count);
    }

    /**
     * Build Contractor Information (Vendor Name with Address)
     */
    private String buildContractorInfo(PoHeader poHeader) {
        if (poHeader == null) return "";
		String vendorName = extractVendorName(poHeader.getVendorDetails());
		String vendorAddress = extractVendorAddress(poHeader.getVendorDetails());
		return vendorName + (vendorAddress.isBlank() ? "" : ", " + vendorAddress);
    }

    /**
     * Build Manufacturer Information
     * Name of Manufacturer of Steel Rounds / Supplier of Raw Material along with city
     */
    private String buildManufacturerInfo(List<RmHeatQuantity> heatQuantities) {
        if (heatQuantities.isEmpty()) return "";

        // Get unique manufacturers
        return heatQuantities.stream()
                .map(RmHeatQuantity::getManufacturer)
                .distinct()
                .collect(Collectors.joining(", "));
    }

    /**
     * Build Contract Reference (PO Number & Date + Modification Advise)
     */
    private String buildContractRef(PoHeader poHeader) {
        if (poHeader == null) return "";
		return poHeader.getPoNo() + " dated " + formatDate(poHeader.getPoDate() != null ? poHeader.getPoDate().toLocalDate() : null);
    }

    /**
     * Build Contractor's PO Number & Date
     */
    private String buildContractorPo(RmInspectionDetails rmDetails) {
        if (rmDetails == null) return "";
        return (rmDetails.getSubPoNumber() != null ? rmDetails.getSubPoNumber() : "") +
               " dated " +
               (rmDetails.getSubPoDate() != null ? formatDate(rmDetails.getSubPoDate()) : "");
    }

    /**
     * Build Consignee Railway from PO Items
     */
    private String buildConsigneeRailway(List<PoItem> poItems) {
        if (poItems.isEmpty()) return "";

        // Get consignee from first item (assuming same for all items in PO)
        PoItem firstItem = poItems.get(0);
        return firstItem.getConsigneeDetail() != null ? firstItem.getConsigneeDetail() : "";
    }

    /**
     * Build Consignee Manufacturer (Vendor with complete address)
     */
    private String buildConsigneeManufacturer(PoHeader poHeader) {
        if (poHeader == null) return "";
		String vendorName = extractVendorName(poHeader.getVendorDetails());
		String vendorAddress = extractVendorAddress(poHeader.getVendorDetails());
		return vendorName + (vendorAddress.isBlank() ? "" : ", " + vendorAddress);
    }

	/**
	 * vendorDetails is persisted from CRIS field VENDOR_DETAILS.
	 * Observed format in existing code: "VENDOR NAME-CITY~address~...".
	 */
	private String extractVendorName(String vendorDetails) {
		if (vendorDetails == null || vendorDetails.isBlank()) return "";
		String[] parts = vendorDetails.split("~");
		return parts.length > 0 ? parts[0].trim() : vendorDetails.trim();
	}

	private String extractVendorAddress(String vendorDetails) {
		if (vendorDetails == null || vendorDetails.isBlank()) return "";
		String[] parts = vendorDetails.split("~");
		if (parts.length <= 1) return "";
		// Join remaining segments as a human-readable address/details string
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < parts.length; i++) {
			String p = parts[i] != null ? parts[i].trim() : "";
			if (p.isEmpty()) continue;
			if (sb.length() > 0) sb.append(", ");
			sb.append(p);
		}
		return sb.toString();
	}

    /**
     * Build Description based on ERC Type
     */
    private String buildDescription(InspectionCall inspectionCall) {
        String ercType = inspectionCall.getErcType();
        if (ercType == null) return "";

        switch (ercType.toUpperCase()) {
            case "MK-III":
                return "55Si7 SPRING STEEL ROUND 20.64MM";
            case "MK-V":
                return "55Si7 SPRING STEEL ROUND 23MM";
            case "J TYPE CLIP":
                return "J TYPE CLIP";
            default:
                return ercType;
        }
    }

    /**
     * Build Result based on Heat Final Results
     */
    private String buildResult(List<RmHeatFinalResult> heatResults) {
        if (heatResults.isEmpty()) return "";

        // Check if all heats are accepted
        boolean allAccepted = heatResults.stream()
                .allMatch(hr -> "ACCEPTED".equalsIgnoreCase(hr.getStatus()));

        if (allAccepted) {
            return "CONFIRMING TO THE SPECIFICATION IRS T–31-2025, GRADE 55SI7";
        } else {
            return "NOT CONFIRMING TO THE SPECIFICATION IRS T–31-2025, GRADE 55SI7";
        }
    }

    /**
     * Build Quantity Cleared (Heat No. / Qty (MT) + Total + No. of bundles + ERC calculation)
     */
    private String buildQtyCleared(List<RmHeatFinalResult> heatResults) {
        if (heatResults.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        double totalAccepted = 0.0;

        for (RmHeatFinalResult hr : heatResults) {
            if ("ACCEPTED".equalsIgnoreCase(hr.getStatus())) {
                BigDecimal acceptedQty = hr.getWeightAcceptedMt();
                sb.append(hr.getHeatNo()).append(" / ").append(acceptedQty).append(" MT\n");
                totalAccepted += (acceptedQty != null ? acceptedQty.doubleValue() : 0.0);
            }
        }

        sb.append("Total: ").append(totalAccepted).append(" MT");
        return sb.toString();
    }

    /**
     * Build Quantity Rejected
     */
    private String buildQtyRejected(List<RmHeatFinalResult> heatResults) {
        if (heatResults.isEmpty()) return "Nil";

        StringBuilder sb = new StringBuilder();
        double totalRejected = 0.0;

        for (RmHeatFinalResult hr : heatResults) {
            if ("REJECTED".equalsIgnoreCase(hr.getStatus())) {
                BigDecimal rejectedQty = hr.getWeightRejectedMt();
                sb.append(hr.getHeatNo()).append(" / ").append(rejectedQty).append(" MT\n");
                totalRejected += (rejectedQty != null ? rejectedQty.doubleValue() : 0.0);
            }
        }

        return totalRejected > 0 ? sb.toString() : "Nil";
    }

    /**
     * Build Remarks
     */
    private String buildRemarks(InspectionCall inspectionCall) {
        String ercType = inspectionCall.getErcType();
        if (ercType == null) return "";
        return "LOT FOUND ACCEPTABLE AND CLEARED FOR MANUFACTURING OF ERC " + ercType.toUpperCase();
    }

    /**
     * Build Date of Call (Call Date + Desired Date)
     */
    private String buildDateOfCall(InspectionCall inspectionCall) {
        String callDate = formatDate(inspectionCall.getCreatedAt() != null ?
                inspectionCall.getCreatedAt().toLocalDate() : null);
        String desiredDate = formatDate(inspectionCall.getDesiredInspectionDate());
        return "Call Date: " + callDate + ", Desired Date: " + desiredDate;
    }

    /**
     * Build Sealing Pattern
     */
    private String buildSealingPattern() {
        return "RITES HOLOGRAM FROM SL. NO. W-XXXXXXX TO W-XXXXXXX AFFIXED WITH TAPE ON LEAD SEAL OR ON TAG OF EACH BUNDLE.";
    }

    /**
     * Build Heat Details List
     * Combines data from RmHeatFinalResult (weights, status) and RmHeatQuantity (manufacturer, TC info)
     */
    private List<RawMaterialCertificateDto.HeatDetailDto> buildHeatDetails(
            List<RmHeatQuantity> heatQuantities,
            List<RmHeatFinalResult> heatResults) {

        List<RawMaterialCertificateDto.HeatDetailDto> heatDetails = new ArrayList<>();

        // Create a map of heat number to heat quantity for quick lookup
        Map<String, RmHeatQuantity> heatQuantityMap = heatQuantities.stream()
                .collect(Collectors.toMap(
                        RmHeatQuantity::getHeatNumber,
                        hq -> hq,
                        (existing, replacement) -> existing // Keep first if duplicates
                ));

        for (RmHeatFinalResult hr : heatResults) {
            // Find matching heat quantity by heat number
            RmHeatQuantity hq = heatQuantityMap.get(hr.getHeatNo());

            RawMaterialCertificateDto.HeatDetailDto detail = RawMaterialCertificateDto.HeatDetailDto.builder()
                    .heatNo(hr.getHeatNo())
                    .manufacturer(hq != null ? hq.getManufacturer() : "")
                    .weightOfferedMt(hr.getWeightOfferedMt() != null ? hr.getWeightOfferedMt().toString() : "0")
                    .weightAcceptedMt(hr.getWeightAcceptedMt() != null ? hr.getWeightAcceptedMt().toString() : "0")
                    .weightRejectedMt(hr.getWeightRejectedMt() != null ? hr.getWeightRejectedMt().toString() : "0")
                    .status(hr.getStatus())
                    .tcNo(hq != null ? hq.getTcNumber() : "")
                    .tcDate(hq != null ? formatDate(hq.getTcDate()) : "")
                    .build();
            heatDetails.add(detail);
        }

        return heatDetails;
    }

    /**
     * Build Bill Paying Officer
     * Priority: Section A > Empty
     */
    private String buildBillPayingOfficer(MainPoInformation mainPoInfo) {
        if (mainPoInfo != null && mainPoInfo.getBillPayingOfficer() != null) {
            return mainPoInfo.getBillPayingOfficer();
        }
        return "";
    }

    /**
     * Build Purchasing Authority
     * Priority: PO Header (purchaser_detail + purchaser_code) > Section A > Empty
     * Format: "PURCHASER_DETAIL (PURCHASER_CODE)"
     */
    private String buildPurchasingAuthority(PoHeader poHeader, MainPoInformation mainPoInfo) {
        // Try PO Header first
        if (poHeader != null) {
            String purchaserDetail = poHeader.getPurchaserDetail();
            String purchaserCode = poHeader.getPurchaserCode();

            if (purchaserDetail != null && !purchaserDetail.isEmpty()) {
                if (purchaserCode != null && !purchaserCode.isEmpty()) {
                    return purchaserDetail + " (" + purchaserCode + ")";
                }
                return purchaserDetail;
            }
        }

        // Fallback to Section A
        if (mainPoInfo != null && mainPoInfo.getPurchasingAuthority() != null) {
            return mainPoInfo.getPurchasingAuthority();
        }

        return "";
    }

    /**
     * Build Date of Inspection
     * Use created_at from rm_heat_final_result table (earliest date if multiple heats)
     */
    private String buildDateOfInspection(List<RmHeatFinalResult> heatResults) {
        if (heatResults == null || heatResults.isEmpty()) {
            return "";
        }

        // Find the earliest created_at from heat results
        LocalDate earliestDate = heatResults.stream()
                .map(RmHeatFinalResult::getCreatedAt)
                .filter(dateTime -> dateTime != null)
                .map(LocalDateTime::toLocalDate)
                .min(LocalDate::compareTo)
                .orElse(null);

        return formatDate(earliestDate);
    }

    /**
     * Format LocalDate to dd/MM/yyyy
     */
    private String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : "";
    }
}

