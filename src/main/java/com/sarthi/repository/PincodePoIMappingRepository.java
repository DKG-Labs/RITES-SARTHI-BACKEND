package com.sarthi.repository;

import com.sarthi.entity.PincodePoIMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PincodePoIMappingRepository extends JpaRepository<PincodePoIMapping, Long> {
    boolean existsByPinCodeAndPoiCode(String pinCode, String poiCode);
}
