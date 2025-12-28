package com.sarthi.repository.processmaterial;

import com.sarthi.entity.processmaterial.ProcessSummaryReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessSummaryReportRepository extends JpaRepository<ProcessSummaryReport, Long> {

    List<ProcessSummaryReport> findByInspectionCallNo(String inspectionCallNo);

    Optional<ProcessSummaryReport> findByInspectionCallNoAndPoNoAndLineNo(
            String inspectionCallNo, String poNo, String lineNo);

    List<ProcessSummaryReport> findByInspectionCallNoAndHeatNo(String inspectionCallNo, String heatNo);

    List<ProcessSummaryReport> findByFinalStatus(String finalStatus);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

