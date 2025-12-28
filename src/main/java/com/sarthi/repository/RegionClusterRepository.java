package com.sarthi.repository;

import com.sarthi.entity.RegionCluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RegionClusterRepository extends JpaRepository<RegionCluster, Integer> {

    Optional<RegionCluster> findByClusterName(String clusterName);

    @Query("SELECT DISTINCT r.regionName FROM RegionCluster r")
    List<String> findDistinctRegions();

    List<RegionCluster> findByRegionName(String regionName);
}


