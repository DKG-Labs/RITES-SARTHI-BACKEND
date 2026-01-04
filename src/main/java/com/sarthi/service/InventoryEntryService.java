package com.sarthi.service;

import com.sarthi.dto.InventoryEntryRequestDto;
import com.sarthi.dto.InventoryEntryResponseDto;

import java.util.List;

/**
 * Service interface for inventory entry operations
 */
public interface InventoryEntryService {

    /**
     * Create a new inventory entry
     * @param requestDto The inventory entry data
     * @return The created inventory entry
     */
    InventoryEntryResponseDto createInventoryEntry(InventoryEntryRequestDto requestDto);

    /**
     * Get all inventory entries for a vendor
     * @param vendorCode The vendor code
     * @return List of inventory entries
     */
    List<InventoryEntryResponseDto> getInventoryEntriesByVendor(String vendorCode);

    /**
     * Get inventory entry by ID
     * @param id The inventory entry ID
     * @return The inventory entry
     */
    InventoryEntryResponseDto getInventoryEntryById(Long id);

    /**
     * Update inventory entry status
     * @param id The inventory entry ID
     * @param status The new status
     * @return The updated inventory entry
     */
    InventoryEntryResponseDto updateInventoryStatus(Long id, String status);
}

