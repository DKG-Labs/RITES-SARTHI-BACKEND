package com.sarthi.service;

import com.sarthi.dto.processmaterial.ProcessTemperingDataDTO;
import java.util.List;

/**
 * Service interface for Process Material Tempering Data operations (8-Hour Grid).
 */
public interface ProcessTemperingDataService {

    List<ProcessTemperingDataDTO> getByInspectionCallNo(String inspectionCallNo);

    List<ProcessTemperingDataDTO> getByCallNoPoNoLineNo(String inspectionCallNo, String poNo, String lineNo);

    List<ProcessTemperingDataDTO> getByCallNoAndShift(String inspectionCallNo, String shift);

    ProcessTemperingDataDTO save(ProcessTemperingDataDTO dto);

    List<ProcessTemperingDataDTO> saveAll(List<ProcessTemperingDataDTO> dtos);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

