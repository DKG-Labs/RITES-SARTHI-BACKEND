package com.sarthi.repository;

import com.sarthi.entity.IePincodePoiMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IePincodePoiMappingRepository extends JpaRepository<IePincodePoiMapping, Long> {
   // Optional<Integer> findPrimaryIe(String pinCode, String product, String poiCode, String rio);

   // Optional<Integer> findSecondaryIe(String pinCode, String product, String poiCode, String rio);


    @Query("""
    SELECT m.employeeCode
    FROM IePincodePoiMapping m
    WHERE m.pinCode = :pinCode
      AND m.product = :product
      AND m.poiCode = :poiCode
      AND m.ieType = 'PRIMARY'
""")
    Optional<String> findPrimaryIe(
            String pinCode,
            String product,
            String poiCode
    );

    @Query("""
    SELECT m.employeeCode
    FROM IePincodePoiMapping m
    WHERE m.pinCode = :pinCode
      AND m.product = :product
      AND m.poiCode = :poiCode
      AND m.ieType = 'SECONDARY'
""")
    Optional<String> findSecondaryIe(
            String pinCode,
            String product,
            String poiCode
    );



}
