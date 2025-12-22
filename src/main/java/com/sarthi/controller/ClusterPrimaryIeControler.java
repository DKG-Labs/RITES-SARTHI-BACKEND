package com.sarthi.controller;

import com.sarthi.service.ClusterPrimaryIeService;
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
@RequestMapping("/api")
public class ClusterPrimaryIeControler {
    @Autowired
    private ClusterPrimaryIeService clusterPrimaryIeService;

    @GetMapping("/ie-users/{clusterName}")
    public ResponseEntity<Object> getIeUsers(@PathVariable String clusterName) {

        List<Integer> ieUserIds = clusterPrimaryIeService.getIeUsersByCluster(clusterName);

        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(ieUserIds), HttpStatus.OK);
    }
}
