package com.sarthi.repository;

import com.sarthi.entity.ProcessIeUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessIeUsersRepository extends JpaRepository<ProcessIeUsers, Long> {
    Optional<ProcessIeUsers> findByIeUserId(Long ieUserId);

    List<ProcessIeUsers> findAllByProcessUserId(Integer processIeUserId);

    @Query("""
SELECT piu.ieUserId
FROM ProcessIeUsers piu
JOIN IePoiMapping ipm
     ON ipm.ieUserId = piu.ieUserId
WHERE piu.processUserId = :processIeUserId
AND ipm.poiCode = :poiCode
""")
    List<Long> findIeUsersByProcessIeAndPoi(
            @Param("processIeUserId") Integer processIeUserId,
            @Param("poiCode") String poiCode
    );

    Optional<ProcessIeUsers>
    findTopByIeUserIdOrderByCreatedDateDesc(Long ieUserId);
}
