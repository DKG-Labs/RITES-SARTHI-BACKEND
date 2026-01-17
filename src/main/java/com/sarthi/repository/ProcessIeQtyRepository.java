package com.sarthi.repository;

import com.sarthi.entity.ProcessIeQty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessIeQtyRepository
        extends JpaRepository<ProcessIeQty, Long> {

    Optional<ProcessIeQty> findByRequestIdAndSwiftCode(
            String requestId, String swiftCode);

    @Query("SELECT COALESCE(SUM(p.inspectedQty),0) " +
            "FROM ProcessIeQty p WHERE p.requestId = :requestId")
    int sumInspectedQtyByRequestId(@Param("requestId") String requestId);
}

