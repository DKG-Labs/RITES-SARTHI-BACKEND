package com.sarthi.service.Impl;

import com.sarthi.dto.processmaterial.ProcessTemperingDataDTO;
import com.sarthi.entity.processmaterial.ProcessTemperingData;
import com.sarthi.repository.processmaterial.ProcessTemperingDataRepository;
import com.sarthi.service.ProcessTemperingDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessTemperingDataServiceImpl implements ProcessTemperingDataService {

    @Autowired
    private ProcessTemperingDataRepository repository;

    @Override
    public List<ProcessTemperingDataDTO> getByInspectionCallNo(String inspectionCallNo) {
        return repository.findByInspectionCallNo(inspectionCallNo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProcessTemperingDataDTO> getByCallNoPoNoLineNo(
            String inspectionCallNo, String poNo, String lineNo) {
        return repository.findByInspectionCallNoAndPoNoAndLineNo(inspectionCallNo, poNo, lineNo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProcessTemperingDataDTO> getByCallNoAndShift(String inspectionCallNo, String shift) {
        return repository.findByInspectionCallNoAndShift(inspectionCallNo, shift)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProcessTemperingDataDTO save(ProcessTemperingDataDTO dto) {
        ProcessTemperingData entity = toEntity(dto);
        ProcessTemperingData saved = repository.save(entity);
        return toDTO(saved);
    }

    @Override
    @Transactional
    public List<ProcessTemperingDataDTO> saveAll(List<ProcessTemperingDataDTO> dtos) {
        List<ProcessTemperingData> entities = dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
        List<ProcessTemperingData> saved = repository.saveAll(entities);
        return saved.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteByInspectionCallNo(String inspectionCallNo) {
        repository.deleteByInspectionCallNo(inspectionCallNo);
    }

    private ProcessTemperingDataDTO toDTO(ProcessTemperingData entity) {
        ProcessTemperingDataDTO dto = new ProcessTemperingDataDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    private ProcessTemperingData toEntity(ProcessTemperingDataDTO dto) {
        ProcessTemperingData entity = new ProcessTemperingData();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}

