package com.sarthi.service.Impl;

import com.sarthi.entity.RegionCluster;
import com.sarthi.repository.RegionClusterRepository;
import com.sarthi.service.RegionClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionClusterServiceImpl implements RegionClusterService {

    @Autowired
    private RegionClusterRepository regionClusterRepository;
    @Override
    public List<String> getAllRegions() {
        return regionClusterRepository.findDistinctRegions();
    }

    @Override
    public List<String> getClustersByRegion(String regionName) {
        return regionClusterRepository.findByRegionName(regionName)
                .stream()
                .map(RegionCluster::getClusterName)
                .toList();
    }
}
