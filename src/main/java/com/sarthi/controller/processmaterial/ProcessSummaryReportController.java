package com.sarthi.controller.processmaterial;

import com.sarthi.dto.processmaterial.ProcessSummaryReportDTO;
import com.sarthi.service.ProcessSummaryReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/process-material/summary-report")
@CrossOrigin(origins = "*")
public class ProcessSummaryReportController {

    @Autowired
    private ProcessSummaryReportService service;

    @GetMapping("/call/{inspectionCallNo}")
    public ResponseEntity<List<ProcessSummaryReportDTO>> getByInspectionCallNo(
            @PathVariable String inspectionCallNo) {
        return ResponseEntity.ok(service.getByInspectionCallNo(inspectionCallNo));
    }

    @GetMapping("/call/{inspectionCallNo}/po/{poNo}/line/{lineNo}")
    public ResponseEntity<ProcessSummaryReportDTO> getByCallNoPoNoLineNo(
            @PathVariable String inspectionCallNo,
            @PathVariable String poNo,
            @PathVariable String lineNo) {
        return service.getByCallNoPoNoLineNo(inspectionCallNo, poNo, lineNo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProcessSummaryReportDTO> save(
            @RequestBody ProcessSummaryReportDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/call/{inspectionCallNo}/po/{poNo}/line/{lineNo}/complete")
    public ResponseEntity<ProcessSummaryReportDTO> completeInspection(
            @PathVariable String inspectionCallNo,
            @PathVariable String poNo,
            @PathVariable String lineNo,
            @RequestBody Map<String, String> payload) {
        String ieRemarks = payload.get("ieRemarks");
        String finalStatus = payload.get("finalStatus");
        ProcessSummaryReportDTO result = service.completeInspection(
                inspectionCallNo, poNo, lineNo, ieRemarks, finalStatus);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/call/{inspectionCallNo}")
    public ResponseEntity<Void> deleteByInspectionCallNo(@PathVariable String inspectionCallNo) {
        service.deleteByInspectionCallNo(inspectionCallNo);
        return ResponseEntity.noContent().build();
    }
}

