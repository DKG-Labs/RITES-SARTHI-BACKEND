-- ============================================================
-- Migration: Add Audit Fields to Raw Material Tables
-- Tables: rm_dimensional_check, rm_visual_inspection, rm_packing_storage
-- Description: Add audit trail fields (created_by, updated_by, updated_at)
--              to all Raw Material inspection tables for complete audit tracking
-- ============================================================

-- ============================================================
-- TABLE: rm_dimensional_check
-- Add audit fields for dimensional check data
-- ============================================================
ALTER TABLE rm_dimensional_check 
ADD COLUMN created_by VARCHAR(100) NULL AFTER diameter,
ADD COLUMN updated_by VARCHAR(100) NULL AFTER created_at,
ADD COLUMN updated_at DATETIME NULL AFTER updated_by;

-- Create indexes for audit fields
CREATE INDEX idx_rdc_created_by ON rm_dimensional_check(created_by);
CREATE INDEX idx_rdc_updated_by ON rm_dimensional_check(updated_by);

-- ============================================================
-- TABLE: rm_visual_inspection
-- Add missing audit fields (updated_by, updated_at)
-- ============================================================
ALTER TABLE rm_visual_inspection 
ADD COLUMN updated_by VARCHAR(100) NULL AFTER created_at,
ADD COLUMN updated_at DATETIME NULL AFTER updated_by;

-- Create indexes for audit fields
CREATE INDEX idx_rvi_updated_by ON rm_visual_inspection(updated_by);

-- ============================================================
-- TABLE: rm_packing_storage
-- Add audit fields for packing storage data
-- ============================================================
ALTER TABLE rm_packing_storage 
ADD COLUMN created_by VARCHAR(100) NULL AFTER remarks,
ADD COLUMN updated_by VARCHAR(100) NULL AFTER created_at,
ADD COLUMN updated_at DATETIME NULL AFTER updated_by;

-- Create indexes for audit fields
CREATE INDEX idx_rps_created_by ON rm_packing_storage(created_by);
CREATE INDEX idx_rps_updated_by ON rm_packing_storage(updated_by);

-- Set default value for updated_at to current timestamp for existing records
UPDATE rm_dimensional_check SET updated_at = created_at WHERE updated_at IS NULL;
UPDATE rm_visual_inspection SET updated_at = created_at WHERE updated_at IS NULL;
UPDATE rm_packing_storage SET updated_at = created_at WHERE updated_at IS NULL;

