package com.sarthi.service;

import com.sarthi.dto.processmaterial.ProcessMpiDataDTO;
import java.util.List;

/**
 * Service interface for Process Material MPI Data operations (8-Hour Grid).
 */
public interface ProcessMpiDataService {

    List<ProcessMpiDataDTO> getByInspectionCallNo(String inspectionCallNo);

    List<ProcessMpiDataDTO> getByCallNoPoNoLineNo(String inspectionCallNo, String poNo, String lineNo);

    List<ProcessMpiDataDTO> getByCallNoAndShift(String inspectionCallNo, String shift);

    ProcessMpiDataDTO save(ProcessMpiDataDTO dto);

    List<ProcessMpiDataDTO> saveAll(List<ProcessMpiDataDTO> dtos);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

