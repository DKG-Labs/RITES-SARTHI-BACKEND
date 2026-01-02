package com.sarthi.repository;

import com.sarthi.entity.IEFieldsMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IeFieldsMappingRepository extends JpaRepository<IEFieldsMapping, Long> {
    Optional<IEFieldsMapping> findByPinCodeAndProductAndStage(String pinCode, String product, String stage);
}
