package com.sarthi.repository.processmaterial;

import com.sarthi.entity.processmaterial.ProcessOilTankCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessOilTankCounterRepository extends JpaRepository<ProcessOilTankCounter, Long> {

    List<ProcessOilTankCounter> findByInspectionCallNo(String inspectionCallNo);

    Optional<ProcessOilTankCounter> findByInspectionCallNoAndPoNoAndLineNo(
            String inspectionCallNo, String poNo, String lineNo);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

