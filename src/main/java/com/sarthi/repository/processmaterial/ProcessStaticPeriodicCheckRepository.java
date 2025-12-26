package com.sarthi.repository.processmaterial;

import com.sarthi.entity.processmaterial.ProcessStaticPeriodicCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessStaticPeriodicCheckRepository extends JpaRepository<ProcessStaticPeriodicCheck, Long> {

    List<ProcessStaticPeriodicCheck> findByInspectionCallNo(String inspectionCallNo);

    Optional<ProcessStaticPeriodicCheck> findByInspectionCallNoAndPoNoAndLineNo(
            String inspectionCallNo, String poNo, String lineNo);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

