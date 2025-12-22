package com.sarthi.repository;

import com.sarthi.entity.ClusterCmUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClusterCmUserRepository extends JpaRepository<ClusterCmUser, Integer> {
    Optional<ClusterCmUser> findByClusterName(String clusterName);
}
