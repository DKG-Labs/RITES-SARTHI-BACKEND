package com.sarthi.repository.processmaterial;

import com.sarthi.entity.processmaterial.ProcessTemperingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessTemperingDataRepository extends JpaRepository<ProcessTemperingData, Long> {

    List<ProcessTemperingData> findByInspectionCallNo(String inspectionCallNo);

    List<ProcessTemperingData> findByInspectionCallNoAndPoNoAndLineNo(
            String inspectionCallNo, String poNo, String lineNo);

    List<ProcessTemperingData> findByInspectionCallNoAndShift(String inspectionCallNo, String shift);

    Optional<ProcessTemperingData> findByInspectionCallNoAndShiftAndHourIndex(
            String inspectionCallNo, String shift, Integer hourIndex);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

