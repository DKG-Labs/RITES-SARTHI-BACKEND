package com.sarthi.service;

import com.sarthi.dto.processmaterial.ProcessTestingFinishingDataDTO;
import java.util.List;

/**
 * Service interface for Process Material Testing & Finishing Data operations (8-Hour Grid).
 */
public interface ProcessTestingFinishingDataService {

    List<ProcessTestingFinishingDataDTO> getByInspectionCallNo(String inspectionCallNo);

    List<ProcessTestingFinishingDataDTO> getByCallNoPoNoLineNo(String inspectionCallNo, String poNo, String lineNo);

    List<ProcessTestingFinishingDataDTO> getByCallNoAndShift(String inspectionCallNo, String shift);

    ProcessTestingFinishingDataDTO save(ProcessTestingFinishingDataDTO dto);

    List<ProcessTestingFinishingDataDTO> saveAll(List<ProcessTestingFinishingDataDTO> dtos);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

