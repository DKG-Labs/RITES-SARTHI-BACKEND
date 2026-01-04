package com.sarthi.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.sarthi.entity.IeControllingManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ieControllingManagerRepository extends JpaRepository<IeControllingManager, Long> {
    Optional<IeControllingManager> findByIeEmployeeCode(String ieEmployeeCode);
}
