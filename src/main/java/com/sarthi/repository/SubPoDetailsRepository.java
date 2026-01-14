package com.sarthi.repository;

import com.sarthi.entity.SubPoDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for SubPoDetails (Section C) entity.
 * Provides CRUD operations and custom query methods.
 */
@Repository
public interface SubPoDetailsRepository extends JpaRepository<SubPoDetails, Long> {

    /**
     * Find all SubPoDetails by inspection call number
     */
    List<SubPoDetails> findByInspectionCallNo(String inspectionCallNo);

    /**
     * Check if records exist by inspection call number
     */
    boolean existsByInspectionCallNo(String inspectionCallNo);

    /**
     * Find all SubPoDetails by status
     */
    List<SubPoDetails> findByStatus(String status);

    /**
     * Find all SubPoDetails by sub PO number
     */
    List<SubPoDetails> findBySubPoNo(String subPoNo);

    /**
     * Find all SubPoDetails by heat number
     */
    List<SubPoDetails> findByHeatNo(String heatNo);

    /**
     * Find all SubPoDetails by InspectionCallDetails ID
     */
    List<SubPoDetails> findByInspectionCallDetailsId(Long inspectionCallDetailsId);

    /**
     * Find a SubPoDetails record by inspection call number and sub PO number.
     * Used for upsert behavior when saving batch data.
     */
    java.util.Optional<SubPoDetails> findByInspectionCallNoAndSubPoNo(String inspectionCallNo, String subPoNo);

    /**
     * Delete all SubPoDetails by inspection call number
     */
    void deleteByInspectionCallNo(String inspectionCallNo);
}

