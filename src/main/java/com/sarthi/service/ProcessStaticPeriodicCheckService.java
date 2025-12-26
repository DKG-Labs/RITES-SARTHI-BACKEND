package com.sarthi.service;

import com.sarthi.dto.processmaterial.ProcessStaticPeriodicCheckDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for Process Material Static Periodic Check operations.
 */
public interface ProcessStaticPeriodicCheckService {

    List<ProcessStaticPeriodicCheckDTO> getByInspectionCallNo(String inspectionCallNo);

    Optional<ProcessStaticPeriodicCheckDTO> getByCallNoPoNoLineNo(String inspectionCallNo, String poNo, String lineNo);

    ProcessStaticPeriodicCheckDTO save(ProcessStaticPeriodicCheckDTO dto);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

