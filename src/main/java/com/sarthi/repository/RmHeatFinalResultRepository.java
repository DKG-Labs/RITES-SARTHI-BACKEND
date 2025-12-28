package com.sarthi.repository;

import com.sarthi.entity.RmHeatFinalResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for RmHeatFinalResult entity.
 */
@Repository
public interface RmHeatFinalResultRepository extends JpaRepository<RmHeatFinalResult, Long> {

    List<RmHeatFinalResult> findByInspectionCallNo(String inspectionCallNo);

    List<RmHeatFinalResult> findByInspectionCallNoAndHeatNo(String inspectionCallNo, String heatNo);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

