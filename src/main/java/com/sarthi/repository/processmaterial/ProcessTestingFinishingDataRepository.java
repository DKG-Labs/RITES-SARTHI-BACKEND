package com.sarthi.repository.processmaterial;

import com.sarthi.entity.processmaterial.ProcessTestingFinishingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessTestingFinishingDataRepository extends JpaRepository<ProcessTestingFinishingData, Long> {

    List<ProcessTestingFinishingData> findByInspectionCallNo(String inspectionCallNo);

    List<ProcessTestingFinishingData> findByInspectionCallNoAndPoNoAndLineNo(
            String inspectionCallNo, String poNo, String lineNo);

    List<ProcessTestingFinishingData> findByInspectionCallNoAndShift(String inspectionCallNo, String shift);

    Optional<ProcessTestingFinishingData> findByInspectionCallNoAndShiftAndHourIndex(
            String inspectionCallNo, String shift, Integer hourIndex);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

