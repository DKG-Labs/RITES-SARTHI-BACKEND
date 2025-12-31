package com.sarthi.repository;

import com.sarthi.entity.RmPackingStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for RmPackingStorage entity.
=======
import java.util.List;

/**
 * Repository for RmPackingStorage entity.
 * Now supports per-heat packing storage records.
>>>>>>> b25fb8a13f91b1c2c4f3aa1c8ed8ab67a7380ee8
 */
@Repository
public interface RmPackingStorageRepository extends JpaRepository<RmPackingStorage, Long> {


    List<RmPackingStorage> findByInspectionCallNo(String inspectionCallNo);

    List<RmPackingStorage> findByInspectionCallNoAndHeatNo(String inspectionCallNo, String heatNo);


    void deleteByInspectionCallNo(String inspectionCallNo);
}

