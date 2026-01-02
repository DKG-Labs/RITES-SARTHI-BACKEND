package com.sarthi.repository;

import com.sarthi.entity.IeControllingManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ieControllingManagerRepository extends JpaRepository<IeControllingManager, Long> {
}
