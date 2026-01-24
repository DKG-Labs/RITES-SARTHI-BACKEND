-- ============================================================
-- Migration: Add Inclusion Types and Audit Fields
-- Table: rm_material_testing
-- Description: Add missing inclusion type fields (Thick/Thin) and 
--              audit trail fields (created_by, updated_by, updated_at)
-- ============================================================

-- Add Inclusion Type columns (Thick/Thin)
ALTER TABLE rm_material_testing 
ADD COLUMN inclusion_type_a VARCHAR(20) NULL AFTER inclusion_a,
ADD COLUMN inclusion_type_b VARCHAR(20) NULL AFTER inclusion_b,
ADD COLUMN inclusion_type_c VARCHAR(20) NULL AFTER inclusion_c,
ADD COLUMN inclusion_type_d VARCHAR(20) NULL AFTER inclusion_d;

-- Add Audit Fields
ALTER TABLE rm_material_testing 
ADD COLUMN created_by VARCHAR(100) NULL AFTER remarks,
ADD COLUMN updated_by VARCHAR(100) NULL AFTER created_at,
ADD COLUMN updated_at DATETIME NULL AFTER updated_by;

-- Set default value for updated_at to current timestamp for existing records
UPDATE rm_material_testing SET updated_at = created_at WHERE updated_at IS NULL;

-- Create indexes for audit fields
CREATE INDEX idx_rmt_created_by ON rm_material_testing(created_by);
CREATE INDEX idx_rmt_updated_by ON rm_material_testing(updated_by);

