package com.sarthi.repository;

import com.sarthi.entity.CricsPos.AmendmentPoHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AmendmentPoHeaderRepository extends JpaRepository<AmendmentPoHeader, Long> {


    boolean existsByPokey(String pokey);

    Optional<AmendmentPoHeader> findByPokey(String pokey);
}
