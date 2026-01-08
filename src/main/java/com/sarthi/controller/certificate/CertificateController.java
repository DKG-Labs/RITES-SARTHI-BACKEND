package com.sarthi.controller.certificate;

import com.sarthi.dto.certificate.RawMaterialCertificateDto;
import com.sarthi.service.certificate.CertificateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Certificate Generation APIs.
 * Provides endpoints to generate inspection certificates.
 */
@RestController
@RequestMapping("/api/certificate")
@CrossOrigin(origins = "*")
public class CertificateController {

    private static final Logger logger = LoggerFactory.getLogger(CertificateController.class);

    @Autowired
    private CertificateService certificateService;

    /**
     * Generate Raw Material Inspection Certificate by IC Number
     * 
     * @param icNumber - Inspection Call Number (e.g., RM-IC-1767772023499)
     * @return RawMaterialCertificateDto with all certificate data
     * 
     * Example: GET /api/certificate/raw-material/RM-IC-1767772023499
     */
    @GetMapping("/raw-material/{icNumber}")
    public ResponseEntity<?> generateRawMaterialCertificate(@PathVariable String icNumber) {
        try {
            logger.info("Generating Raw Material Certificate for IC Number: {}", icNumber);
            RawMaterialCertificateDto certificate = certificateService.generateRawMaterialCertificate(icNumber);
            return ResponseEntity.ok(certificate);
        } catch (IllegalArgumentException e) {
            logger.error("Error generating certificate: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error generating certificate", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating certificate: " + e.getMessage());
        }
    }

    /**
     * Generate Raw Material Inspection Certificate by Call ID
     * 
     * @param callId - Inspection Call ID
     * @return RawMaterialCertificateDto with all certificate data
     * 
     * Example: GET /api/certificate/raw-material/by-id/1
     */
    @GetMapping("/raw-material/by-id/{callId}")
    public ResponseEntity<?> generateRawMaterialCertificateById(@PathVariable Long callId) {
        try {
            logger.info("Generating Raw Material Certificate for Call ID: {}", callId);
            RawMaterialCertificateDto certificate = certificateService.generateRawMaterialCertificateById(callId);
            return ResponseEntity.ok(certificate);
        } catch (IllegalArgumentException e) {
            logger.error("Error generating certificate: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error generating certificate", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating certificate: " + e.getMessage());
        }
    }

    /**
     * Health check endpoint
     * 
     * Example: GET /api/certificate/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Certificate Service is running");
    }
}

