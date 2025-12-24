package com.sarthi.repository;

import com.sarthi.entity.CricsPos.PoMaHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoMaHeaderRepository extends JpaRepository<PoMaHeader, Long> {
    boolean existsByMaKey(String maKey);
}
