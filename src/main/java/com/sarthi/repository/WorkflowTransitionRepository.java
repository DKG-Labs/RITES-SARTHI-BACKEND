package com.sarthi.repository;

import com.sarthi.entity.WorkflowTransition;
import org.springframework.beans.PropertyValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowTransitionRepository extends JpaRepository<WorkflowTransition, Integer> {
    WorkflowTransition findByWorkflowIdAndRequestId(Integer workflowId, String requestId);

//    @Query("SELECT COUNT(w) FROM WorkflowTransition w " +
//            "WHERE w.assignedToUser = :ieUserId AND w.status IN ('VERIFIED','ASSIGNED','INITIATED')")
//    int countActiveCallsForIE(@Param("ieUserId") Integer ieUserId);
    @Query("SELECT COUNT(w) FROM WorkflowTransition w " +
        "WHERE w.workflowTransitionId IN (" +
        "   SELECT MAX(w2.workflowTransitionId) FROM WorkflowTransition w2 " +
        "   WHERE w2.assignedToUser = :ieUserId GROUP BY w2.requestId" +
        ") AND w.jobStatus IN ('ASSIGNED','IN_PROGRESS','VERIFIED','INITIATED')")
    int countActiveCallsForIE(@Param("ieUserId") Integer ieUserId);


    WorkflowTransition findTopByRequestIdOrderByWorkflowTransitionIdDesc(String requestId);
/*
    @Query("SELECT wt FROM WorkflowTransition wt " +
            "WHERE wt.workflowTransitionId IN (" +
            "    SELECT MAX(wt2.workflowTransitionId) " +
            "    FROM WorkflowTransition wt2 " +
            "    GROUP BY wt2.requestId" +
            ") " +
            "AND wt.nextRoleName = :roleName " +
            "AND wt.jobStatus IN ('IN_PROGRESS','VERIFIED','APPROVED','REGISTERED','Created','ASSIGNED','REJECTED','PAUSED')")
    List<WorkflowTransition> findPendingByRole(@Param("roleName") String roleName);

    */
  @Query("SELECT wt FROM WorkflowTransition wt " +
          "WHERE wt.workflowTransitionId IN (" +
          "   SELECT MAX(wt2.workflowTransitionId) " +
          "   FROM WorkflowTransition wt2 " +
          "   WHERE wt2.transitionId NOT IN (42,44,45) " +   // Exclude qty edit transitions
          "   GROUP BY wt2.requestId" +
          ") " +
          "AND wt.nextRoleName = :roleName " +
          "AND wt.jobStatus IN ('IN_PROGRESS','VERIFIED','APPROVED','REGISTERED','Created','ASSIGNED','REJECTED','PAUSED')")
  List<WorkflowTransition> findPendingByRole(@Param("roleName") String roleName);

    @Query("""
SELECT wt FROM WorkflowTransition wt
WHERE wt.workflowTransitionId IN (
    SELECT MAX(wt2.workflowTransitionId)
    FROM WorkflowTransition wt2
    WHERE wt2.transitionId NOT IN (42,44,45)
    GROUP BY wt2.requestId
)
AND wt.nextRoleName IN :roleNames
AND wt.jobStatus IN (
    'IN_PROGRESS','VERIFIED','APPROVED','REGISTERED',
    'Created','ASSIGNED','REJECTED','PAUSED'
)
""")
    List<WorkflowTransition> findPendingByRoles(
            @Param("roleNames") List<String> roleNames
    );



    @Query("SELECT wt FROM WorkflowTransition wt " +
            "WHERE wt.workflowTransitionId IN (" +
            "     SELECT MAX(wt2.workflowTransitionId) " +
            "     FROM WorkflowTransition wt2 " +
            "     WHERE wt2.transitionId IN (42, 44, 45) " +
            "     GROUP BY wt2.requestId" +
            ") " +
            "AND wt.nextRoleName = :roleName " +
            "ORDER BY wt.createdDate DESC")
    List<WorkflowTransition> findPendingQtyEditByRole(@Param("roleName") String roleName);

    List<WorkflowTransition> findByRequestId(String requestId);

    @Query("SELECT w FROM WorkflowTransition w " +
            "WHERE w.status = :status " +
            "AND w.requestId = :requestId " +
            "AND w.currentRoleName = :roleName")
    WorkflowTransition findByStatusRequestIdAndCurrentRoleName(
            @Param("status") String status,
            @Param("requestId") String requestId,
            @Param("roleName") String roleName
    );

    WorkflowTransition findTopByRequestIdAndStatus(String requestId, String initiateInspection);

    WorkflowTransition findTopByRequestIdAndStatusOrderByWorkflowTransitionIdDesc(String requestId, String callRegistered);

    @Query("SELECT w FROM WorkflowTransition w WHERE w.status = 'BLOCKED'")
    List<WorkflowTransition> findBlockedTransitions();

    List<WorkflowTransition> findAllByStatusAndCreatedBy(String status, Integer createdBy);

  @Query("""
    SELECT
        MIN(w.createdDate),
        MAX(
            CASE
                WHEN w.status = 'INSPECTION_COMPLETE_CONFIRM'
                THEN w.createdDate
                ELSE NULL
            END
        )
    FROM WorkflowTransition w
    WHERE w.requestId = :requestId
""")
  List<Object[]> findStartAndEndDateByRequestId(
          @Param("requestId") String requestId);


  @Query("""
    SELECT
        w.requestId,
        MIN(w.createdDate),
        MAX(
            CASE
                WHEN w.status = 'INSPECTION_COMPLETE_CONFIRM'
                THEN w.createdDate
            END
        )
    FROM WorkflowTransition w
    WHERE w.requestId IN :requestIds
    GROUP BY w.requestId
""")
  List<Object[]> findStartAndEndDateByRequestIds(
          @Param("requestIds") List<String> requestIds
  );

}