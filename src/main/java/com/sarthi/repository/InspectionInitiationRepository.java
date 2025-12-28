package com.sarthi.repository;

import com.sarthi.entity.InspectionInitiation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for InspectionInitiation entity.
 */
@Repository
public interface InspectionInitiationRepository extends JpaRepository<InspectionInitiation, Long> {

    Optional<InspectionInitiation> findByCallNo(String callNo);

    Optional<InspectionInitiation> findByInspectionRequestId(Long inspectionRequestId);

    List<InspectionInitiation> findByStatus(String status);

    List<InspectionInitiation> findByInitiatedBy(String initiatedBy);

    boolean existsByCallNo(String callNo);

    boolean existsByInspectionRequestId(Long inspectionRequestId);
}

