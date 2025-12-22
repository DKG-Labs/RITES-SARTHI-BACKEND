package com.sarthi.repository;

import com.sarthi.entity.RmDimensionalCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for RmDimensionalCheck entity.
 */
@Repository
public interface RmDimensionalCheckRepository extends JpaRepository<RmDimensionalCheck, Long> {

    List<RmDimensionalCheck> findByInspectionCallNo(String inspectionCallNo);

    List<RmDimensionalCheck> findByInspectionCallNoAndHeatNo(String inspectionCallNo, String heatNo);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

