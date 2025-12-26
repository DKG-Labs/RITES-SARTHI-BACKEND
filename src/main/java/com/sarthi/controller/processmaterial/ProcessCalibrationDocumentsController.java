package com.sarthi.controller.processmaterial;

import com.sarthi.dto.processmaterial.ProcessCalibrationDocumentsDTO;
import com.sarthi.service.ProcessCalibrationDocumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/process-material/calibration-documents")
@CrossOrigin(origins = "*")
public class ProcessCalibrationDocumentsController {

    @Autowired
    private ProcessCalibrationDocumentsService service;

    @GetMapping("/call/{inspectionCallNo}")
    public ResponseEntity<List<ProcessCalibrationDocumentsDTO>> getByInspectionCallNo(
            @PathVariable String inspectionCallNo) {
        return ResponseEntity.ok(service.getByInspectionCallNo(inspectionCallNo));
    }

    @GetMapping("/call/{inspectionCallNo}/po/{poNo}/line/{lineNo}")
    public ResponseEntity<List<ProcessCalibrationDocumentsDTO>> getByCallNoPoNoLineNo(
            @PathVariable String inspectionCallNo,
            @PathVariable String poNo,
            @PathVariable String lineNo) {
        return ResponseEntity.ok(service.getByCallNoPoNoLineNo(inspectionCallNo, poNo, lineNo));
    }

    @PostMapping
    public ResponseEntity<ProcessCalibrationDocumentsDTO> save(
            @RequestBody ProcessCalibrationDocumentsDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ProcessCalibrationDocumentsDTO>> saveAll(
            @RequestBody List<ProcessCalibrationDocumentsDTO> dtos) {
        return ResponseEntity.ok(service.saveAll(dtos));
    }

    @DeleteMapping("/call/{inspectionCallNo}")
    public ResponseEntity<Void> deleteByInspectionCallNo(@PathVariable String inspectionCallNo) {
        service.deleteByInspectionCallNo(inspectionCallNo);
        return ResponseEntity.noContent().build();
    }
}

