package com.sarthi.service;

import com.sarthi.dto.processmaterial.ProcessFinalCheckDataDTO;
import java.util.List;

/**
 * Service interface for Process Material Final Check Data operations (8-Hour Grid).
 */
public interface ProcessFinalCheckDataService {

    List<ProcessFinalCheckDataDTO> getByInspectionCallNo(String inspectionCallNo);

    List<ProcessFinalCheckDataDTO> getByCallNoPoNoLineNo(String inspectionCallNo, String poNo, String lineNo);

    List<ProcessFinalCheckDataDTO> getByCallNoAndShift(String inspectionCallNo, String shift);

    ProcessFinalCheckDataDTO save(ProcessFinalCheckDataDTO dto);

    List<ProcessFinalCheckDataDTO> saveAll(List<ProcessFinalCheckDataDTO> dtos);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

