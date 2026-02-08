-- Flyway Migration V8: Add shift_of_inspection to rm_heat_final_result
-- Date: 2026-02-08
-- Description: Add shift_of_inspection column to rm_heat_final_result table

ALTER TABLE rm_heat_final_result
ADD COLUMN shift_of_inspection VARCHAR(20);
