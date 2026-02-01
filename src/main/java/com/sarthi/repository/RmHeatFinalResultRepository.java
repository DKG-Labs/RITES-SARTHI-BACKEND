package com.sarthi.repository;

import com.sarthi.entity.RmHeatFinalResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repository for RmHeatFinalResult entity.
 */
@Repository
public interface RmHeatFinalResultRepository extends JpaRepository<RmHeatFinalResult, Long> {

    List<RmHeatFinalResult> findByInspectionCallNo(String inspectionCallNo);

    List<RmHeatFinalResult> findByInspectionCallNoAndHeatNo(String inspectionCallNo, String heatNo);

    void deleteByInspectionCallNo(String inspectionCallNo);

    @Query("""
                SELECT COALESCE(SUM(r.acceptedQtyMt), 0)
                FROM RmHeatFinalResult r
                WHERE r.inspectionCallNo IN :callNos
                AND r.heatNo = :heatNo
            """)
    BigDecimal sumRmAcceptedQty(
            @Param("callNos") List<String> callNos,
            @Param("heatNo") String heatNo);

    @Query("""
                SELECT COALESCE(SUM(r.weightAcceptedMt), 0)
                FROM RmHeatFinalResult r
                WHERE r.inspectionCallNo IN :callNos
                AND r.heatNo = :heatNo
            """)
    BigDecimal sumWeightAcceptedMt(
            @Param("callNos") List<String> callNos,
            @Param("heatNo") String heatNo);

    /**
     * Sum offered earlier for a heat across multiple inspection calls
     */
    // @Query("""
    // SELECT COALESCE(SUM(r.offeredEarlier), 0)
    // FROM RmHeatFinalResult r
    // WHERE r.inspectionCallNo IN :callNos
    // AND r.heatNo = :heatNo
    // """)
    // Integer sumOfferedEarlierByHeatNoAndInspectionCallNos(
    // @Param("heatNo") String heatNo,
    // @Param("callNos") List<String> callNos
    // );

}
