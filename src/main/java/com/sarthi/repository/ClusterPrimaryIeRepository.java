package com.sarthi.repository;

import com.sarthi.entity.ClusterPrimaryIe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClusterPrimaryIeRepository extends JpaRepository<ClusterPrimaryIe, Integer> {
    Optional<ClusterPrimaryIe> findByClusterName(String clusterName);

    Optional<ClusterPrimaryIe> findByIeUserId(Integer ieUserId);

    @Query("SELECT c.ieUserId FROM ClusterPrimaryIe c WHERE c.clusterName = :clusterName")
    List<Integer> findIeUserIdsByClusterName(@Param("clusterName") String clusterName);
}
