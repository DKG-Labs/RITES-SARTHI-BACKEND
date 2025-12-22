package com.sarthi.repository;

import com.sarthi.entity.RmMaterialTesting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for RmMaterialTesting entity.
 */
@Repository
public interface RmMaterialTestingRepository extends JpaRepository<RmMaterialTesting, Long> {

    List<RmMaterialTesting> findByInspectionCallNo(String inspectionCallNo);

    List<RmMaterialTesting> findByInspectionCallNoAndHeatNo(String inspectionCallNo, String heatNo);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

