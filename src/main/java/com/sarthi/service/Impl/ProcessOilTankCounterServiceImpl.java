package com.sarthi.service.Impl;

import com.sarthi.dto.processmaterial.ProcessOilTankCounterDTO;
import com.sarthi.entity.processmaterial.ProcessOilTankCounter;
import com.sarthi.repository.processmaterial.ProcessOilTankCounterRepository;
import com.sarthi.service.ProcessOilTankCounterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProcessOilTankCounterServiceImpl implements ProcessOilTankCounterService {

    @Autowired
    private ProcessOilTankCounterRepository repository;

    @Override
    public List<ProcessOilTankCounterDTO> getByInspectionCallNo(String inspectionCallNo) {
        return repository.findByInspectionCallNo(inspectionCallNo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProcessOilTankCounterDTO> getByCallNoPoNoLineNo(
            String inspectionCallNo, String poNo, String lineNo) {
        return repository.findByInspectionCallNoAndPoNoAndLineNo(inspectionCallNo, poNo, lineNo)
                .map(this::toDTO);
    }

    @Override
    @Transactional
    public ProcessOilTankCounterDTO save(ProcessOilTankCounterDTO dto) {
        ProcessOilTankCounter entity = toEntity(dto);
        ProcessOilTankCounter saved = repository.save(entity);
        return toDTO(saved);
    }

    @Override
    @Transactional
    public ProcessOilTankCounterDTO incrementCounter(String inspectionCallNo, String poNo, String lineNo) {
        Optional<ProcessOilTankCounter> optEntity = repository
                .findByInspectionCallNoAndPoNoAndLineNo(inspectionCallNo, poNo, lineNo);
        
        if (optEntity.isPresent()) {
            ProcessOilTankCounter entity = optEntity.get();
            if (!Boolean.TRUE.equals(entity.getIsLocked())) {
                entity.setOilTankCounter(entity.getOilTankCounter() + 1);
                return toDTO(repository.save(entity));
            }
        }
        return optEntity.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional
    public ProcessOilTankCounterDTO markCleaningDone(String inspectionCallNo, String poNo, String lineNo) {
        Optional<ProcessOilTankCounter> optEntity = repository
                .findByInspectionCallNoAndPoNoAndLineNo(inspectionCallNo, poNo, lineNo);
        
        if (optEntity.isPresent()) {
            ProcessOilTankCounter entity = optEntity.get();
            entity.setCleaningDone(true);
            entity.setCleaningDoneAt(LocalDateTime.now());
            entity.setOilTankCounter(0);
            return toDTO(repository.save(entity));
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteByInspectionCallNo(String inspectionCallNo) {
        repository.deleteByInspectionCallNo(inspectionCallNo);
    }

    private ProcessOilTankCounterDTO toDTO(ProcessOilTankCounter entity) {
        ProcessOilTankCounterDTO dto = new ProcessOilTankCounterDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    private ProcessOilTankCounter toEntity(ProcessOilTankCounterDTO dto) {
        ProcessOilTankCounter entity = new ProcessOilTankCounter();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}

