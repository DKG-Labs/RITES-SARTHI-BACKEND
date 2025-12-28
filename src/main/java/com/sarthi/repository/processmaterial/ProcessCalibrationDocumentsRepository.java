package com.sarthi.repository.processmaterial;

import com.sarthi.entity.processmaterial.ProcessCalibrationDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessCalibrationDocumentsRepository extends JpaRepository<ProcessCalibrationDocuments, Long> {

    List<ProcessCalibrationDocuments> findByInspectionCallNo(String inspectionCallNo);

    List<ProcessCalibrationDocuments> findByPoNo(String poNo);

    List<ProcessCalibrationDocuments> findByInspectionCallNoAndPoNoAndLineNo(
            String inspectionCallNo, String poNo, String lineNo);

    Optional<ProcessCalibrationDocuments> findByInspectionCallNoAndInstrumentId(
            String inspectionCallNo, String instrumentId);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

