package com.sarthi.repository;

import com.sarthi.entity.InventoryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for InventoryEntry entity.
 * Provides CRUD operations and custom query methods.
 */
@Repository
public interface InventoryEntryRepository extends JpaRepository<InventoryEntry, Long> {

    /**
     * Find all inventory entries by vendor code
     * @param vendorCode The vendor code to search for
     * @return List of inventory entries for the vendor
     */
    List<InventoryEntry> findByVendorCodeOrderByCreatedDateDesc(String vendorCode);

    /**
     * Find inventory entries by vendor code and status
     * @param vendorCode The vendor code
     * @param status The inventory status
     * @return List of inventory entries matching the criteria
     */
    List<InventoryEntry> findByVendorCodeAndStatus(String vendorCode, InventoryEntry.InventoryStatus status);

    /**
     * Find inventory entries by heat number
     * @param heatNumber The heat/batch/lot number
     * @return List of inventory entries with the heat number
     */
    List<InventoryEntry> findByHeatNumber(String heatNumber);

    /**
     * Find inventory entries by sub PO number
     * @param subPoNumber The sub PO number
     * @return List of inventory entries with the sub PO number
     */
    List<InventoryEntry> findBySubPoNumber(String subPoNumber);

    /**
     * Check if an entry exists with the given heat number and vendor code
     * @param heatNumber The heat number
     * @param vendorCode The vendor code
     * @return true if exists, false otherwise
     */
    boolean existsByHeatNumberAndVendorCode(String heatNumber, String vendorCode);
}

