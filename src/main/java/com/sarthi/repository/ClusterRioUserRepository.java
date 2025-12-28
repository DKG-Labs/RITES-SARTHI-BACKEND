package com.sarthi.repository;

import com.sarthi.entity.ClusterRioUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ClusterRioUserRepository extends JpaRepository<ClusterRioUser, Integer> {

    Optional<ClusterRioUser> findByClusterName(String regionName);


}

