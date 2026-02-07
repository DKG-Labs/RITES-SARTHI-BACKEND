-- Migration to align process_final_check_data table with new granular fields
-- File: V15__Update_Final_Check_Data_Schema.sql

-- 1. Drop old columns
ALTER TABLE process_final_check_data DROP COLUMN visual_check_1;
ALTER TABLE process_final_check_data DROP COLUMN visual_check_2;
ALTER TABLE process_final_check_data DROP COLUMN dimension_check_1;
ALTER TABLE process_final_check_data DROP COLUMN dimension_check_2;
ALTER TABLE process_final_check_data DROP COLUMN hardness_check_1;
ALTER TABLE process_final_check_data DROP COLUMN hardness_check_2;

-- 2. Add new granular measurement columns (2 samples each)
ALTER TABLE process_final_check_data ADD COLUMN box_gauge_1 VARCHAR(50);
ALTER TABLE process_final_check_data ADD COLUMN box_gauge_2 VARCHAR(50);

ALTER TABLE process_final_check_data ADD COLUMN flat_bearing_area_1 VARCHAR(50);
ALTER TABLE process_final_check_data ADD COLUMN flat_bearing_area_2 VARCHAR(50);

ALTER TABLE process_final_check_data ADD COLUMN falling_gauge_1 VARCHAR(50);
ALTER TABLE process_final_check_data ADD COLUMN falling_gauge_2 VARCHAR(50);

ALTER TABLE process_final_check_data ADD COLUMN surface_defect_1 VARCHAR(50);
ALTER TABLE process_final_check_data ADD COLUMN surface_defect_2 VARCHAR(50);

ALTER TABLE process_final_check_data ADD COLUMN embossing_defect_1 VARCHAR(50);
ALTER TABLE process_final_check_data ADD COLUMN embossing_defect_2 VARCHAR(50);

ALTER TABLE process_final_check_data ADD COLUMN marking_1 VARCHAR(50);
ALTER TABLE process_final_check_data ADD COLUMN marking_2 VARCHAR(50);

ALTER TABLE process_final_check_data ADD COLUMN tempering_hardness_1 VARCHAR(50);
ALTER TABLE process_final_check_data ADD COLUMN tempering_hardness_2 VARCHAR(50);
