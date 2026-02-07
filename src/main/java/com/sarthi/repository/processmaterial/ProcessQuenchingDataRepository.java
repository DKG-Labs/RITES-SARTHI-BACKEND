package com.sarthi.repository.processmaterial;

import com.sarthi.entity.processmaterial.ProcessQuenchingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessQuenchingDataRepository extends JpaRepository<ProcessQuenchingData, Long> {

    List<ProcessQuenchingData> findByInspectionCallNo(String inspectionCallNo);

    List<ProcessQuenchingData> findByInspectionCallNoAndPoNoAndLineNo(
            String inspectionCallNo, String poNo, String lineNo);

    List<ProcessQuenchingData> findByInspectionCallNoAndShift(String inspectionCallNo, String shift);

    Optional<ProcessQuenchingData> findByInspectionCallNoAndShiftAndHourIndex(
            String inspectionCallNo, String shift, Integer hourIndex);

    void deleteByInspectionCallNo(String inspectionCallNo);

    @Query("""
            SELECT COALESCE(SUM(p.boxGaugeRejected),0)
            FROM ProcessQuenchingData p
            WHERE p.inspectionCallNo = :callNo
            AND p.lotNo = :lotNo
            AND p.shift = :shift
            AND DATE(p.createdAt) = :date
            """)
    Integer getQuenchingBoxGaugeSum(
            String callNo,
            String lotNo,
            String shift,
            LocalDate date
    );

    @Query("""
            SELECT
            COALESCE(SUM(p.flatBearingAreaRejected),0),
            COALESCE(SUM(p.fallingGaugeRejected),0)
            FROM ProcessQuenchingData p
            WHERE p.inspectionCallNo = :callNo
            AND p.lotNo = :lotNo
            AND p.shift = :shift
            AND DATE(p.createdAt) = :date
            """)
    Object[] getQuenchingDimensionalSum(
            String callNo,
            String lotNo,
            String shift,
            LocalDate date
    );

    @Query("""
            SELECT COALESCE(SUM(p.flatBearingAreaRejected),0)
            FROM ProcessQuenchingData p
            WHERE p.inspectionCallNo = :callNo
            AND p.lotNo = :lotNo
            AND p.shift = :shift
            AND DATE(p.createdAt) = :date
            """)
    Integer getQuenchingFlatBearingSum(
            String callNo,
            String lotNo,
            String shift,
            LocalDate date
    );

    @Query("""
            SELECT COALESCE(SUM(p.fallingGaugeRejected),0)
            FROM ProcessQuenchingData p
            WHERE p.inspectionCallNo = :callNo
            AND p.lotNo = :lotNo
            AND p.shift = :shift
            AND DATE(p.createdAt) = :date
            """)
    Integer getQuenchingFallingGaugeSum(
            String callNo,
            String lotNo,
            String shift,
            LocalDate date
    );
}