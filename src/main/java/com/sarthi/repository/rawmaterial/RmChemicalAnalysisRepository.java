package com.sarthi.repository.rawmaterial;

import com.sarthi.entity.rawmaterial.RmChemicalAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for RmChemicalAnalysis entity.
 * Note: This table currently mirrors inspection_calls structure.
 * Basic repository for future chemical analysis implementation.
 */
@Repository
public interface RmChemicalAnalysisRepository extends JpaRepository<RmChemicalAnalysis, Integer> {

    /* ==================== Find by IC Number ==================== */

    Optional<RmChemicalAnalysis> findByIcNumber(String icNumber);

    List<RmChemicalAnalysis> findByIcNumberOrderByCreatedAtDesc(String icNumber);

    /* ==================== Find by Status ==================== */

    List<RmChemicalAnalysis> findByStatusIgnoreCaseOrderByCreatedAtDesc(String status);

    /* ==================== Find by Type of Call ==================== */

    List<RmChemicalAnalysis> findByTypeOfCallOrderByCreatedAtDesc(String typeOfCall);
}

