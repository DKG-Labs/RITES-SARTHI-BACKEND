package com.sarthi.repository;

import com.sarthi.dto.InspectionQtySummaryView;
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


        @Query("""
        SELECT COALESCE(SUM(p.inspectedQty), 0)
        FROM ProcessIeQty p
        WHERE p.requestId = :requestId
          AND p.lotNumber = :lotNumber
    """)
        int sumInspectedQtyByRequestIdAndLotNumber(
                @Param("requestId") String requestId,
                @Param("lotNumber") String lotNumber
        );

        @Query("""
        SELECT COALESCE(MAX(p.offeredQty), 0)
        FROM ProcessIeQty p
        WHERE p.requestId = :requestId
          AND p.lotNumber = :lotNumber
    """)
        int findOfferedQtyByRequestIdAndLotNumber(
                @Param("requestId") String requestId,
                @Param("lotNumber") String lotNumber
        );

    @Query("""
    SELECT 
        COALESCE(SUM(p.inspectedQty), 0) AS acceptedQty,
        COALESCE(SUM(p.offeredQty), 0) AS totalOfferedQty,
        COALESCE(SUM(p.manufactureQty), 0) AS totalManufactureQty
    FROM ProcessIeQty p
    WHERE p.requestId = :requestId
""")
    InspectionQtySummaryView getQtySummaryByRequestId(
            @Param("requestId") String requestId
    );

    boolean existsByRequestId(String requestId);
}

