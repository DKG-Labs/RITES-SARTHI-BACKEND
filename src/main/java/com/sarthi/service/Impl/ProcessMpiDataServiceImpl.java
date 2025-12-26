package com.sarthi.service.Impl;

import com.sarthi.dto.processmaterial.ProcessMpiDataDTO;
import com.sarthi.entity.processmaterial.ProcessMpiData;
import com.sarthi.repository.processmaterial.ProcessMpiDataRepository;
import com.sarthi.service.ProcessMpiDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessMpiDataServiceImpl implements ProcessMpiDataService {

    @Autowired
    private ProcessMpiDataRepository repository;

    @Override
    public List<ProcessMpiDataDTO> getByInspectionCallNo(String inspectionCallNo) {
        return repository.findByInspectionCallNo(inspectionCallNo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProcessMpiDataDTO> getByCallNoPoNoLineNo(
            String inspectionCallNo, String poNo, String lineNo) {
        return repository.findByInspectionCallNoAndPoNoAndLineNo(inspectionCallNo, poNo, lineNo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProcessMpiDataDTO> getByCallNoAndShift(String inspectionCallNo, String shift) {
        return repository.findByInspectionCallNoAndShift(inspectionCallNo, shift)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProcessMpiDataDTO save(ProcessMpiDataDTO dto) {
        ProcessMpiData entity = toEntity(dto);
        ProcessMpiData saved = repository.save(entity);
        return toDTO(saved);
    }

    @Override
    @Transactional
    public List<ProcessMpiDataDTO> saveAll(List<ProcessMpiDataDTO> dtos) {
        List<ProcessMpiData> entities = dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
        List<ProcessMpiData> saved = repository.saveAll(entities);
        return saved.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteByInspectionCallNo(String inspectionCallNo) {
        repository.deleteByInspectionCallNo(inspectionCallNo);
    }

    private ProcessMpiDataDTO toDTO(ProcessMpiData entity) {
        ProcessMpiDataDTO dto = new ProcessMpiDataDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    private ProcessMpiData toEntity(ProcessMpiDataDTO dto) {
        ProcessMpiData entity = new ProcessMpiData();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}

