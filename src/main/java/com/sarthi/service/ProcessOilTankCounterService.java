package com.sarthi.service;

import com.sarthi.dto.processmaterial.ProcessOilTankCounterDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for Process Material Oil Tank Counter operations.
 */
public interface ProcessOilTankCounterService {

    List<ProcessOilTankCounterDTO> getByInspectionCallNo(String inspectionCallNo);

    Optional<ProcessOilTankCounterDTO> getByCallNoPoNoLineNo(String inspectionCallNo, String poNo, String lineNo);

    ProcessOilTankCounterDTO save(ProcessOilTankCounterDTO dto);

    ProcessOilTankCounterDTO incrementCounter(String inspectionCallNo, String poNo, String lineNo);

    ProcessOilTankCounterDTO markCleaningDone(String inspectionCallNo, String poNo, String lineNo);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

