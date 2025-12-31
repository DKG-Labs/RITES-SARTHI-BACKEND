package com.sarthi.repository;

import com.sarthi.entity.CricsPos.PoCancellationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoCancellationDetailRepository extends JpaRepository<PoCancellationDetail, Long> {
}
