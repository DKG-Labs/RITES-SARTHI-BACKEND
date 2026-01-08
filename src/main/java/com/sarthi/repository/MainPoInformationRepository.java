package com.sarthi.repository;

import com.sarthi.entity.MainPoInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for MainPoInformation (Section A) entity.
 * Provides CRUD operations and custom query methods.
 */
@Repository
public interface MainPoInformationRepository extends JpaRepository<MainPoInformation, Long> {

    /**
     * Find MainPoInformation by inspection call number
     */
    Optional<MainPoInformation> findByInspectionCallNo(String inspectionCallNo);

    /**
     * Check if record exists by inspection call number
     */
    boolean existsByInspectionCallNo(String inspectionCallNo);

    /**
     * Find all MainPoInformation by PO number
     */
    List<MainPoInformation> findByPoNo(String poNo);

    /**
     * Find all MainPoInformation by status
     */
    List<MainPoInformation> findByStatus(String status);

    /**
     * Find all MainPoInformation by vendor name
     */
    List<MainPoInformation> findByVendorName(String vendorName);
}

