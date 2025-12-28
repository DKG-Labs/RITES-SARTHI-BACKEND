package com.sarthi.repository;

import com.sarthi.entity.RmVisualInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for RmVisualInspection entity.
 */
@Repository
public interface RmVisualInspectionRepository extends JpaRepository<RmVisualInspection, Long> {

    List<RmVisualInspection> findByInspectionCallNo(String inspectionCallNo);

    List<RmVisualInspection> findByInspectionCallNoAndHeatNo(String inspectionCallNo, String heatNo);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

