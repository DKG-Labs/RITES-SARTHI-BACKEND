package com.sarthi.repository;

import com.sarthi.entity.ProcessIeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessIeMasterRepository extends JpaRepository<ProcessIeMaster, Integer> {
    Optional<ProcessIeMaster> findByClusterName(String clusterName);
}
