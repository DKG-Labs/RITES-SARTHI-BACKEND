package com.sarthi.repository.rawmaterial;

import com.sarthi.entity.rawmaterial.RmChemicalAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for RmChemicalAnalysis entity.
 * Provides CRUD operations and custom queries for chemical analysis data.
 */
@Repository
public interface RmChemicalAnalysisRepository extends JpaRepository<RmChemicalAnalysis, Long> {

    /* ==================== Find by Heat Number ==================== */

    Optional<RmChemicalAnalysis> findByHeatNumber(String heatNumber);

    List<RmChemicalAnalysis> findByHeatNumberOrderByCreatedAtDesc(String heatNumber);

    /* ==================== Find by RM Detail ID ==================== */

    List<RmChemicalAnalysis> findByRmInspectionDetailsIdOrderByCreatedAtDesc(Integer rmDetailId);

    List<RmChemicalAnalysis> findByRmInspectionDetailsId(Integer rmDetailId);
}

