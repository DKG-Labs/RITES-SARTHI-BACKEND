package com.sarthi.controller;

import com.sarthi.service.RegionClusterService;
import com.sarthi.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/region-cluster")
public class RegionClusterController {

    @Autowired
    private RegionClusterService regionClusterService;

    //Get unique regions
    @GetMapping("/regions")
    public ResponseEntity<Object> getAllRegions() {
        List<String> regions = regionClusterService.getAllRegions();
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(regions), HttpStatus.OK);
    }

    //Get clusters by region
    @GetMapping("/clusters/{regionName}")
    public ResponseEntity<Object> getClustersByRegion(@PathVariable String regionName) {
        List<String> clusters = regionClusterService.getClustersByRegion(regionName);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(clusters), HttpStatus.OK);
    }


}
