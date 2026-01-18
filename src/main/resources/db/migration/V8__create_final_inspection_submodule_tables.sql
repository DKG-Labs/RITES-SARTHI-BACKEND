-- =====================================================
-- Migration V8: Create Final Inspection Submodule Tables
-- =====================================================
-- Creates tables for all final inspection submodules
-- Each table includes audit fields: created_by, updated_by, created_at, updated_at
-- =====================================================

-- ===== 1. CALIBRATION & DOCUMENTS TABLE =====
CREATE TABLE IF NOT EXISTS final_calibration_documents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    lot_no VARCHAR(100),
    heat_no VARCHAR(100),
    instrument_name VARCHAR(200),
    calibration_status VARCHAR(50),
    calibration_date DATE,
    next_calibration_date DATE,
    certificate_no VARCHAR(100),
    remarks TEXT,
    created_by VARCHAR(100),
    created_at DATETIME NOT NULL,
    updated_by VARCHAR(100),
    updated_at DATETIME,
    
    INDEX idx_final_calib_call_no (inspection_call_no),
    INDEX idx_final_calib_lot_no (lot_no),
    INDEX idx_final_calib_heat_no (heat_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===== 2. VISUAL & DIMENSIONAL TABLE =====
CREATE TABLE IF NOT EXISTS final_visual_dimensional (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    lot_no VARCHAR(100),
    heat_no VARCHAR(100),
    sample_no INT,
    surface_condition VARCHAR(100),
    dimension_check VARCHAR(50),
    visual_defects VARCHAR(500),
    remarks TEXT,
    created_by VARCHAR(100),
    created_at DATETIME NOT NULL,
    updated_by VARCHAR(100),
    updated_at DATETIME,
    
    INDEX idx_final_visual_call_no (inspection_call_no),
    INDEX idx_final_visual_lot_no (lot_no),
    INDEX idx_final_visual_heat_no (heat_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===== 3. CHEMICAL ANALYSIS TABLE =====
CREATE TABLE IF NOT EXISTS final_chemical_analysis (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    lot_no VARCHAR(100),
    heat_no VARCHAR(100),
    sample_no INT,
    carbon_percent DECIMAL(5,3),
    silicon_percent DECIMAL(5,3),
    manganese_percent DECIMAL(5,3),
    sulphur_percent DECIMAL(5,3),
    phosphorus_percent DECIMAL(5,3),
    remarks TEXT,
    created_by VARCHAR(100),
    created_at DATETIME NOT NULL,
    updated_by VARCHAR(100),
    updated_at DATETIME,
    
    INDEX idx_final_chem_call_no (inspection_call_no),
    INDEX idx_final_chem_lot_no (lot_no),
    INDEX idx_final_chem_heat_no (heat_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===== 4. HARDNESS TEST TABLE =====
CREATE TABLE IF NOT EXISTS final_hardness_test (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    lot_no VARCHAR(100),
    heat_no VARCHAR(100),
    sample_no INT,
    hardness_1st_sample DECIMAL(5,2),
    hardness_2nd_sample DECIMAL(5,2),
    status VARCHAR(50),
    remarks TEXT,
    created_by VARCHAR(100),
    created_at DATETIME NOT NULL,
    updated_by VARCHAR(100),
    updated_at DATETIME,

    INDEX idx_final_hard_call_no (inspection_call_no),
    INDEX idx_final_hard_lot_no (lot_no),
    INDEX idx_final_hard_heat_no (heat_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===== 5. INCLUSION & DECARB TABLE =====
CREATE TABLE IF NOT EXISTS final_inclusion_rating (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    lot_no VARCHAR(100),
    heat_no VARCHAR(100),
    sample_no INT,
    microstructure_1st VARCHAR(100),
    microstructure_2nd VARCHAR(100),
    decarb_1st DECIMAL(5,2),
    decarb_2nd DECIMAL(5,2),
    inclusion_a_rating VARCHAR(50),
    inclusion_b_rating VARCHAR(50),
    inclusion_c_rating VARCHAR(50),
    inclusion_d_rating VARCHAR(50),
    inclusion_type VARCHAR(50),
    freedom_from_defects VARCHAR(50),
    remarks TEXT,
    created_by VARCHAR(100),
    created_at DATETIME NOT NULL,
    updated_by VARCHAR(100),
    updated_at DATETIME,

    INDEX idx_final_incl_call_no (inspection_call_no),
    INDEX idx_final_incl_lot_no (lot_no),
    INDEX idx_final_incl_heat_no (heat_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===== 6. DEFLECTION TEST TABLE =====
CREATE TABLE IF NOT EXISTS final_application_deflection (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    lot_no VARCHAR(100),
    heat_no VARCHAR(100),
    sample_no INT,
    deflection_value DECIMAL(8,2),
    load_applied DECIMAL(8,2),
    status VARCHAR(50),
    remarks TEXT,
    created_by VARCHAR(100),
    created_at DATETIME NOT NULL,
    updated_by VARCHAR(100),
    updated_at DATETIME,

    INDEX idx_final_defl_call_no (inspection_call_no),
    INDEX idx_final_defl_lot_no (lot_no),
    INDEX idx_final_defl_heat_no (heat_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===== 7. WEIGHT TEST TABLE =====
CREATE TABLE IF NOT EXISTS final_weight_test (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    lot_no VARCHAR(100),
    heat_no VARCHAR(100),
    sample_no INT,
    weight_value DECIMAL(10,3),
    min_weight DECIMAL(10,3),
    max_weight DECIMAL(10,3),
    status VARCHAR(50),
    remarks TEXT,
    created_by VARCHAR(100),
    created_at DATETIME NOT NULL,
    updated_by VARCHAR(100),
    updated_at DATETIME,

    INDEX idx_final_weight_call_no (inspection_call_no),
    INDEX idx_final_weight_lot_no (lot_no),
    INDEX idx_final_weight_heat_no (heat_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===== 8. TOE LOAD TEST TABLE =====
CREATE TABLE IF NOT EXISTS final_toe_load_test (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    lot_no VARCHAR(100),
    heat_no VARCHAR(100),
    sample_no INT,
    toe_load_value DECIMAL(10,2),
    min_load DECIMAL(10,2),
    max_load DECIMAL(10,2),
    status VARCHAR(50),
    remarks TEXT,
    created_by VARCHAR(100),
    created_at DATETIME NOT NULL,
    updated_by VARCHAR(100),
    updated_at DATETIME,

    INDEX idx_final_toe_call_no (inspection_call_no),
    INDEX idx_final_toe_lot_no (lot_no),
    INDEX idx_final_toe_heat_no (heat_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

