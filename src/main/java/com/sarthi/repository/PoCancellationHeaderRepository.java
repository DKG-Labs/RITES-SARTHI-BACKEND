package com.sarthi.repository;

import com.sarthi.entity.CricsPos.PoCancellationHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoCancellationHeaderRepository extends JpaRepository<PoCancellationHeader, Long> {
    boolean existsByCakey(String cakey);
}
