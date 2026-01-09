package com.sarthi.service.Impl;

import com.sarthi.dto.AvailableHeatNumberDto;
import com.sarthi.entity.InventoryEntry;
import com.sarthi.entity.RmHeatTcMapping;
import com.sarthi.repository.InventoryEntryRepository;
import com.sarthi.repository.RmHeatTcMappingRepository;
import com.sarthi.service.VendorHeatNumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for vendor heat number operations.
 * Handles filtering of available heat numbers based on inventory status.
 */
@Service
public class VendorHeatNumberServiceImpl implements VendorHeatNumberService {

    private static final Logger logger = LoggerFactory.getLogger(VendorHeatNumberServiceImpl.class);

    @Autowired
    private RmHeatTcMappingRepository heatTcMappingRepository;

    @Autowired
    private InventoryEntryRepository inventoryEntryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AvailableHeatNumberDto> getAvailableHeatNumbers(String vendorCode) {
        logger.info("Fetching available heat numbers for vendor: {}", vendorCode);

        // Fetch ALL heat TC mappings (including exhausted ones)
        // Database retains all records for audit trail and historical tracking
        List<RmHeatTcMapping> allMappings = heatTcMappingRepository
                .findAllByVendorCode(vendorCode);

        logger.info("Found {} total heat TC mappings for vendor: {} (including exhausted)",
                    allMappings.size(), vendorCode);

        // Convert to DTOs and enrich with inventory data
        // The mapToDto method will set isAvailable based on remaining quantity and status
        List<AvailableHeatNumberDto> allDtos = allMappings.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        // Filter to return only available heat numbers for vendor dropdown
        // Exhausted heat numbers remain in database but are filtered out here
        List<AvailableHeatNumberDto> availableDtos = allDtos.stream()
                .filter(AvailableHeatNumberDto::isAvailable)
                .collect(Collectors.toList());

        logger.info("Returning {} available heat numbers (filtered from {} total) for vendor: {}",
                    availableDtos.size(), allDtos.size(), vendorCode);

        return availableDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AvailableHeatNumberDto> getAllHeatNumbers(String vendorCode) {
        logger.info("Fetching all heat numbers (including exhausted) for vendor: {}", vendorCode);

        // Fetch ALL heat TC mappings from database
        // This includes both available and exhausted heat numbers
        List<RmHeatTcMapping> allMappings = heatTcMappingRepository
                .findAllByVendorCode(vendorCode);

        logger.info("Found {} total heat TC mappings for vendor: {} (available + exhausted)",
                    allMappings.size(), vendorCode);

        // Convert to DTOs with proper availability marking
        // Each DTO will have isAvailable set based on remaining quantity and status
        List<AvailableHeatNumberDto> allDtos = allMappings.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        long availableCount = allDtos.stream().filter(AvailableHeatNumberDto::isAvailable).count();
        long exhaustedCount = allDtos.size() - availableCount;

        logger.info("Returning {} total heat numbers for vendor: {} ({} available, {} exhausted)",
                    allDtos.size(), vendorCode, availableCount, exhaustedCount);

        return allDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isHeatNumberAvailable(String heatNumber, String tcNumber) {
        logger.info("Checking availability for heat number: {}, TC number: {}", heatNumber, tcNumber);

        // Check in RmHeatTcMapping
        List<RmHeatTcMapping> mappings = heatTcMappingRepository.findByHeatNumber(heatNumber);
        
        for (RmHeatTcMapping mapping : mappings) {
            if (tcNumber.equals(mapping.getTcNumber())) {
                boolean available = isQuantityAvailable(mapping.getTcQtyRemaining());
                logger.info("Heat number {} availability: {}", heatNumber, available);
                return available;
            }
        }

        // Also check in InventoryEntry
        Optional<InventoryEntry> inventoryOpt = inventoryEntryRepository
                .findByHeatNumberAndTcNumber(heatNumber, tcNumber);

        if (inventoryOpt.isPresent()) {
            InventoryEntry inventory = inventoryOpt.get();
            boolean available = inventory.getStatus() == InventoryEntry.InventoryStatus.FRESH_PO;
            logger.info("Heat number {} availability from inventory: {}", heatNumber, available);
            return available;
        }

        logger.warn("Heat number {} with TC {} not found in system", heatNumber, tcNumber);
        return false;
    }

    /**
     * Maps RmHeatTcMapping entity to AvailableHeatNumberDto.
     *
     * This method converts database entities to DTOs and determines availability status.
     * The isAvailable field is computed based on:
     * 1. Remaining quantity (tcQtyRemaining > 0)
     * 2. Inventory status (FRESH_PO = available, other statuses = unavailable)
     *
     * IMPORTANT: This method does NOT filter out exhausted heat numbers.
     * All heat numbers (available + exhausted) are converted to DTOs.
     * The filtering happens in the service layer methods based on business requirements.
     *
     * @param mapping The RmHeatTcMapping entity from database
     * @return AvailableHeatNumberDto with isAvailable field properly set
     */
    private AvailableHeatNumberDto mapToDto(RmHeatTcMapping mapping) {
        AvailableHeatNumberDto dto = AvailableHeatNumberDto.builder()
                .id(mapping.getId())
                .heatNumber(mapping.getHeatNumber())
                .tcNumber(mapping.getTcNumber())
                .tcDate(mapping.getTcDate() != null ? mapping.getTcDate().toString() : null)
                .manufacturer(mapping.getManufacturer())
                .tcQtyRemaining(parseQuantity(mapping.getTcQtyRemaining()))
                .offeredQty(parseQuantity(mapping.getOfferedQty()))
                .subPoNumber(mapping.getSubPoNumber())
                .subPoDate(mapping.getSubPoDate() != null ? mapping.getSubPoDate().toString() : null)
                .subPoQty(mapping.getSubPoQty())
                .subPoTotalValue(mapping.getSubPoTotalValue())
                .invoiceNo(mapping.getInvoiceNo())
                .invoiceDate(mapping.getInvoiceDate() != null ? mapping.getInvoiceDate().toString() : null)
                .build();

        // Parse TC quantity
        dto.setTcQuantity(parseQuantity(mapping.getTcQty()));

        // Determine availability based on remaining quantity
        // This will be set to false if quantity <= 0
        dto.setAvailable(isQuantityAvailable(mapping.getTcQtyRemaining()));

        // Enrich with inventory data and cross-validate availability
        // This may override isAvailable to false if inventory status is not FRESH_PO
        enrichWithInventoryData(dto, mapping.getHeatNumber(), mapping.getTcNumber());

        return dto;
    }

    /**
     * Enriches DTO with inventory entry data if available.
     *
     * This method:
     * 1. Fetches inventory details from inventory_entries table
     * 2. Adds material information (raw material, grade specification)
     * 3. Cross-validates availability based on inventory status
     *
     * Availability Logic:
     * - EXHAUSTED status = Unavailable (completely consumed, no remaining quantity)
     * - All other statuses (FRESH_PO, UNDER_INSPECTION, ACCEPTED, REJECTED) = Available
     *
     * IMPORTANT: Only EXHAUSTED entries are filtered out from the dropdown.
     * Entries with UNDER_INSPECTION, ACCEPTED, or REJECTED status remain available
     * for vendor selection, allowing vendors to raise inspection calls for these materials.
     * The record remains in the database for audit trail regardless of status.
     *
     * @param dto The DTO to enrich
     * @param heatNumber The heat number
     * @param tcNumber The TC number
     */
    private void enrichWithInventoryData(AvailableHeatNumberDto dto, String heatNumber, String tcNumber) {
        Optional<InventoryEntry> inventoryOpt = inventoryEntryRepository
                .findByHeatNumberAndTcNumber(heatNumber, tcNumber);

        if (inventoryOpt.isPresent()) {
            InventoryEntry inventory = inventoryOpt.get();

            // Enrich with material information
            dto.setRawMaterial(inventory.getRawMaterial());
            dto.setGradeSpecification(inventory.getGradeSpecification());
            dto.setStatus(inventory.getStatus().name());

            // Cross-validate availability based on inventory status
            // Only EXHAUSTED entries are marked as unavailable
            // All other statuses (FRESH_PO, UNDER_INSPECTION, ACCEPTED, REJECTED) remain available
            if (inventory.getStatus() == InventoryEntry.InventoryStatus.EXHAUSTED) {
                dto.setAvailable(false);
                logger.debug("Heat number {} marked unavailable due to EXHAUSTED status",
                            heatNumber);
            } else {
                logger.debug("Heat number {} is available with status: {}",
                            heatNumber, inventory.getStatus());
            }
        }
    }

    /**
     * Parses quantity string to BigDecimal
     * Handles various formats like "100", "100.5", "100 Kg", etc.
     */
    private BigDecimal parseQuantity(String quantityStr) {
        if (quantityStr == null || quantityStr.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }

        try {
            // Remove common units and whitespace
            String cleaned = quantityStr.trim()
                    .replaceAll("(?i)\\s*(kg|mt|ton|tons|pieces|pcs)\\s*", "")
                    .replaceAll("[^0-9.]", "")
                    .trim();

            if (cleaned.isEmpty()) {
                return BigDecimal.ZERO;
            }

            return new BigDecimal(cleaned);
        } catch (NumberFormatException e) {
            logger.warn("Failed to parse quantity: {}", quantityStr, e);
            return BigDecimal.ZERO;
        }
    }

    /**
     * Checks if a quantity string represents available inventory (> 0)
     */
    private boolean isQuantityAvailable(String quantityStr) {
        BigDecimal quantity = parseQuantity(quantityStr);
        return quantity.compareTo(BigDecimal.ZERO) > 0;
    }
}

