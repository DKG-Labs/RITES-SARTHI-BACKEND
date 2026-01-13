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

    /**
     * Find InspectionCompleteDetails by certificate number
     * Used to resolve certificate numbers (e.g., N/ER-01080001/RAJK) to call numbers (e.g., ER-01080001)
     */
    Optional<InspectionCompleteDetails> findByCertificateNo(String certificateNo);
}
