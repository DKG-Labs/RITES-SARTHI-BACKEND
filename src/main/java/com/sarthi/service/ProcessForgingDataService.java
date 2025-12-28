package com.sarthi.service;

import com.sarthi.dto.processmaterial.ProcessForgingDataDTO;
import java.util.List;

/**
 * Service interface for Process Material Forging Data operations (8-Hour Grid).
 */
public interface ProcessForgingDataService {

    List<ProcessForgingDataDTO> getByInspectionCallNo(String inspectionCallNo);

    List<ProcessForgingDataDTO> getByCallNoPoNoLineNo(String inspectionCallNo, String poNo, String lineNo);

    List<ProcessForgingDataDTO> getByCallNoAndShift(String inspectionCallNo, String shift);

    ProcessForgingDataDTO save(ProcessForgingDataDTO dto);

    List<ProcessForgingDataDTO> saveAll(List<ProcessForgingDataDTO> dtos);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

