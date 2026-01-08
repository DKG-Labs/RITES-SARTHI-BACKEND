-- =====================================================
-- ALTER TABLE: rm_heat_final_result
-- =====================================================
-- This script modifies the existing rm_heat_final_result table
-- to store only the essential fields for final inspection results
-- 
-- Run this in MySQL Workbench
-- =====================================================

USE your_database_name; -- Replace with your actual database name

-- Step 1: Drop unnecessary columns (Heat Pre-Inspection Data)
ALTER TABLE rm_heat_final_result
DROP COLUMN IF EXISTS heat_index,
DROP COLUMN IF EXISTS tc_no,
DROP COLUMN IF EXISTS tc_date,
DROP COLUMN IF EXISTS manufacturer_name,
DROP COLUMN IF EXISTS invoice_number,
DROP COLUMN IF EXISTS invoice_date,
DROP COLUMN IF EXISTS sub_po_number,
DROP COLUMN IF EXISTS sub_po_date,
DROP COLUMN IF EXISTS sub_po_qty,
DROP COLUMN IF EXISTS total_value_of_po,
DROP COLUMN IF EXISTS tc_quantity,
DROP COLUMN IF EXISTS offered_qty,
DROP COLUMN IF EXISTS color_code;

-- Step 2: Add new column for overall_status (if not exists)
ALTER TABLE rm_heat_final_result
ADD COLUMN IF NOT EXISTS overall_status VARCHAR(20) DEFAULT 'PENDING' COMMENT 'Overall inspection status: ACCEPTED, PARTIALLY_ACCEPTED, REJECTED, PENDING';

-- Step 3: Add Cumulative Data Summary columns
ALTER TABLE rm_heat_final_result
ADD COLUMN IF NOT EXISTS total_heats_offered INT DEFAULT 0 COMMENT 'Total number of heats offered in this inspection call',
ADD COLUMN IF NOT EXISTS total_qty_offered_mt DECIMAL(12, 4) DEFAULT 0.000 COMMENT 'Total quantity offered across all heats (MT)',
ADD COLUMN IF NOT EXISTS no_of_bundles INT DEFAULT 0 COMMENT 'Number of bundles',
ADD COLUMN IF NOT EXISTS no_of_erc_finished INT DEFAULT 0 COMMENT 'Number of ERC (Finished)';

-- Step 4: Add updated_at column if not exists
ALTER TABLE rm_heat_final_result
ADD COLUMN IF NOT EXISTS updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-- Step 5: Add index for overall_status
ALTER TABLE rm_heat_final_result
ADD INDEX IF NOT EXISTS idx_rm_heat_overall_status (overall_status);

-- Step 6: Add unique constraint to prevent duplicate heat entries per call
ALTER TABLE rm_heat_final_result
ADD CONSTRAINT unique_call_heat UNIQUE (inspection_call_no, heat_no);

-- =====================================================
-- Verification: Check the updated table structure
-- =====================================================
DESCRIBE rm_heat_final_result;

-- =====================================================
-- Verification: Check existing data
-- =====================================================
SELECT 
    inspection_call_no,
    heat_no,
    weight_offered_mt,
    weight_accepted_mt,
    weight_rejected_mt,
    calibration_status,
    visual_status,
    dimensional_status,
    material_test_status,
    packing_status,
    status,
    overall_status,
    total_heats_offered,
    total_qty_offered_mt,
    no_of_bundles,
    no_of_erc_finished,
    remarks
FROM rm_heat_final_result
ORDER BY inspection_call_no, heat_no;

-- =====================================================
-- Final Table Structure (17 fields):
-- =====================================================
-- 1. id (Primary Key)
-- 2. inspection_call_no
-- 3. heat_no
-- 4. weight_offered_mt
-- 5. weight_accepted_mt
-- 6. weight_rejected_mt
-- 7. calibration_status
-- 8. visual_status
-- 9. dimensional_status
-- 10. material_test_status
-- 11. packing_status
-- 12. status
-- 13. overall_status
-- 14. remarks
-- 15. total_heats_offered (Cumulative)
-- 16. total_qty_offered_mt (Cumulative)
-- 17. no_of_bundles (Cumulative)
-- 18. no_of_erc_finished (Cumulative)
-- 19. created_at (Audit)
-- 20. updated_at (Audit)
-- =====================================================

