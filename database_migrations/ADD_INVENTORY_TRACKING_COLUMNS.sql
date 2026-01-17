-- ============================================================
-- DATABASE MIGRATION: ADD INVENTORY TRACKING COLUMNS
-- ============================================================
-- Description: Add offered_quantity and qty_left_for_inspection columns
--              to inventory_entries table for tracking inspection call usage
-- Date: 2026-01-16
-- ============================================================

-- ============================================================
-- Step 1: ADD offered_quantity COLUMN
-- ============================================================
-- This column tracks the total quantity offered across all inspection calls
-- Default value is 0 for existing records
-- ============================================================

ALTER TABLE inventory_entries
ADD COLUMN offered_quantity DECIMAL(12,3) NOT NULL DEFAULT 0.000
COMMENT 'Total quantity offered across all inspection calls';

-- ============================================================
-- Step 2: ADD qty_left_for_inspection COLUMN
-- ============================================================
-- This column tracks the remaining quantity available for future inspection calls
-- Calculated as: tc_quantity - offered_quantity
-- For existing records, initialize to tc_quantity (since offered_quantity = 0)
-- ============================================================

ALTER TABLE inventory_entries
ADD COLUMN qty_left_for_inspection DECIMAL(12,3) NOT NULL DEFAULT 0.000
COMMENT 'Remaining quantity available for inspection calls (tc_quantity - offered_quantity)';

-- ============================================================
-- Step 3: INITIALIZE qty_left_for_inspection FOR EXISTING RECORDS
-- ============================================================
-- Set qty_left_for_inspection = tc_quantity for all existing records
-- This ensures existing inventory entries have correct remaining quantities
-- ============================================================

UPDATE inventory_entries
SET qty_left_for_inspection = tc_quantity
WHERE qty_left_for_inspection = 0;

-- ============================================================
-- Step 4: ADD INDEX FOR PERFORMANCE
-- ============================================================
-- Add index on offered_quantity and qty_left_for_inspection for faster queries
-- ============================================================

CREATE INDEX idx_inventory_qty_left ON inventory_entries(qty_left_for_inspection);
CREATE INDEX idx_inventory_offered_qty ON inventory_entries(offered_quantity);

-- ============================================================
-- Step 5: VERIFY THE CHANGES
-- ============================================================
-- Check the schema to ensure columns were added correctly
-- ============================================================

SHOW COLUMNS FROM inventory_entries LIKE 'offered_quantity';
SHOW COLUMNS FROM inventory_entries LIKE 'qty_left_for_inspection';

-- Display sample data to verify initialization
SELECT
    id,
    heat_number,
    tc_number,
    tc_quantity,
    offered_quantity,
    qty_left_for_inspection,
    status
FROM inventory_entries
LIMIT 10;

-- ============================================================
-- ROLLBACK SCRIPT (if needed)
-- ============================================================
-- Uncomment and run these commands to rollback the changes
-- ============================================================

-- DROP INDEX idx_inventory_qty_left ON inventory_entries;
-- DROP INDEX idx_inventory_offered_qty ON inventory_entries;
-- ALTER TABLE inventory_entries DROP COLUMN qty_left_for_inspection;
-- ALTER TABLE inventory_entries DROP COLUMN offered_quantity;

