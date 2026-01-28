-- ============================================================
-- Migration: Add Separate Rejection Fields to Process Material Tables
-- Date: 2026-01-27
-- Description: Adds separate rejection fields for each measurement column
--              in Forging, Quenching, Tempering, Final Check, and Testing & Finishing sections
-- ============================================================

-- ============================================================
-- 1) FORGING DATA TABLE - Add 5 separate rejection fields
-- ============================================================
ALTER TABLE process_forging_data
ADD COLUMN forging_temperature_rejected INT DEFAULT 0 COMMENT 'Rejected count for Forging Temperature',
ADD COLUMN forging_stabilisation_rejected INT DEFAULT 0 COMMENT 'Rejected count for Forging Stabilisation',
ADD COLUMN improper_forging_rejected INT DEFAULT 0 COMMENT 'Rejected count for Improper Forging',
ADD COLUMN forging_defect_rejected INT DEFAULT 0 COMMENT 'Rejected count for Forging Defect',
ADD COLUMN embossing_defect_rejected INT DEFAULT 0 COMMENT 'Rejected count for Embossing Defect';

-- ============================================================
-- 2) QUENCHING DATA TABLE - Add 6 separate rejection fields
-- ============================================================
ALTER TABLE process_quenching_data
ADD COLUMN quenching_temperature_rejected INT DEFAULT 0 COMMENT 'Rejected count for Quenching Temperature',
ADD COLUMN quenching_duration_rejected INT DEFAULT 0 COMMENT 'Rejected count for Quenching Duration',
ADD COLUMN quenching_hardness_rejected INT DEFAULT 0 COMMENT 'Rejected count for Quenching Hardness',
ADD COLUMN box_gauge_rejected INT DEFAULT 0 COMMENT 'Rejected count for Box Gauge',
ADD COLUMN flat_bearing_area_rejected INT DEFAULT 0 COMMENT 'Rejected count for Flat Bearing Area',
ADD COLUMN falling_gauge_rejected INT DEFAULT 0 COMMENT 'Rejected count for Falling Gauge';

-- ============================================================
-- 3) TEMPERING DATA TABLE - Add 2 separate rejection fields
-- ============================================================
ALTER TABLE process_tempering_data
ADD COLUMN tempering_temperature_rejected INT DEFAULT 0 COMMENT 'Rejected count for Tempering Temperature',
ADD COLUMN tempering_duration_rejected INT DEFAULT 0 COMMENT 'Rejected count for Tempering Duration';

-- ============================================================
-- 4) FINAL CHECK DATA TABLE - Add 7 separate rejection fields
-- ============================================================
ALTER TABLE process_final_check_data
ADD COLUMN box_gauge_rejected INT DEFAULT 0 COMMENT 'Rejected count for Box Gauge',
ADD COLUMN flat_bearing_area_rejected INT DEFAULT 0 COMMENT 'Rejected count for Flat Bearing Area',
ADD COLUMN falling_gauge_rejected INT DEFAULT 0 COMMENT 'Rejected count for Falling Gauge',
ADD COLUMN surface_defect_rejected INT DEFAULT 0 COMMENT 'Rejected count for Surface Defect',
ADD COLUMN embossing_defect_rejected INT DEFAULT 0 COMMENT 'Rejected count for Embossing Defect',
ADD COLUMN marking_rejected INT DEFAULT 0 COMMENT 'Rejected count for Marking',
ADD COLUMN tempering_hardness_rejected INT DEFAULT 0 COMMENT 'Rejected count for Tempering Hardness';

-- ============================================================
-- 5) TESTING & FINISHING DATA TABLE - Create table if not exists
-- ============================================================
CREATE TABLE IF NOT EXISTS process_testing_finishing_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    po_no VARCHAR(50),
    line_no VARCHAR(50),
    shift VARCHAR(50),
    hour_index INT,
    hour_label VARCHAR(50),
    no_production BOOLEAN DEFAULT FALSE,
    lot_no VARCHAR(100),

    -- Measurement fields
    toe_load_1 DECIMAL(10,2),
    toe_load_2 DECIMAL(10,2),
    weight_1 DECIMAL(10,2),
    weight_2 DECIMAL(10,2),
    paint_identification_1 VARCHAR(100),
    paint_identification_2 VARCHAR(100),
    erc_coating_1 VARCHAR(100),
    erc_coating_2 VARCHAR(100),

    -- Quantity fields
    accepted_qty INT DEFAULT 0,
    rejected_qty INT DEFAULT 0,

    -- Separate rejection fields for each measurement
    toe_load_rejected INT DEFAULT 0 COMMENT 'Rejected count for Toe Load',
    weight_rejected INT DEFAULT 0 COMMENT 'Rejected count for Weight',
    paint_identification_rejected INT DEFAULT 0 COMMENT 'Rejected count for Paint Identification',
    erc_coating_rejected INT DEFAULT 0 COMMENT 'Rejected count for ERC Coating',

    -- Additional fields
    remarks TEXT,
    created_by VARCHAR(100),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- Indexes
    INDEX idx_proc_testing_call_no (inspection_call_no),
    INDEX idx_proc_testing_po_no (po_no),
    INDEX idx_proc_testing_line_no (line_no),
    INDEX idx_proc_testing_lot_no (lot_no),
    INDEX idx_proc_testing_rejection (toe_load_rejected)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- Create indexes for better query performance
-- ============================================================
CREATE INDEX idx_proc_forg_rejection ON process_forging_data(forging_temperature_rejected);
CREATE INDEX idx_proc_quench_rejection ON process_quenching_data(quenching_hardness_rejected);
CREATE INDEX idx_proc_temper_rejection ON process_tempering_data(tempering_temperature_rejected);
CREATE INDEX idx_proc_final_rejection ON process_final_check_data(box_gauge_rejected);

