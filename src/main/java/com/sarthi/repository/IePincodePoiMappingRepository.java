package com.sarthi.repository;

import com.sarthi.entity.IePincodePoiMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IePincodePoiMappingRepository extends JpaRepository<IePincodePoiMapping, Long> {
    Optional<Integer> findPrimaryIe(String pinCode, String product, String poiCode, String rio);

    Optional<Integer> findSecondaryIe(String pinCode, String product, String poiCode, String rio);
}
