package com.sarthi.service.Impl;

import com.sarthi.dto.processmaterial.ProcessQuenchingDataDTO;
import com.sarthi.entity.processmaterial.ProcessQuenchingData;
import com.sarthi.repository.processmaterial.ProcessQuenchingDataRepository;
import com.sarthi.service.ProcessQuenchingDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessQuenchingDataServiceImpl implements ProcessQuenchingDataService {

    @Autowired
    private ProcessQuenchingDataRepository repository;

    @Override
    public List<ProcessQuenchingDataDTO> getByInspectionCallNo(String inspectionCallNo) {
        return repository.findByInspectionCallNo(inspectionCallNo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProcessQuenchingDataDTO> getByCallNoPoNoLineNo(
            String inspectionCallNo, String poNo, String lineNo) {
        return repository.findByInspectionCallNoAndPoNoAndLineNo(inspectionCallNo, poNo, lineNo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProcessQuenchingDataDTO> getByCallNoAndShift(String inspectionCallNo, String shift) {
        return repository.findByInspectionCallNoAndShift(inspectionCallNo, shift)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProcessQuenchingDataDTO save(ProcessQuenchingDataDTO dto) {
        ProcessQuenchingData entity = toEntity(dto);
        ProcessQuenchingData saved = repository.save(entity);
        return toDTO(saved);
    }

    @Override
    @Transactional
    public List<ProcessQuenchingDataDTO> saveAll(List<ProcessQuenchingDataDTO> dtos) {
        List<ProcessQuenchingData> entities = dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
        List<ProcessQuenchingData> saved = repository.saveAll(entities);
        return saved.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteByInspectionCallNo(String inspectionCallNo) {
        repository.deleteByInspectionCallNo(inspectionCallNo);
    }

    private ProcessQuenchingDataDTO toDTO(ProcessQuenchingData entity) {
        ProcessQuenchingDataDTO dto = new ProcessQuenchingDataDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    private ProcessQuenchingData toEntity(ProcessQuenchingDataDTO dto) {
        ProcessQuenchingData entity = new ProcessQuenchingData();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}

