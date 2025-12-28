package com.sarthi.repository;

import com.sarthi.entity.ClusterSecondaryIe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClusterSecondaryIeRepository extends JpaRepository<ClusterSecondaryIe, Integer> {
    List<ClusterSecondaryIe> findByClusterNameOrderByPriorityOrderAsc(String clusterName);

    Optional<ClusterSecondaryIe> findByIeUserId(Integer ieUserId);
}
