package com.sarthi.repository;

import com.sarthi.entity.TransitionConditionMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransitionConditionMasterRepository extends JpaRepository<TransitionConditionMaster, Integer> {


}
