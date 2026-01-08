-- =====================================================
-- Migration V4: Verify and fix place_of_inspection column
-- =====================================================
-- This migration ensures the place_of_inspection column exists
-- in the sub_po_details table with the correct definition
-- =====================================================

-- Add place_of_inspection column if it doesn't exist
-- This is a safety check in case V1 migration didn't run properly
ALTER TABLE sub_po_details
ADD COLUMN IF NOT EXISTS place_of_inspection VARCHAR(200) AFTER unit;

-- Verify the column exists by selecting from information_schema
-- (This is just for logging purposes - MySQL will execute it)
SELECT 
    COLUMN_NAME, 
    DATA_TYPE, 
    CHARACTER_MAXIMUM_LENGTH,
    IS_NULLABLE
FROM 
    INFORMATION_SCHEMA.COLUMNS
WHERE 
    TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'sub_po_details'
    AND COLUMN_NAME = 'place_of_inspection';

