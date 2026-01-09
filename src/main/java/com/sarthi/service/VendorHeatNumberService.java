package com.sarthi.service;

import com.sarthi.dto.AvailableHeatNumberDto;

import java.util.List;

/**
 * Service interface for vendor heat number operations.
 * Provides methods to fetch heat numbers with inventory validation.
 *
 * IMPORTANT: All heat numbers (available + exhausted) are retained in the database
 * for audit trail, compliance, and historical tracking. The filtering of available
 * vs exhausted heat numbers happens in the service layer, not at the database level.
 */
public interface VendorHeatNumberService {

    /**
     * Get available heat numbers for a vendor.
     *
     * This method:
     * 1. Fetches ALL heat numbers from database (including exhausted ones)
     * 2. Marks each heat number as available/unavailable based on:
     *    - Remaining quantity (tcQtyRemaining > 0)
     *    - Inventory status (FRESH_PO = available)
     * 3. Filters and returns only available heat numbers
     *
     * Exhausted heat numbers remain in the database but are filtered out from the response.
     * This ensures data integrity while providing clean data for vendor dropdowns.
     *
     * @param vendorCode The vendor code
     * @return List of available heat numbers with remaining stock (isAvailable = true)
     */
    List<AvailableHeatNumberDto> getAvailableHeatNumbers(String vendorCode);

    /**
     * Get all heat numbers for a vendor (including exhausted ones).
     *
     * This method:
     * 1. Fetches ALL heat numbers from database
     * 2. Marks each heat number as available/unavailable
     * 3. Returns complete list without filtering
     *
     * Used for:
     * - Reporting and analytics
     * - Historical data viewing
     * - Audit trail and compliance
     * - Inventory management dashboards
     *
     * Each DTO will have isAvailable field set to indicate current status.
     *
     * @param vendorCode The vendor code
     * @return List of all heat numbers (available + exhausted) with availability status
     */
    List<AvailableHeatNumberDto> getAllHeatNumbers(String vendorCode);

    /**
     * Check if a specific heat number has available inventory.
     *
     * Validates availability based on:
     * - Remaining quantity > 0
     * - Inventory status = FRESH_PO
     *
     * @param heatNumber The heat number to check
     * @param tcNumber The TC number
     * @return true if inventory is available, false otherwise
     */
    boolean isHeatNumberAvailable(String heatNumber, String tcNumber);
}

