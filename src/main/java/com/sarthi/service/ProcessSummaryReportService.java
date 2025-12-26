package com.sarthi.service;

import com.sarthi.dto.processmaterial.ProcessSummaryReportDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for Process Material Summary Report operations.
 */
public interface ProcessSummaryReportService {

    List<ProcessSummaryReportDTO> getByInspectionCallNo(String inspectionCallNo);

    Optional<ProcessSummaryReportDTO> getByCallNoPoNoLineNo(String inspectionCallNo, String poNo, String lineNo);

    ProcessSummaryReportDTO save(ProcessSummaryReportDTO dto);

    ProcessSummaryReportDTO completeInspection(String inspectionCallNo, String poNo, String lineNo, 
            String ieRemarks, String finalStatus);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

