package com.sarthi.repository;

import com.sarthi.entity.PoHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PoHeaderRepository extends JpaRepository<PoHeader, Long> {


	/**
	 * Find PO Header by PO Number.
	 */
	Optional<PoHeader> findByPoNo(String poNo);


    boolean existsByPoKey(String poKey);

    List<PoHeader> findByVendorCode(String vendorCode);

//    @Query("""
//        select distinct h
//        from PoHeader h
//        left join fetch h.items i
//        where h.vendorCode = :vendorCode
//    """)
//    List<PoHeader> findAllByVendorCodeWithItems(String vendorCode);
    @Query("""
        select distinct h
        from PoHeader h
        left join fetch h.items i
        where h.vendorCode = :vendorCode
    """)
    List<PoHeader> findAllByVendorCodeWithItems(String vendorCode);
}
