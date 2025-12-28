package com.sarthi.repository.processmaterial;

import com.sarthi.entity.processmaterial.ProcessForgingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessForgingDataRepository extends JpaRepository<ProcessForgingData, Long> {

    List<ProcessForgingData> findByInspectionCallNo(String inspectionCallNo);

    List<ProcessForgingData> findByInspectionCallNoAndPoNoAndLineNo(
            String inspectionCallNo, String poNo, String lineNo);

    List<ProcessForgingData> findByInspectionCallNoAndShift(String inspectionCallNo, String shift);

    Optional<ProcessForgingData> findByInspectionCallNoAndShiftAndHourIndex(
            String inspectionCallNo, String shift, Integer hourIndex);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

