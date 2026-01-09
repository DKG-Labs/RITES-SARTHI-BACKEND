package com.sarthi.repository;


import com.sarthi.entity.InspectionCompleteDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InspectionCompleteDetailsRepository extends JpaRepository<InspectionCompleteDetails, Long> {

    /**
     * Find InspectionCompleteDetails by call number
     */
    Optional<InspectionCompleteDetails> findByCallNo(String callNo);
}
