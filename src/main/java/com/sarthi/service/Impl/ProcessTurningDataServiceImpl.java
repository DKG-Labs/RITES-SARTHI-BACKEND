package com.sarthi.service.Impl;

import com.sarthi.dto.processmaterial.ProcessTurningDataDTO;
import com.sarthi.entity.processmaterial.ProcessTurningData;
import com.sarthi.repository.processmaterial.ProcessTurningDataRepository;
import com.sarthi.service.ProcessTurningDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessTurningDataServiceImpl implements ProcessTurningDataService {

    @Autowired
    private ProcessTurningDataRepository repository;

    @Override
    public List<ProcessTurningDataDTO> getByInspectionCallNo(String inspectionCallNo) {
        return repository.findByInspectionCallNo(inspectionCallNo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProcessTurningDataDTO> getByCallNoPoNoLineNo(
            String inspectionCallNo, String poNo, String lineNo) {
        return repository.findByInspectionCallNoAndPoNoAndLineNo(inspectionCallNo, poNo, lineNo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProcessTurningDataDTO> getByCallNoAndShift(String inspectionCallNo, String shift) {
        return repository.findByInspectionCallNoAndShift(inspectionCallNo, shift)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProcessTurningDataDTO save(ProcessTurningDataDTO dto) {
        ProcessTurningData entity = toEntity(dto);
        ProcessTurningData saved = repository.save(entity);
        return toDTO(saved);
    }

    @Override
    @Transactional
    public List<ProcessTurningDataDTO> saveAll(List<ProcessTurningDataDTO> dtos) {
        List<ProcessTurningData> entities = dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
        List<ProcessTurningData> saved = repository.saveAll(entities);
        return saved.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteByInspectionCallNo(String inspectionCallNo) {
        repository.deleteByInspectionCallNo(inspectionCallNo);
    }

    private ProcessTurningDataDTO toDTO(ProcessTurningData entity) {
        ProcessTurningDataDTO dto = new ProcessTurningDataDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    private ProcessTurningData toEntity(ProcessTurningDataDTO dto) {
        ProcessTurningData entity = new ProcessTurningData();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}

