package com.sarthi.service.Impl;

import com.sarthi.dto.processmaterial.ProcessCalibrationDocumentsDTO;
import com.sarthi.entity.processmaterial.ProcessCalibrationDocuments;
import com.sarthi.repository.processmaterial.ProcessCalibrationDocumentsRepository;
import com.sarthi.service.ProcessCalibrationDocumentsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessCalibrationDocumentsServiceImpl implements ProcessCalibrationDocumentsService {

    @Autowired
    private ProcessCalibrationDocumentsRepository repository;

    @Override
    public List<ProcessCalibrationDocumentsDTO> getByInspectionCallNo(String inspectionCallNo) {
        return repository.findByInspectionCallNo(inspectionCallNo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProcessCalibrationDocumentsDTO> getByCallNoPoNoLineNo(
            String inspectionCallNo, String poNo, String lineNo) {
        return repository.findByInspectionCallNoAndPoNoAndLineNo(inspectionCallNo, poNo, lineNo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProcessCalibrationDocumentsDTO save(ProcessCalibrationDocumentsDTO dto) {
        ProcessCalibrationDocuments entity = toEntity(dto);
        ProcessCalibrationDocuments saved = repository.save(entity);
        return toDTO(saved);
    }

    @Override
    @Transactional
    public List<ProcessCalibrationDocumentsDTO> saveAll(List<ProcessCalibrationDocumentsDTO> dtos) {
        List<ProcessCalibrationDocuments> entities = dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
        List<ProcessCalibrationDocuments> saved = repository.saveAll(entities);
        return saved.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteByInspectionCallNo(String inspectionCallNo) {
        repository.deleteByInspectionCallNo(inspectionCallNo);
    }

    private ProcessCalibrationDocumentsDTO toDTO(ProcessCalibrationDocuments entity) {
        ProcessCalibrationDocumentsDTO dto = new ProcessCalibrationDocumentsDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    private ProcessCalibrationDocuments toEntity(ProcessCalibrationDocumentsDTO dto) {
        ProcessCalibrationDocuments entity = new ProcessCalibrationDocuments();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}

