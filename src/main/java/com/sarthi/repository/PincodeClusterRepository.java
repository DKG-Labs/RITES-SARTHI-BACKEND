package com.sarthi.repository;

import com.sarthi.entity.PincodeCluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PincodeClusterRepository extends JpaRepository<PincodeCluster, Integer> {

    Optional<PincodeCluster> findByPincode(String pincode);
}

