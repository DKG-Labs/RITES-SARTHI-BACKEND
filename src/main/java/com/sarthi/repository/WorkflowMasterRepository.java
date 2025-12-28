package com.sarthi.repository;


import com.sarthi.entity.WorkflowMaster;
import com.sarthi.entity.WorkflowTransition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkflowMasterRepository extends JpaRepository<WorkflowMaster, Integer> {
    WorkflowMaster findByWorkflowName(String inspectionCall);

}
