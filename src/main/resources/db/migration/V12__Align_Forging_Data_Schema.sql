-- Migration to align process_forging_data table with new granular fields
-- File: V12__Align_Forging_Data_Schema.sql

-- 1. Drop deprecated columns
ALTER TABLE process_forging_data DROP COLUMN forging_temp_3;
ALTER TABLE process_forging_data DROP COLUMN accepted_qty;
ALTER TABLE process_forging_data DROP COLUMN rejected_qty;

-- 2. Rename existing rejection columns for consistency
ALTER TABLE process_forging_data RENAME COLUMN forging_temperature_rejected TO forging_temp_rejected;
ALTER TABLE process_forging_data RENAME COLUMN forging_stabilisation_rejected TO forging_stabilisation_rejection_rejected;

-- 3. Add new granular measurement columns
ALTER TABLE process_forging_data ADD COLUMN forging_stabilisation_rejection_1 VARCHAR(30);
ALTER TABLE process_forging_data ADD COLUMN forging_stabilisation_rejection_2 VARCHAR(30);

ALTER TABLE process_forging_data ADD COLUMN improper_forging_1 VARCHAR(30);
ALTER TABLE process_forging_data ADD COLUMN improper_forging_2 VARCHAR(30);

ALTER TABLE process_forging_data ADD COLUMN forging_defect_1 VARCHAR(30);
ALTER TABLE process_forging_data ADD COLUMN forging_defect_2 VARCHAR(30);

ALTER TABLE process_forging_data ADD COLUMN embossing_defect_1 VARCHAR(30);
ALTER TABLE process_forging_data ADD COLUMN embossing_defect_2 VARCHAR(30);
