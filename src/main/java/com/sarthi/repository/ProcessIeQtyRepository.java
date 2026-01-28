package com.sarthi.repository;

import com.sarthi.dto.InspectionQtySummaryView;
import com.sarthi.dto.TotalManufaturedQtyOfPoDto;
import com.sarthi.entity.ProcessIeQty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
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

    /*@Query("""
        SELECT COALESCE(SUM(p.manufactureQty), 0)
        FROM ProcessIeQty p
        WHERE p.requestId IN :callNos
        AND (:heatNo IS NULL OR p.heatNo = :heatNo)
    """)
    BigDecimal sumManufacturedQtyByCallNos(
            @Param("callNos") List<String> callNos,
            @Param("heatNo") String heatNo
    );*/
    @Query("""
    SELECT new com.sarthi.dto.TotalManufaturedQtyOfPoDto(
        CAST(COALESCE(SUM(p.manufactureQty), 0) AS big_decimal),
        COALESCE(SUM(p.rejectedQty), 0),
        NULL,
        CAST(COALESCE(SUM(p.inspectedQty), 0) AS big_decimal),
        :heatNo
    )
    FROM ProcessIeQty p
    WHERE p.requestId IN :callNos
    AND p.heatNo = :heatNo
""")
    TotalManufaturedQtyOfPoDto sumProcessQty(
            @Param("callNos") List<String> callNos,
            @Param("heatNo") String heatNo
    );


}

