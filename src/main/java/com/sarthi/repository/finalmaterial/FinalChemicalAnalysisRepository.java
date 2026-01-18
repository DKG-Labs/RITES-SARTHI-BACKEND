package com.sarthi.repository.finalmaterial;

import com.sarthi.entity.finalmaterial.FinalChemicalAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Final Inspection - Chemical Analysis
 */
@Repository
public interface FinalChemicalAnalysisRepository extends JpaRepository<FinalChemicalAnalysis, Long> {

    /**
     * Find all chemical analysis data by inspection call number
     */
    List<FinalChemicalAnalysis> findByInspectionCallNo(String inspectionCallNo);

    /**
     * Find chemical analysis data by inspection call number and lot number
     */
    List<FinalChemicalAnalysis> findByInspectionCallNoAndLotNo(String inspectionCallNo, String lotNo);

    /**
     * Find chemical analysis data by inspection call number and heat number
     */
    List<FinalChemicalAnalysis> findByInspectionCallNoAndHeatNo(String inspectionCallNo, String heatNo);

    /**
     * Find chemical analysis data by inspection call number, lot number and heat number
     */
    List<FinalChemicalAnalysis> findByInspectionCallNoAndLotNoAndHeatNo(
            String inspectionCallNo, String lotNo, String heatNo);
}

