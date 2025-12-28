package com.sarthi.service;

import com.sarthi.dto.processmaterial.ProcessCalibrationDocumentsDTO;
import java.util.List;

/**
 * Service interface for Process Material Calibration Documents operations.
 */
public interface ProcessCalibrationDocumentsService {

    List<ProcessCalibrationDocumentsDTO> getByInspectionCallNo(String inspectionCallNo);

    List<ProcessCalibrationDocumentsDTO> getByCallNoPoNoLineNo(String inspectionCallNo, String poNo, String lineNo);

    ProcessCalibrationDocumentsDTO save(ProcessCalibrationDocumentsDTO dto);

    List<ProcessCalibrationDocumentsDTO> saveAll(List<ProcessCalibrationDocumentsDTO> dtos);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

