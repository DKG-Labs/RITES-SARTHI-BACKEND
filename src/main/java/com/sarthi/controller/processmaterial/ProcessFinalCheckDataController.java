package com.sarthi.controller.processmaterial;

import com.sarthi.dto.processmaterial.ProcessFinalCheckDataDTO;
import com.sarthi.service.ProcessFinalCheckDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/process-material/final-check-data")
@CrossOrigin(origins = "*")
public class ProcessFinalCheckDataController {

    @Autowired
    private ProcessFinalCheckDataService service;

    @GetMapping("/call/{inspectionCallNo}")
    public ResponseEntity<List<ProcessFinalCheckDataDTO>> getByInspectionCallNo(
            @PathVariable String inspectionCallNo) {
        return ResponseEntity.ok(service.getByInspectionCallNo(inspectionCallNo));
    }

    @GetMapping("/call/{inspectionCallNo}/po/{poNo}/line/{lineNo}")
    public ResponseEntity<List<ProcessFinalCheckDataDTO>> getByCallNoPoNoLineNo(
            @PathVariable String inspectionCallNo,
            @PathVariable String poNo,
            @PathVariable String lineNo) {
        return ResponseEntity.ok(service.getByCallNoPoNoLineNo(inspectionCallNo, poNo, lineNo));
    }

    @GetMapping("/call/{inspectionCallNo}/shift/{shift}")
    public ResponseEntity<List<ProcessFinalCheckDataDTO>> getByCallNoAndShift(
            @PathVariable String inspectionCallNo,
            @PathVariable String shift) {
        return ResponseEntity.ok(service.getByCallNoAndShift(inspectionCallNo, shift));
    }

    @PostMapping
    public ResponseEntity<ProcessFinalCheckDataDTO> save(
            @RequestBody ProcessFinalCheckDataDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ProcessFinalCheckDataDTO>> saveAll(
            @RequestBody List<ProcessFinalCheckDataDTO> dtos) {
        return ResponseEntity.ok(service.saveAll(dtos));
    }

    @DeleteMapping("/call/{inspectionCallNo}")
    public ResponseEntity<Void> deleteByInspectionCallNo(@PathVariable String inspectionCallNo) {
        service.deleteByInspectionCallNo(inspectionCallNo);
        return ResponseEntity.noContent().build();
    }
}

