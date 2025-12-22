package com.sarthi.repository;

import com.sarthi.entity.InspectionSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for InspectionSchedule entity.
 */
@Repository
public interface InspectionScheduleRepository extends JpaRepository<InspectionSchedule, Long> {

    /**
     * Find schedule by call number.
     */
    Optional<InspectionSchedule> findByCallNo(String callNo);

    /**
     * Check if schedule exists for call number.
     */
    boolean existsByCallNo(String callNo);

    /**
     * Find all schedules by schedule date.
     */
    List<InspectionSchedule> findByScheduleDate(LocalDate scheduleDate);

    /**
     * Find all schedules by status.
     */
    List<InspectionSchedule> findByStatus(String status);

    /**
     * Count schedules for a specific date.
     */
    @Query("SELECT COUNT(s) FROM InspectionSchedule s WHERE s.scheduleDate = :date")
    long countByScheduleDate(@Param("date") LocalDate date);

    /**
     * Find all schedules ordered by schedule date descending.
     */
    List<InspectionSchedule> findAllByOrderByScheduleDateDesc();

    /**
     * Delete schedule by call number.
     */
    void deleteByCallNo(String callNo);
}

