package com.sarthi.repository.finalmaterial;

import com.sarthi.entity.finalmaterial.FinalCalibrationDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Final Inspection - Calibration & Documents
 */
@Repository
public interface FinalCalibrationDocumentsRepository extends JpaRepository<FinalCalibrationDocuments, Long> {

    /**
     * Find all calibration documents by inspection call number
     */
    List<FinalCalibrationDocuments> findByInspectionCallNo(String inspectionCallNo);

    /**
     * Find calibration documents by inspection call number and lot number
     */
    List<FinalCalibrationDocuments> findByInspectionCallNoAndLotNo(String inspectionCallNo, String lotNo);

    /**
     * Find calibration documents by inspection call number and heat number
     */
    List<FinalCalibrationDocuments> findByInspectionCallNoAndHeatNo(String inspectionCallNo, String heatNo);

    /**
     * Find calibration documents by inspection call number, lot number and heat number
     */
    Optional<FinalCalibrationDocuments> findByInspectionCallNoAndLotNoAndHeatNo(
            String inspectionCallNo, String lotNo, String heatNo);
}

