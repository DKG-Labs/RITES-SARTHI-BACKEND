package com.sarthi.repository.processmaterial;

import com.sarthi.entity.processmaterial.ProcessTurningData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessTurningDataRepository extends JpaRepository<ProcessTurningData, Long> {

    List<ProcessTurningData> findByInspectionCallNo(String inspectionCallNo);

    List<ProcessTurningData> findByInspectionCallNoAndPoNoAndLineNo(
            String inspectionCallNo, String poNo, String lineNo);

    List<ProcessTurningData> findByInspectionCallNoAndShift(String inspectionCallNo, String shift);

    Optional<ProcessTurningData> findByInspectionCallNoAndShiftAndHourIndex(
            String inspectionCallNo, String shift, Integer hourIndex);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

