-- Migration to make company_id and unit_id nullable in process_inspection_details table
-- This allows Process IC to be created with NULL company_id/unit_id when the RM IC uses POI code instead
-- Aligns with V8 migration that made these fields nullable in inspection_calls table

ALTER TABLE process_inspection_details 
  MODIFY COLUMN company_id INT NULL COMMENT 'Company ID (same as RM IC) - nullable when using POI code',
  MODIFY COLUMN unit_id INT NULL COMMENT 'Unit ID (same as RM IC) - nullable when using POI code';

-- Verify the changes
DESCRIBE process_inspection_details;

