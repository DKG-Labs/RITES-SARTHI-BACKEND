-- Migration script to update rm_packing_storage table to support per-heat data
-- This script modifies the table to allow multiple records per inspection call (one per heat)

-- Step 1: Remove the unique constraint on inspection_call_no
ALTER TABLE rm_packing_storage DROP INDEX inspection_call_no;

-- Step 2: Add heat_no and heat_index columns
ALTER TABLE rm_packing_storage 
ADD COLUMN heat_no VARCHAR(50) AFTER inspection_call_no,
ADD COLUMN heat_index INT AFTER heat_no;

-- Step 3: Add index on heat_no for faster queries
ALTER TABLE rm_packing_storage 
ADD INDEX idx_rm_pack_heat_no (heat_no);

-- Step 4: Add composite index on inspection_call_no and heat_no
ALTER TABLE rm_packing_storage 
ADD INDEX idx_rm_pack_call_heat (inspection_call_no, heat_no);

