package com.sarthi.repository;

import com.sarthi.entity.ProcessIeUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessIeUsersRepository extends JpaRepository<ProcessIeUsers, Long> {
    Optional<ProcessIeUsers> findByIeUserId(Long ieUserId);

    List<ProcessIeUsers> findAllByProcessUserId(Integer processIeUserId);
}
