package com.sarthi.repository;


import com.sarthi.entity.InspectionCompleteDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InspectionCompleteDetailsRepository extends JpaRepository<InspectionCompleteDetails, Long> {
}
