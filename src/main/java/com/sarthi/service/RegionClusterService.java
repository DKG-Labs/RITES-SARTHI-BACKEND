package com.sarthi.service;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RegionClusterService {

    public List<String> getAllRegions();

    public List<String> getClustersByRegion(String regionName);


}
