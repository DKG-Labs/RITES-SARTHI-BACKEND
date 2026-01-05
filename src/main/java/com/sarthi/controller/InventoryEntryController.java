package com.sarthi.controller;

import com.sarthi.dto.InventoryEntryRequestDto;
import com.sarthi.dto.InventoryEntryResponseDto;
import com.sarthi.service.InventoryEntryService;
import com.sarthi.util.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for inventory entry operations
 */
@RestController
@RequestMapping("/api/vendor/inventory")
@CrossOrigin(origins = "*")
public class InventoryEntryController {

    private static final Logger logger = LoggerFactory.getLogger(InventoryEntryController.class);

    @Autowired
    private InventoryEntryService inventoryEntryService;

    /**
     * Create a new inventory entry
     * POST /api/vendor/inventory/entries
     */
    @PostMapping("/entries")
    public ResponseEntity<Object> createInventoryEntry(@RequestBody InventoryEntryRequestDto requestDto) {
        logger.info("Received request to create inventory entry for vendor: {}", requestDto.getVendorCode());

        try {
            InventoryEntryResponseDto response = inventoryEntryService.createInventoryEntry(requestDto);
            logger.info("Inventory entry created successfully with ID: {}", response.getId());
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.CREATED);

        } catch (Exception e) {
            logger.error("Error creating inventory entry: {}", e.getMessage(), e);
            return new ResponseEntity<>(
                ResponseBuilder.getSuccessResponse(null),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    /**
     * Get all inventory entries for a vendor
     * GET /api/vendor/inventory/entries/{vendorCode}
     */
    @GetMapping("/entries/{vendorCode}")
    public ResponseEntity<Object> getInventoryEntries(@PathVariable String vendorCode) {
        logger.info("Received request to fetch inventory entries for vendor: {}", vendorCode);

        try {
            List<InventoryEntryResponseDto> entries = inventoryEntryService.getInventoryEntriesByVendor(vendorCode);
            logger.info("Found {} inventory entries for vendor: {}", entries.size(), vendorCode);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(entries), HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Error fetching inventory entries: {}", e.getMessage(), e);
            return new ResponseEntity<>(
                ResponseBuilder.getSuccessResponse(null),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    /**
     * Get inventory entry by ID
     * GET /api/vendor/inventory/entries/detail/{id}
     */
    @GetMapping("/entries/detail/{id}")
    public ResponseEntity<Object> getInventoryEntryById(@PathVariable Long id) {
        logger.info("Received request to fetch inventory entry by ID: {}", id);

        try {
            InventoryEntryResponseDto entry = inventoryEntryService.getInventoryEntryById(id);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(entry), HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Error fetching inventory entry: {}", e.getMessage(), e);
            return new ResponseEntity<>(
                ResponseBuilder.getSuccessResponse(null),
                HttpStatus.NOT_FOUND
            );
        }
    }

    /**
     * Update inventory entry status
     * PUT /api/vendor/inventory/entries/{id}/status
     */
    @PutMapping("/entries/{id}/status")
    public ResponseEntity<Object> updateInventoryStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        logger.info("Received request to update inventory entry {} status to: {}", id, status);

        try {
            InventoryEntryResponseDto entry = inventoryEntryService.updateInventoryStatus(id, status);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(entry), HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Error updating inventory status: {}", e.getMessage(), e);
            return new ResponseEntity<>(
                ResponseBuilder.getSuccessResponse(null),
                HttpStatus.BAD_REQUEST
            );
        }
    }

    /**
     * Get inventory entry by heat number and TC number combination
     * GET /api/vendor/inventory/entries/heat/{heatNumber}/tc/{tcNumber}
     */
    @GetMapping("/entries/heat/{heatNumber}/tc/{tcNumber}")
    public ResponseEntity<Object> getInventoryEntryByHeatAndTc(
            @PathVariable String heatNumber,
            @PathVariable String tcNumber) {
        logger.info("Received request to fetch inventory entry by heat: {} and TC: {}", heatNumber, tcNumber);

        try {
            InventoryEntryResponseDto entry = inventoryEntryService.getInventoryEntryByHeatAndTc(heatNumber, tcNumber);
            if (entry == null) {
                logger.warn("No inventory entry found for heat: {} and TC: {}", heatNumber, tcNumber);
                return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(null), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(entry), HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Error fetching inventory entry: {}", e.getMessage(), e);
            return new ResponseEntity<>(
                ResponseBuilder.getSuccessResponse(null),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}

