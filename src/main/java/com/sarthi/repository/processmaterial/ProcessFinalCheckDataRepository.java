package com.sarthi.repository.processmaterial;

import com.sarthi.entity.processmaterial.ProcessFinalCheckData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessFinalCheckDataRepository extends JpaRepository<ProcessFinalCheckData, Long> {

    List<ProcessFinalCheckData> findByInspectionCallNo(String inspectionCallNo);

    List<ProcessFinalCheckData> findByInspectionCallNoAndPoNoAndLineNo(
            String inspectionCallNo, String poNo, String lineNo);

    List<ProcessFinalCheckData> findByInspectionCallNoAndShift(String inspectionCallNo, String shift);

    Optional<ProcessFinalCheckData> findByInspectionCallNoAndShiftAndHourIndex(
            String inspectionCallNo, String shift, Integer hourIndex);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

