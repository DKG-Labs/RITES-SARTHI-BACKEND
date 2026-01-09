package com.sarthi.repository;

import com.sarthi.entity.FinalIeMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinalIeMappingRepository extends JpaRepository<FinalIeMapping, Long> {
    List<FinalIeMapping> findByWorkflowTransitionId(Integer workflowTransitionId);

    boolean existsByWorkflowTransitionIdAndIeUserId(Integer workflowTransitionId, Integer actionBy);
}
