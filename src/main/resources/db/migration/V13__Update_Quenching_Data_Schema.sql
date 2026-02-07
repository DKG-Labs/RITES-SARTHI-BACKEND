-- Migration to align process_quenching_data table with new granular fields
-- File: V13__Update_Quenching_Data_Schema.sql

-- 1. Drop old columns
ALTER TABLE process_quenching_data DROP COLUMN quenching_temperature;
ALTER TABLE process_quenching_data DROP COLUMN quenching_duration;

-- 2. Add new granular measurement columns (2 samples each)
ALTER TABLE process_quenching_data ADD COLUMN quenching_temperature_1 DECIMAL(10,2);
ALTER TABLE process_quenching_data ADD COLUMN quenching_temperature_2 DECIMAL(10,2);

ALTER TABLE process_quenching_data ADD COLUMN quenching_duration_1 DECIMAL(10,2);
ALTER TABLE process_quenching_data ADD COLUMN quenching_duration_2 DECIMAL(10,2);

ALTER TABLE process_quenching_data ADD COLUMN box_gauge_1 VARCHAR(30);
ALTER TABLE process_quenching_data ADD COLUMN box_gauge_2 VARCHAR(30);

ALTER TABLE process_quenching_data ADD COLUMN flat_bearing_area_1 VARCHAR(30);
ALTER TABLE process_quenching_data ADD COLUMN flat_bearing_area_2 VARCHAR(30);

ALTER TABLE process_quenching_data ADD COLUMN falling_gauge_1 VARCHAR(30);
ALTER TABLE process_quenching_data ADD COLUMN falling_gauge_2 VARCHAR(30);
