package com.sarthi.repository;

import com.sarthi.entity.CricsPos.PoMaDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoMaDetailRepository extends JpaRepository<PoMaDetail, Long> {
}
