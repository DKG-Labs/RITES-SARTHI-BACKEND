package com.sarthi.controller.processmaterial;

import com.sarthi.dto.processmaterial.ProcessOilTankCounterDTO;
import com.sarthi.service.ProcessOilTankCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/process-material/oil-tank-counter")
@CrossOrigin(origins = "*")
public class ProcessOilTankCounterController {

    @Autowired
    private ProcessOilTankCounterService service;

    @GetMapping("/call/{inspectionCallNo}")
    public ResponseEntity<List<ProcessOilTankCounterDTO>> getByInspectionCallNo(
            @PathVariable String inspectionCallNo) {
        return ResponseEntity.ok(service.getByInspectionCallNo(inspectionCallNo));
    }

    @GetMapping("/call/{inspectionCallNo}/po/{poNo}/line/{lineNo}")
    public ResponseEntity<ProcessOilTankCounterDTO> getByCallNoPoNoLineNo(
            @PathVariable String inspectionCallNo,
            @PathVariable String poNo,
            @PathVariable String lineNo) {
        return service.getByCallNoPoNoLineNo(inspectionCallNo, poNo, lineNo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProcessOilTankCounterDTO> save(
            @RequestBody ProcessOilTankCounterDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/call/{inspectionCallNo}/po/{poNo}/line/{lineNo}/increment")
    public ResponseEntity<ProcessOilTankCounterDTO> incrementCounter(
            @PathVariable String inspectionCallNo,
            @PathVariable String poNo,
            @PathVariable String lineNo) {
        ProcessOilTankCounterDTO result = service.incrementCounter(inspectionCallNo, poNo, lineNo);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @PutMapping("/call/{inspectionCallNo}/po/{poNo}/line/{lineNo}/cleaning-done")
    public ResponseEntity<ProcessOilTankCounterDTO> markCleaningDone(
            @PathVariable String inspectionCallNo,
            @PathVariable String poNo,
            @PathVariable String lineNo) {
        ProcessOilTankCounterDTO result = service.markCleaningDone(inspectionCallNo, poNo, lineNo);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/call/{inspectionCallNo}")
    public ResponseEntity<Void> deleteByInspectionCallNo(@PathVariable String inspectionCallNo) {
        service.deleteByInspectionCallNo(inspectionCallNo);
        return ResponseEntity.noContent().build();
    }
}

