package com.sarthi.repository;

import com.sarthi.entity.ProcessIeMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessIeMappingRepository extends JpaRepository<ProcessIeMapping, Integer> {
    List<ProcessIeMapping> findByProcessIeUserId(Integer processIeUserId);
}
