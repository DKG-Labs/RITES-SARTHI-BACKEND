package com.sarthi.repository;

import com.sarthi.entity.PoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoItemRepository extends JpaRepository<PoItem, Long> {

	/**
	 * Fetch PO items by PO header id.
	 */
	List<PoItem> findByPoHeader_Id(Long poHeaderId);
}
