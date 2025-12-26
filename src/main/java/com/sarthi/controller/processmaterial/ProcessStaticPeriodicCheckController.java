package com.sarthi.controller.processmaterial;

import com.sarthi.dto.processmaterial.ProcessStaticPeriodicCheckDTO;
import com.sarthi.service.ProcessStaticPeriodicCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/process-material/static-periodic-check")
@CrossOrigin(origins = "*")
public class ProcessStaticPeriodicCheckController {

    @Autowired
    private ProcessStaticPeriodicCheckService service;

    @GetMapping("/call/{inspectionCallNo}")
    public ResponseEntity<List<ProcessStaticPeriodicCheckDTO>> getByInspectionCallNo(
            @PathVariable String inspectionCallNo) {
        return ResponseEntity.ok(service.getByInspectionCallNo(inspectionCallNo));
    }

    @GetMapping("/call/{inspectionCallNo}/po/{poNo}/line/{lineNo}")
    public ResponseEntity<ProcessStaticPeriodicCheckDTO> getByCallNoPoNoLineNo(
            @PathVariable String inspectionCallNo,
            @PathVariable String poNo,
            @PathVariable String lineNo) {
        return service.getByCallNoPoNoLineNo(inspectionCallNo, poNo, lineNo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProcessStaticPeriodicCheckDTO> save(
            @RequestBody ProcessStaticPeriodicCheckDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @DeleteMapping("/call/{inspectionCallNo}")
    public ResponseEntity<Void> deleteByInspectionCallNo(@PathVariable String inspectionCallNo) {
        service.deleteByInspectionCallNo(inspectionCallNo);
        return ResponseEntity.noContent().build();
    }
}

