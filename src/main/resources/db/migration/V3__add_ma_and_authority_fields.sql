-- =====================================================
-- Migration V3: Add missing fields and fix issues
-- =====================================================
-- 1. Add MA and authority fields to main_po_information
-- 2. Add type_of_erc field to inspection_call_details
-- =====================================================

-- ===== Section A: main_po_information table =====
-- Add new columns for MA and authority information
ALTER TABLE main_po_information
ADD COLUMN IF NOT EXISTS ma_no VARCHAR(100) AFTER vendor_name,
ADD COLUMN IF NOT EXISTS ma_date VARCHAR(255) AFTER ma_no,
ADD COLUMN IF NOT EXISTS purchasing_authority VARCHAR(200) AFTER ma_date,
ADD COLUMN IF NOT EXISTS bill_paying_officer VARCHAR(200) AFTER purchasing_authority;

-- Drop the old bpo column if it exists (we're replacing it with bill_paying_officer)
ALTER TABLE main_po_information
DROP COLUMN IF EXISTS bpo;

-- ===== Section B: inspection_call_details table =====
-- Add type_of_erc column for ERC type (Raw Material, Process, Final)
ALTER TABLE inspection_call_details
ADD COLUMN  type_of_erc VARCHAR(50) AFTER product_type;

ALTER TABLE sub_po_details
ADD COLUMN IF NOT EXISTS place_of_inspection VARCHAR(200) AFTER unit;
