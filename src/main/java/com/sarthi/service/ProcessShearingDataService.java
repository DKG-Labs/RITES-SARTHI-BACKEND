package com.sarthi.service;

import com.sarthi.dto.processmaterial.ProcessShearingDataDTO;
import java.util.List;

/**
 * Service interface for Process Material Shearing Data operations (8-Hour Grid).
 */
public interface ProcessShearingDataService {

    List<ProcessShearingDataDTO> getByInspectionCallNo(String inspectionCallNo);

    List<ProcessShearingDataDTO> getByCallNoPoNoLineNo(String inspectionCallNo, String poNo, String lineNo);

    List<ProcessShearingDataDTO> getByCallNoAndShift(String inspectionCallNo, String shift);

    ProcessShearingDataDTO save(ProcessShearingDataDTO dto);

    List<ProcessShearingDataDTO> saveAll(List<ProcessShearingDataDTO> dtos);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

