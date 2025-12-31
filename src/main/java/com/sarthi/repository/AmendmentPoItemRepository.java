package com.sarthi.repository;

import com.sarthi.entity.CricsPos.AmendmentPoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmendmentPoItemRepository extends JpaRepository<AmendmentPoItem, Long> {


}
