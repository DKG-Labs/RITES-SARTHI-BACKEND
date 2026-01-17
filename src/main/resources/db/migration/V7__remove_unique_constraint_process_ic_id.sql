-- =====================================================
-- Migration V7: Remove UNIQUE constraint on ic_id in process_inspection_details
-- =====================================================
-- 
-- Purpose: Allow multiple lot records per Process Inspection Call
-- 
-- Before: process_inspection_details has UNIQUE constraint on ic_id (one-to-one)
-- After:  Multiple rows can have the same ic_id (one-to-many)
-- 
-- This allows storing multiple lots like:
-- | id | ic_id | lot_number | offered_qty |
-- |----|-------|------------|-------------|
-- | 48 | 178   | 001        | 5           |
-- | 49 | 179   | 001        | 10          |
-- | 50 | 184   | lot 2      | 20          |
-- | 51 | 189   | 12         | 20          |
-- | 52 | 190   | lot 1      | 20          |
-- | 53 | 197   | lot 23     | 5           |
-- | 54 | 198   | lot 2, lot 3| 30         |
-- =====================================================

-- Step 1: Drop the UNIQUE constraint on ic_id
-- First, find the constraint name
SET @constraint_name = (
    SELECT CONSTRAINT_NAME 
    FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE 
    WHERE TABLE_SCHEMA = DATABASE() 
    AND TABLE_NAME = 'process_inspection_details' 
    AND COLUMN_NAME = 'ic_id'
    AND CONSTRAINT_NAME != 'PRIMARY'
    LIMIT 1
);

-- Drop the constraint if it exists
SET @sql = IF(@constraint_name IS NOT NULL, 
    CONCAT('ALTER TABLE process_inspection_details DROP INDEX ', @constraint_name),
    'SELECT "No unique constraint found on ic_id" AS message'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Step 2: Verify the change
SELECT 
    COLUMN_NAME,
    COLUMN_KEY,
    EXTRA
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_SCHEMA = DATABASE()
AND TABLE_NAME = 'process_inspection_details'
AND COLUMN_NAME = 'ic_id';

-- =====================================================
-- NOTES:
-- =====================================================
-- 1. This migration allows multiple lots per Process IC
-- 2. Each lot will be stored as a separate row
-- 3. The ic_id column still has a foreign key to inspection_calls
-- 4. No data is lost - existing records remain unchanged
-- =====================================================

