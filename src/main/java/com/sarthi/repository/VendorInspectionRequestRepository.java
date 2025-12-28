package com.sarthi.repository;

import com.sarthi.entity.VendorInspectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for VendorInspectionRequest entity.
 * Provides data access methods for inspection request operations.
 */
@Repository
public interface VendorInspectionRequestRepository extends JpaRepository<VendorInspectionRequest, Long> {

    /**
     * Find inspection request by PO number
     */
    Optional<VendorInspectionRequest> findByPoNo(String poNo);

    /**
     * Find inspection request by PO serial number
     */
    Optional<VendorInspectionRequest> findByPoSerialNo(String poSerialNo);

    /**
     * Find all inspection requests by status
     */
    List<VendorInspectionRequest> findByStatus(String status);

    /**
     * Find all inspection requests by type of call
     */
    List<VendorInspectionRequest> findByTypeOfCall(String typeOfCall);

    /**
     * Find all inspection requests by company ID
     */
    List<VendorInspectionRequest> findByCompanyId(Integer companyId);

    /**
     * Find all inspection requests by unit ID
     */
    List<VendorInspectionRequest> findByUnitId(Integer unitId);

    /**
     * Find inspection requests by status ordered by created date descending
     */
    List<VendorInspectionRequest> findByStatusOrderByCreatedAtDesc(String status);

    /**
     * Find all inspection requests ordered by created date descending
     */
    List<VendorInspectionRequest> findAllByOrderByCreatedAtDesc();

    /**
     * Check if inspection request exists by PO serial number
     */
    boolean existsByPoSerialNo(String poSerialNo);

    /**
     * Find inspection requests by company ID and status
     */
    @Query("SELECT v FROM VendorInspectionRequest v WHERE v.companyId = :companyId AND v.status = :status ORDER BY v.createdAt DESC")
    List<VendorInspectionRequest> findByCompanyIdAndStatus(@Param("companyId") Integer companyId, @Param("status") String status);
}

