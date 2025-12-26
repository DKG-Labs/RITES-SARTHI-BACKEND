package com.sarthi.service.Impl;

import com.sarthi.dto.processmaterial.ProcessSummaryReportDTO;
import com.sarthi.entity.processmaterial.ProcessSummaryReport;
import com.sarthi.repository.processmaterial.ProcessSummaryReportRepository;
import com.sarthi.service.ProcessSummaryReportService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProcessSummaryReportServiceImpl implements ProcessSummaryReportService {

    @Autowired
    private ProcessSummaryReportRepository repository;

    @Override
    public List<ProcessSummaryReportDTO> getByInspectionCallNo(String inspectionCallNo) {
        return repository.findByInspectionCallNo(inspectionCallNo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProcessSummaryReportDTO> getByCallNoPoNoLineNo(
            String inspectionCallNo, String poNo, String lineNo) {
        return repository.findByInspectionCallNoAndPoNoAndLineNo(inspectionCallNo, poNo, lineNo)
                .map(this::toDTO);
    }

    @Override
    @Transactional
    public ProcessSummaryReportDTO save(ProcessSummaryReportDTO dto) {
        ProcessSummaryReport entity = toEntity(dto);
        ProcessSummaryReport saved = repository.save(entity);
        return toDTO(saved);
    }

    @Override
    @Transactional
    public ProcessSummaryReportDTO completeInspection(String inspectionCallNo, String poNo,
            String lineNo, String ieRemarks, String finalStatus) {
        Optional<ProcessSummaryReport> optEntity = repository
                .findByInspectionCallNoAndPoNoAndLineNo(inspectionCallNo, poNo, lineNo);

        if (optEntity.isPresent()) {
            ProcessSummaryReport entity = optEntity.get();
            entity.setIeRemarks(ieRemarks);
            entity.setFinalStatus(finalStatus);
            entity.setInspectionCompleted(true);
            entity.setInspectionCompletedAt(LocalDateTime.now());
            return toDTO(repository.save(entity));
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteByInspectionCallNo(String inspectionCallNo) {
        repository.deleteByInspectionCallNo(inspectionCallNo);
    }

    private ProcessSummaryReportDTO toDTO(ProcessSummaryReport entity) {
        ProcessSummaryReportDTO dto = new ProcessSummaryReportDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    private ProcessSummaryReport toEntity(ProcessSummaryReportDTO dto) {
        ProcessSummaryReport entity = new ProcessSummaryReport();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}

