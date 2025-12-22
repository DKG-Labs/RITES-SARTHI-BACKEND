package com.sarthi.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClusterPrimaryIeService {

    public List<Integer> getIeUsersByCluster(String clusterName);
}
