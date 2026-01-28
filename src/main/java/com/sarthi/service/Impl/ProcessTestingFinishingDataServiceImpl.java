package com.sarthi.service.Impl;

import com.sarthi.dto.processmaterial.ProcessTestingFinishingDataDTO;
import com.sarthi.entity.processmaterial.ProcessTestingFinishingData;
import com.sarthi.repository.processmaterial.ProcessTestingFinishingDataRepository;
import com.sarthi.service.ProcessTestingFinishingDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessTestingFinishingDataServiceImpl implements ProcessTestingFinishingDataService {

    @Autowired
    private ProcessTestingFinishingDataRepository repository;

    @Override
    public List<ProcessTestingFinishingDataDTO> getByInspectionCallNo(String inspectionCallNo) {
        return repository.findByInspectionCallNo(inspectionCallNo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProcessTestingFinishingDataDTO> getByCallNoPoNoLineNo(
            String inspectionCallNo, String poNo, String lineNo) {
        return repository.findByInspectionCallNoAndPoNoAndLineNo(inspectionCallNo, poNo, lineNo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProcessTestingFinishingDataDTO> getByCallNoAndShift(String inspectionCallNo, String shift) {
        return repository.findByInspectionCallNoAndShift(inspectionCallNo, shift)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProcessTestingFinishingDataDTO save(ProcessTestingFinishingDataDTO dto) {
        ProcessTestingFinishingData entity = toEntity(dto);
        ProcessTestingFinishingData saved = repository.save(entity);
        return toDTO(saved);
    }

    @Override
    @Transactional
    public List<ProcessTestingFinishingDataDTO> saveAll(List<ProcessTestingFinishingDataDTO> dtos) {
        List<ProcessTestingFinishingData> entities = dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
        List<ProcessTestingFinishingData> saved = repository.saveAll(entities);
        return saved.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteByInspectionCallNo(String inspectionCallNo) {
        repository.deleteByInspectionCallNo(inspectionCallNo);
    }

    private ProcessTestingFinishingDataDTO toDTO(ProcessTestingFinishingData entity) {
        ProcessTestingFinishingDataDTO dto = new ProcessTestingFinishingDataDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    private ProcessTestingFinishingData toEntity(ProcessTestingFinishingDataDTO dto) {
        ProcessTestingFinishingData entity = new ProcessTestingFinishingData();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}

