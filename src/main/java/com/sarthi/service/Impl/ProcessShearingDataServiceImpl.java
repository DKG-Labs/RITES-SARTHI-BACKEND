package com.sarthi.service.Impl;

import com.sarthi.dto.processmaterial.ProcessShearingDataDTO;
import com.sarthi.entity.processmaterial.ProcessShearingData;
import com.sarthi.repository.processmaterial.ProcessShearingDataRepository;
import com.sarthi.service.ProcessShearingDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessShearingDataServiceImpl implements ProcessShearingDataService {

    @Autowired
    private ProcessShearingDataRepository repository;

    @Override
    public List<ProcessShearingDataDTO> getByInspectionCallNo(String inspectionCallNo) {
        return repository.findByInspectionCallNo(inspectionCallNo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProcessShearingDataDTO> getByCallNoPoNoLineNo(
            String inspectionCallNo, String poNo, String lineNo) {
        return repository.findByInspectionCallNoAndPoNoAndLineNo(inspectionCallNo, poNo, lineNo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProcessShearingDataDTO> getByCallNoAndShift(String inspectionCallNo, String shift) {
        return repository.findByInspectionCallNoAndShift(inspectionCallNo, shift)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProcessShearingDataDTO save(ProcessShearingDataDTO dto) {
        ProcessShearingData entity = toEntity(dto);
        ProcessShearingData saved = repository.save(entity);
        return toDTO(saved);
    }

    @Override
    @Transactional
    public List<ProcessShearingDataDTO> saveAll(List<ProcessShearingDataDTO> dtos) {
        List<ProcessShearingData> entities = dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
        List<ProcessShearingData> saved = repository.saveAll(entities);
        return saved.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteByInspectionCallNo(String inspectionCallNo) {
        repository.deleteByInspectionCallNo(inspectionCallNo);
    }

    private ProcessShearingDataDTO toDTO(ProcessShearingData entity) {
        ProcessShearingDataDTO dto = new ProcessShearingDataDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    private ProcessShearingData toEntity(ProcessShearingDataDTO dto) {
        ProcessShearingData entity = new ProcessShearingData();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}

