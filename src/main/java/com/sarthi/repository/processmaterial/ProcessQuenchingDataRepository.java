package com.sarthi.repository.processmaterial;

import com.sarthi.entity.processmaterial.ProcessQuenchingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessQuenchingDataRepository extends JpaRepository<ProcessQuenchingData, Long> {

    List<ProcessQuenchingData> findByInspectionCallNo(String inspectionCallNo);

    List<ProcessQuenchingData> findByInspectionCallNoAndPoNoAndLineNo(
            String inspectionCallNo, String poNo, String lineNo);

    List<ProcessQuenchingData> findByInspectionCallNoAndShift(String inspectionCallNo, String shift);

    Optional<ProcessQuenchingData> findByInspectionCallNoAndShiftAndHourIndex(
            String inspectionCallNo, String shift, Integer hourIndex);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

