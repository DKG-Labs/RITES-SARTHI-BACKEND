package com.sarthi.repository;

import com.sarthi.entity.RmPackingStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for RmPackingStorage entity.
 */
@Repository
public interface RmPackingStorageRepository extends JpaRepository<RmPackingStorage, Long> {

    Optional<RmPackingStorage> findByInspectionCallNo(String inspectionCallNo);

    void deleteByInspectionCallNo(String inspectionCallNo);
}

