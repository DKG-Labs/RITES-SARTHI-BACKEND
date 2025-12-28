package com.sarthi.service;

import com.sarthi.dto.processmaterial.ProcessQuenchingDataDTO;
import java.util.List;

/**
 * Service interface for Process Material Quenching Data operations (8-Hour Grid).
 */
public interface ProcessQuenchingDataService {

    List<ProcessQuenchingDataDTO> getByInspectionCallNo(String inspectionCallNo);

    List<ProcessQuenchingDataDTO> getByCallNoPoNoLineNo(String inspectionCallNo, String poNo, String lineNo);

    List<ProcessQuenchingDataDTO> getByCallNoAndShift(String inspectionCallNo, String shift);

    ProcessQuenchingDataDTO save(ProcessQuenchingDataDTO dto);

    List<ProcessQuenchingDataDTO> saveAll(List<ProcessQuenchingDataDTO> dtos);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

