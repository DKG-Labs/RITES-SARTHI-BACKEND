-- Migration to align process_tempering_data table with new granular fields
-- File: V14__Update_Tempering_Data_Schema.sql

-- 1. Drop old columns
ALTER TABLE process_tempering_data DROP COLUMN tempering_temperature;
ALTER TABLE process_tempering_data DROP COLUMN tempering_duration;

-- 2. Add new granular measurement columns (2 samples each)
ALTER TABLE process_tempering_data ADD COLUMN tempering_temperature_1 DECIMAL(10,2);
ALTER TABLE process_tempering_data ADD COLUMN tempering_temperature_2 DECIMAL(10,2);

ALTER TABLE process_tempering_data ADD COLUMN tempering_duration_1 DECIMAL(10,2);
ALTER TABLE process_tempering_data ADD COLUMN tempering_duration_2 DECIMAL(10,2);

ALTER TABLE process_tempering_data ADD COLUMN total_tempering_rejection INT DEFAULT 0;
