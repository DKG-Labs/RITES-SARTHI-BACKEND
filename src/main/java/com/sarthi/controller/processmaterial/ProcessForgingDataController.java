package com.sarthi.controller.processmaterial;

import com.sarthi.dto.processmaterial.ProcessForgingDataDTO;
import com.sarthi.service.ProcessForgingDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/process-material/forging-data")
@CrossOrigin(origins = "*")
public class ProcessForgingDataController {

    @Autowired
    private ProcessForgingDataService service;

    @GetMapping("/call/{inspectionCallNo}")
    public ResponseEntity<List<ProcessForgingDataDTO>> getByInspectionCallNo(
            @PathVariable String inspectionCallNo) {
        return ResponseEntity.ok(service.getByInspectionCallNo(inspectionCallNo));
    }

    @GetMapping("/call/{inspectionCallNo}/po/{poNo}/line/{lineNo}")
    public ResponseEntity<List<ProcessForgingDataDTO>> getByCallNoPoNoLineNo(
            @PathVariable String inspectionCallNo,
            @PathVariable String poNo,
            @PathVariable String lineNo) {
        return ResponseEntity.ok(service.getByCallNoPoNoLineNo(inspectionCallNo, poNo, lineNo));
    }

    @GetMapping("/call/{inspectionCallNo}/shift/{shift}")
    public ResponseEntity<List<ProcessForgingDataDTO>> getByCallNoAndShift(
            @PathVariable String inspectionCallNo,
            @PathVariable String shift) {
        return ResponseEntity.ok(service.getByCallNoAndShift(inspectionCallNo, shift));
    }

    @PostMapping
    public ResponseEntity<ProcessForgingDataDTO> save(
            @RequestBody ProcessForgingDataDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ProcessForgingDataDTO>> saveAll(
            @RequestBody List<ProcessForgingDataDTO> dtos) {
        return ResponseEntity.ok(service.saveAll(dtos));
    }

    @DeleteMapping("/call/{inspectionCallNo}")
    public ResponseEntity<Void> deleteByInspectionCallNo(@PathVariable String inspectionCallNo) {
        service.deleteByInspectionCallNo(inspectionCallNo);
        return ResponseEntity.noContent().build();
    }
}

