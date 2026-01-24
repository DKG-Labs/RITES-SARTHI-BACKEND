package com.sarthi.repository.finalmaterial;

import com.sarthi.entity.finalmaterial.FinalApplicationDeflection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Final Inspection - Deflection Test
 */
@Repository
public interface FinalApplicationDeflectionRepository extends JpaRepository<FinalApplicationDeflection, Long> {

    /**
     * Find all deflection test data by inspection call number
     */
    List<FinalApplicationDeflection> findByInspectionCallNo(String inspectionCallNo);

    /**
     * Find deflection test data by inspection call number and lot number
     */
    List<FinalApplicationDeflection> findByInspectionCallNoAndLotNo(String inspectionCallNo, String lotNo);

    /**
     * Find deflection test data by inspection call number and heat number
     */
    List<FinalApplicationDeflection> findByInspectionCallNoAndHeatNo(String inspectionCallNo, String heatNo);

    /**
     * Find deflection test data by inspection call number, lot number and heat number.
     * This combination is unique per inspection session.
     */
    Optional<FinalApplicationDeflection> findByInspectionCallNoAndLotNoAndHeatNo(
            String inspectionCallNo, String lotNo, String heatNo);
}

