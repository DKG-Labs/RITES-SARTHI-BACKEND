-- =====================================================
-- Flyway Migration V5: Simplify rm_heat_final_result Table
-- =====================================================
-- This migration removes redundant Heat Pre-Inspection Data fields
-- and adds new cumulative summary fields to rm_heat_final_result
-- 
-- Changes:
-- 1. Drop 13 redundant columns (heat_index, tc_no, tc_date, etc.)
-- 2. Add overall_status column
-- 3. Add cumulative summary columns (total_heats_offered, etc.)
-- 4. Add updated_at timestamp column
-- 5. Add index for overall_status
-- 6. Add unique constraint for (inspection_call_no, heat_no)
-- =====================================================

-- Step 1: Drop unnecessary columns (Heat Pre-Inspection Data)
-- These fields are now stored in rm_heat_quantities table
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

-- Step 2: Add new column for overall_status
ALTER TABLE rm_heat_final_result
ADD COLUMN IF NOT EXISTS overall_status VARCHAR(20) DEFAULT 'PENDING' 
COMMENT 'Overall inspection status: ACCEPTED, PARTIALLY_ACCEPTED, REJECTED, PENDING';

-- Step 3: Add Cumulative Data Summary columns
ALTER TABLE rm_heat_final_result
ADD COLUMN IF NOT EXISTS total_heats_offered INT DEFAULT 0 
COMMENT 'Total number of heats offered in this inspection call',
ADD COLUMN IF NOT EXISTS total_qty_offered_mt DECIMAL(12, 4) DEFAULT 0.0000 
COMMENT 'Total quantity offered across all heats (MT)',
ADD COLUMN IF NOT EXISTS no_of_bundles INT DEFAULT 0 
COMMENT 'Number of bundles',
ADD COLUMN IF NOT EXISTS no_of_erc_finished INT DEFAULT 0 
COMMENT 'Number of ERC (Finished)';

-- Step 4: Add updated_at column if not exists
ALTER TABLE rm_heat_final_result
ADD COLUMN IF NOT EXISTS updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
COMMENT 'Last update timestamp';

-- Step 5: Add index for overall_status (for faster queries)
ALTER TABLE rm_heat_final_result
ADD INDEX IF NOT EXISTS idx_rm_heat_overall_status (overall_status);

-- Step 6: Add unique constraint to prevent duplicate heat entries per call
-- Note: This will fail if there are existing duplicates
-- Clean up duplicates before running this migration if needed
ALTER TABLE rm_heat_final_result
ADD CONSTRAINT unique_call_heat UNIQUE (inspection_call_no, heat_no);

-- =====================================================
-- Final Table Structure (20 columns):
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
-- 13. overall_status (VARCHAR(20)) -- NEW
-- 14. total_heats_offered (INT) -- NEW
-- 15. total_qty_offered_mt (DECIMAL(12,4)) -- NEW
-- 16. no_of_bundles (INT) -- NEW
-- 17. no_of_erc_finished (INT) -- NEW
-- 18. remarks (TEXT)
-- 19. created_at (DATETIME)
-- 20. updated_at (DATETIME) -- NEW
-- =====================================================

