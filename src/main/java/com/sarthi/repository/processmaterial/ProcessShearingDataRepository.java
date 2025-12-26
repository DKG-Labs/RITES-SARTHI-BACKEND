package com.sarthi.repository.processmaterial;

import com.sarthi.entity.processmaterial.ProcessShearingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessShearingDataRepository extends JpaRepository<ProcessShearingData, Long> {

    List<ProcessShearingData> findByInspectionCallNo(String inspectionCallNo);

    List<ProcessShearingData> findByInspectionCallNoAndPoNoAndLineNo(
            String inspectionCallNo, String poNo, String lineNo);

    List<ProcessShearingData> findByInspectionCallNoAndShift(String inspectionCallNo, String shift);

    Optional<ProcessShearingData> findByInspectionCallNoAndShiftAndHourIndex(
            String inspectionCallNo, String shift, Integer hourIndex);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

