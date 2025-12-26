package com.sarthi.service;

import com.sarthi.dto.processmaterial.ProcessTurningDataDTO;
import java.util.List;

/**
 * Service interface for Process Material Turning Data operations (8-Hour Grid).
 */
public interface ProcessTurningDataService {

    List<ProcessTurningDataDTO> getByInspectionCallNo(String inspectionCallNo);

    List<ProcessTurningDataDTO> getByCallNoPoNoLineNo(String inspectionCallNo, String poNo, String lineNo);

    List<ProcessTurningDataDTO> getByCallNoAndShift(String inspectionCallNo, String shift);

    ProcessTurningDataDTO save(ProcessTurningDataDTO dto);

    List<ProcessTurningDataDTO> saveAll(List<ProcessTurningDataDTO> dtos);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

