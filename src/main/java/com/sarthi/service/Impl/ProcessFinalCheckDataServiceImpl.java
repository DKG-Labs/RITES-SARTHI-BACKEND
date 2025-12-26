package com.sarthi.service.Impl;

import com.sarthi.dto.processmaterial.ProcessFinalCheckDataDTO;
import com.sarthi.entity.processmaterial.ProcessFinalCheckData;
import com.sarthi.repository.processmaterial.ProcessFinalCheckDataRepository;
import com.sarthi.service.ProcessFinalCheckDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessFinalCheckDataServiceImpl implements ProcessFinalCheckDataService {

    @Autowired
    private ProcessFinalCheckDataRepository repository;

    @Override
    public List<ProcessFinalCheckDataDTO> getByInspectionCallNo(String inspectionCallNo) {
        return repository.findByInspectionCallNo(inspectionCallNo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProcessFinalCheckDataDTO> getByCallNoPoNoLineNo(
            String inspectionCallNo, String poNo, String lineNo) {
        return repository.findByInspectionCallNoAndPoNoAndLineNo(inspectionCallNo, poNo, lineNo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProcessFinalCheckDataDTO> getByCallNoAndShift(String inspectionCallNo, String shift) {
        return repository.findByInspectionCallNoAndShift(inspectionCallNo, shift)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProcessFinalCheckDataDTO save(ProcessFinalCheckDataDTO dto) {
        ProcessFinalCheckData entity = toEntity(dto);
        ProcessFinalCheckData saved = repository.save(entity);
        return toDTO(saved);
    }

    @Override
    @Transactional
    public List<ProcessFinalCheckDataDTO> saveAll(List<ProcessFinalCheckDataDTO> dtos) {
        List<ProcessFinalCheckData> entities = dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
        List<ProcessFinalCheckData> saved = repository.saveAll(entities);
        return saved.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteByInspectionCallNo(String inspectionCallNo) {
        repository.deleteByInspectionCallNo(inspectionCallNo);
    }

    private ProcessFinalCheckDataDTO toDTO(ProcessFinalCheckData entity) {
        ProcessFinalCheckDataDTO dto = new ProcessFinalCheckDataDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    private ProcessFinalCheckData toEntity(ProcessFinalCheckDataDTO dto) {
        ProcessFinalCheckData entity = new ProcessFinalCheckData();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}

