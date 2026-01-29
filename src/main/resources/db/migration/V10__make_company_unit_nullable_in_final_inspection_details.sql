-- Migration to make company_id and unit_id nullable in final_inspection_details table
-- This allows Final IC to be created with NULL company_id/unit_id when the RM IC uses POI code instead
-- Aligns with V8 and V9 migrations that made these fields nullable in inspection_calls and process_inspection_details tables

ALTER TABLE final_inspection_details 
  MODIFY COLUMN company_id INT NULL COMMENT 'Company ID (same as RM IC & Process IC) - nullable when using POI code',
  MODIFY COLUMN unit_id INT NULL COMMENT 'Unit ID (same as RM IC & Process IC) - nullable when using POI code';

-- Verify the changes
DESCRIBE final_inspection_details;

