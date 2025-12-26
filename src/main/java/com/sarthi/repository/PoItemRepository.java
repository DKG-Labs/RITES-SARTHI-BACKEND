package com.sarthi.repository;

import com.sarthi.entity.PoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoItemRepository extends JpaRepository<PoItem, Long> {
}
