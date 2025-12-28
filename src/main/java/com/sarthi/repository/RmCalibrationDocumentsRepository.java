package com.sarthi.repository;

import com.sarthi.entity.RmCalibrationDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for RmCalibrationDocuments entity.
 */
@Repository
public interface RmCalibrationDocumentsRepository extends JpaRepository<RmCalibrationDocuments, Long> {

    List<RmCalibrationDocuments> findByInspectionCallNo(String inspectionCallNo);

    List<RmCalibrationDocuments> findByInspectionCallNoAndHeatNo(String inspectionCallNo, String heatNo);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

