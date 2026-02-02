package com.sarthi.repository;

import com.sarthi.dto.UnitAddressDto;
import com.sarthi.entity.InventorySupplierDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventorySupplierDetailsRepository extends JpaRepository<InventorySupplierDetails, Long> {

    @Query("SELECT DISTINCT i.companyName FROM InventorySupplierDetails i WHERE i.product = :product")
    List<String> findCompanyNamesByProduct(@Param("product") String product);

    // By Company Name → Unit Names
    @Query("SELECT DISTINCT i.unitName FROM InventorySupplierDetails i WHERE i.companyName = :companyName")
    List<String> findUnitNamesByCompany(@Param("companyName") String companyName);

    @Query("""
SELECT new com.sarthi.dto.UnitAddressDto(i.unitName, i.address)
FROM InventorySupplierDetails i
WHERE i.companyName = :companyName
""")
    List<UnitAddressDto> findUnitsWithAddressByCompany(
            @Param("companyName") String companyName);


    //  By Unit Name → Address
    @Query("SELECT i.address FROM InventorySupplierDetails i WHERE i.unitName = :unitName")
    List<String> findAddressByUnit(@Param("unitName") String unitName);


}
