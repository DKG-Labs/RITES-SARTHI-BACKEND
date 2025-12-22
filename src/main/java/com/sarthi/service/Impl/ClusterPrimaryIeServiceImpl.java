package com.sarthi.service.Impl;

import com.sarthi.entity.ClusterPrimaryIe;
import com.sarthi.repository.ClusterPrimaryIeRepository;
import com.sarthi.service.ClusterPrimaryIeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClusterPrimaryIeServiceImpl implements ClusterPrimaryIeService {

    @Autowired
    private ClusterPrimaryIeRepository clusterPrimaryIeRepository;

    @Override
    public List<Integer> getIeUsersByCluster(String clusterName) {
        return clusterPrimaryIeRepository.findIeUserIdsByClusterName(clusterName);
    }
}
