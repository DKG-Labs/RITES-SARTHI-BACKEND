-- Migration: Add color_code column to rm_heat_quantities table
-- Purpose: Allow inspectors to manually enter color code for each heat number
-- Date: 2025-12-28

ALTER TABLE rm_heat_quantities 
ADD COLUMN color_code VARCHAR(50) NULL COMMENT 'Color code manually entered by inspector';

