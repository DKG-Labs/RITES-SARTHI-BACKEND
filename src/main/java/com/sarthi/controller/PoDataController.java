package com.sarthi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sarthi.dto.po.PoDataForSectionsDto;
import com.sarthi.service.PoDataService;

/**
 * Controller for fetching PO data for Inspection Initiation Sections A, B, C
 * Provides data from po_header, po_item, po_ma_header, po_ma_detail tables
 */
@RestController
@RequestMapping("/api/po-data")
@CrossOrigin(origins = "*")
public class PoDataController {

    @Autowired
    private PoDataService poDataService;

    /**
     * Get PO data for Sections A, B, C by PO Number
     * Automatically includes RM inspection details (Section C) if available
     *
     * @param poNo PO Number
     * @param requestId Optional Inspection Call Number to filter specific inspection call data
     * @return PoDataForSectionsDto containing all required data including RM heat details
     *
     * Example: GET /api/po-data/sections?poNo=PO123456&requestId=RM-IC-1766906438059
     */
    @GetMapping("/sections")
    public ResponseEntity<?> getPoDataForSections(
            @RequestParam String poNo,
            @RequestParam(required = false) String requestId) {
        try {
            if (poNo == null || poNo.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("PO Number is required");
            }

            // Use the enhanced method that includes RM details
            PoDataForSectionsDto data = poDataService.getPoDataWithRmDetailsForSectionC(poNo, requestId);

            if (data == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("PO not found for PO Number: " + poNo);
            }

            return ResponseEntity.ok(data);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching PO data: " + e.getMessage());
        }
    }

    /**
     * Get PO data for Section A only
     *
     * @param poNo PO Number
     * @param requestId Optional Inspection Call Number
     * @return Section A data
     *
     * Example: GET /api/po-data/section-a?poNo=PO123456&requestId=RM-IC-1766906438059
     */
    @GetMapping("/section-a")
    public ResponseEntity<?> getSectionAData(
            @RequestParam String poNo,
            @RequestParam(required = false) String requestId) {
        return getPoDataForSections(poNo, requestId);
    }

    /**
     * Get PO data for Section B only
     *
     * @param poNo PO Number
     * @param requestId Optional Inspection Call Number
     * @return Section B data
     *
     * Example: GET /api/po-data/section-b?poNo=PO123456&requestId=RM-IC-1766906438059
     */
    @GetMapping("/section-b")
    public ResponseEntity<?> getSectionBData(
            @RequestParam String poNo,
            @RequestParam(required = false) String requestId) {
        return getPoDataForSections(poNo, requestId);
    }

    /**
     * Get PO data for Section C only (with RM inspection details)
     *
     * @param poNo PO Number
     * @param requestId Optional Inspection Call Number
     * @return Section C data with RM heat details
     *
     * Example: GET /api/po-data/section-c?poNo=PO123456&requestId=RM-IC-1766906438059
     */
    @GetMapping("/section-c")
    public ResponseEntity<?> getSectionCData(
            @RequestParam String poNo,
            @RequestParam(required = false) String requestId) {
        try {
            if (poNo == null || poNo.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("PO Number is required");
            }

            PoDataForSectionsDto data = poDataService.getPoDataWithRmDetailsForSectionC(poNo, requestId);

            if (data == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("PO not found for PO Number: " + poNo);
            }

            return ResponseEntity.ok(data);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching Section C data: " + e.getMessage());
        }
    }

    /**
     * Update color code for a specific heat number
     *
     * @param heatId Heat ID from rm_heat_quantities table
     * @param request Request body containing colorCode
     * @return Updated heat data
     *
     * Example: PUT /api/po-data/heat/1/color-code
     * Body: { "colorCode": "RED-001" }
     */
    @PutMapping("/heat/{heatId}/color-code")
    public ResponseEntity<?> updateColorCode(
            @PathVariable Integer heatId,
            @RequestBody ColorCodeUpdateRequest request) {
        try {
            if (heatId == null) {
                return ResponseEntity.badRequest().body("Heat ID is required");
            }

            if (request.getColorCode() == null || request.getColorCode().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Color code is required");
            }

            boolean updated = poDataService.updateColorCode(heatId, request.getColorCode());

            if (updated) {
                return ResponseEntity.ok().body("Color code updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Heat not found with ID: " + heatId);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating color code: " + e.getMessage());
        }
    }

    /**
     * Inner class for color code update request
     */
    public static class ColorCodeUpdateRequest {
        private String colorCode;

        public String getColorCode() {
            return colorCode;
        }

        public void setColorCode(String colorCode) {
            this.colorCode = colorCode;
        }
    }
}

