-- =====================================================
-- Migration V7: Add Audit Fields to rm_heat_final_result
-- =====================================================
-- Description: Add created_by and updated_by columns to rm_heat_final_result table
--              for complete audit trail tracking
-- =====================================================

-- Add audit fields to rm_heat_final_result table
-- Note: Using separate ALTER statements to handle column existence checks
ALTER TABLE rm_heat_final_result
ADD COLUMN created_by VARCHAR(100) NULL COMMENT 'User ID who created this record';

ALTER TABLE rm_heat_final_result
ADD COLUMN updated_by VARCHAR(100) NULL COMMENT 'User ID who last updated this record';

-- Create indexes for audit fields
CREATE INDEX idx_rmhfr_created_by ON rm_heat_final_result(created_by);
CREATE INDEX idx_rmhfr_updated_by ON rm_heat_final_result(updated_by);

-- =====================================================
-- Final Table Structure (22 columns):
-- =====================================================
-- 1.  id (BIGINT, Primary Key, Auto Increment)
-- 2.  inspection_call_no (VARCHAR(50), NOT NULL)
-- 3.  heat_no (VARCHAR(50), NOT NULL)
-- 4.  weight_offered_mt (DECIMAL(12,4))
-- 5.  weight_accepted_mt (DECIMAL(12,4))
-- 6.  weight_rejected_mt (DECIMAL(12,4))
-- 7.  calibration_status (VARCHAR(20))
-- 8.  visual_status (VARCHAR(20))
-- 9.  dimensional_status (VARCHAR(20))
-- 10. material_test_status (VARCHAR(20))
-- 11. packing_status (VARCHAR(20))
-- 12. status (VARCHAR(20))
-- 13. overall_status (VARCHAR(20))
-- 14. total_heats_offered (INT)
-- 15. total_qty_offered_mt (DECIMAL(12,4))
-- 16. no_of_bundles (INT)
-- 17. no_of_erc_finished (INT)
-- 18. remarks (TEXT)
-- 19. created_at (DATETIME)
-- 20. updated_at (DATETIME)
-- 21. created_by (VARCHAR(100)) -- NEW
-- 22. updated_by (VARCHAR(100)) -- NEW
-- =====================================================

