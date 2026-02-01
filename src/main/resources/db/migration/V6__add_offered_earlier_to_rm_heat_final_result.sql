-- =====================================================
-- Flyway Migration V6: Add offered_earlier column to rm_heat_final_result
-- =====================================================
-- This migration adds the offered_earlier column to track
-- cumulative quantities offered in previous process inspection calls
-- for the same heat number and PO combination.
--
-- Business Logic:
-- - offered_earlier: Total quantity offered in previous process ICs for this heat
-- - Future Balance = (Max ERC - Manufactured) - offered_earlier
-- - Maximum allowed declared quantity = Future Balance
-- =====================================================

-- Add offered_earlier column
ALTER TABLE rm_heat_final_result
ADD COLUMN IF NOT EXISTS offered_earlier INT DEFAULT 0
COMMENT 'Cumulative quantity offered in previous process inspection calls for this heat number';

-- Add index for better query performance when calculating offered_earlier
ALTER TABLE rm_heat_final_result
ADD INDEX IF NOT EXISTS idx_rm_heat_po_heat (inspection_call_no, heat_no);

-- =====================================================
-- Updated Table Structure (21 columns):
-- =====================================================
-- 1.  id (BIGINT, Primary Key, Auto Increment)
-- 2.  inspection_call_no (VARCHAR(50), NOT NULL)
-- 3.  heat_no (VARCHAR(50), NOT NULL)
-- 4.  weight_offered_mt (DECIMAL(12,4))
-- 5.  weight_accepted_mt (DECIMAL(12,4))
-- 6.  weight_rejected_mt (DECIMAL(12,4))
-- 7.  accepted_qty_mt (DECIMAL(12,4))
-- 8.  calibration_status (VARCHAR(20))
-- 9.  visual_status (VARCHAR(20))
-- 10. dimensional_status (VARCHAR(20))
-- 11. material_test_status (VARCHAR(20))
-- 12. packing_status (VARCHAR(20))
-- 13. status (VARCHAR(20))
-- 14. overall_status (VARCHAR(20))
-- 15. total_heats_offered (INT)
-- 16. total_qty_offered_mt (DECIMAL(12,4))
-- 17. no_of_bundles (INT)
-- 18. no_of_erc_finished (INT)
-- 19. offered_earlier (INT) -- NEW
-- 20. remarks (TEXT)
-- 21. created_at (DATETIME)
-- 22. updated_at (DATETIME)
-- 23. created_by (VARCHAR(100))
-- 24. updated_by (VARCHAR(100))
-- =====================================================

