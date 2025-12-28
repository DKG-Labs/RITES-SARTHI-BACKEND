package com.sarthi.service.Impl;

import com.sarthi.dto.processmaterial.ProcessForgingDataDTO;
import com.sarthi.entity.processmaterial.ProcessForgingData;
import com.sarthi.repository.processmaterial.ProcessForgingDataRepository;
import com.sarthi.service.ProcessForgingDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessForgingDataServiceImpl implements ProcessForgingDataService {

    @Autowired
    private ProcessForgingDataRepository repository;

    @Override
    public List<ProcessForgingDataDTO> getByInspectionCallNo(String inspectionCallNo) {
        return repository.findByInspectionCallNo(inspectionCallNo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProcessForgingDataDTO> getByCallNoPoNoLineNo(
            String inspectionCallNo, String poNo, String lineNo) {
        return repository.findByInspectionCallNoAndPoNoAndLineNo(inspectionCallNo, poNo, lineNo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProcessForgingDataDTO> getByCallNoAndShift(String inspectionCallNo, String shift) {
        return repository.findByInspectionCallNoAndShift(inspectionCallNo, shift)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProcessForgingDataDTO save(ProcessForgingDataDTO dto) {
        ProcessForgingData entity = toEntity(dto);
        ProcessForgingData saved = repository.save(entity);
        return toDTO(saved);
    }

    @Override
    @Transactional
    public List<ProcessForgingDataDTO> saveAll(List<ProcessForgingDataDTO> dtos) {
        List<ProcessForgingData> entities = dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
        List<ProcessForgingData> saved = repository.saveAll(entities);
        return saved.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteByInspectionCallNo(String inspectionCallNo) {
        repository.deleteByInspectionCallNo(inspectionCallNo);
    }

    private ProcessForgingDataDTO toDTO(ProcessForgingData entity) {
        ProcessForgingDataDTO dto = new ProcessForgingDataDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    private ProcessForgingData toEntity(ProcessForgingDataDTO dto) {
        ProcessForgingData entity = new ProcessForgingData();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}

