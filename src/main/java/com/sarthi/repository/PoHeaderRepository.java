package com.sarthi.repository;

import com.sarthi.entity.PoHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoHeaderRepository extends JpaRepository<PoHeader, Long> {


    boolean existsByPoKey(String poKey);

//    @Query("""
//        select distinct h
//        from PoHeader h
//        left join fetch h.items i
//        where h.vendorCode = :vendorCode
//    """)
//    List<PoHeader> findAllByVendorCodeWithItems(String vendorCode);
}
