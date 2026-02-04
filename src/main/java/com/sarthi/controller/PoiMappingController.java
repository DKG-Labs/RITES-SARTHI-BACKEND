package com.sarthi.controller;


import com.sarthi.dto.UnitDetailsDTO;
import com.sarthi.service.RegionClusterService;
import com.sarthi.service.poiService;
import com.sarthi.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/poiMapping")
public class PoiMappingController {

    @Autowired
    private poiService poiService;


    @GetMapping("/companies")
    public ResponseEntity<Object> getCompanies(@RequestParam String vendorCode) {
        List<String> companies = poiService.getCompanyList(vendorCode);
        return new ResponseEntity<>(
                ResponseBuilder.getSuccessResponse(companies),
                HttpStatus.OK
        );
    }


    @GetMapping("/companies/{companyName}/units")
    public ResponseEntity<Object> getUnitsByCompany(
            @PathVariable String companyName) {

        List<String> units =
                poiService.getUnitsByCompany(companyName);

        return new ResponseEntity<>(
                ResponseBuilder.getSuccessResponse(units),
                HttpStatus.OK
        );
    }
    @GetMapping("/companies/{companyName}/units/{unitName}")
    public ResponseEntity<Object> getUnitDetails(
            @PathVariable String companyName,
            @PathVariable String unitName) {

        UnitDetailsDTO unitDetails =
                poiService.getUnitDetails(
                        companyName.trim(),
                        unitName.trim()
                );

        return new ResponseEntity<>(
                ResponseBuilder.getSuccessResponse(unitDetails),
                HttpStatus.OK
        );
    }


}
