-- =============================================
-- Raw Material Inspection Tables
-- Run this script to create all RM inspection tables
-- =============================================

-- 1. RM Inspection Summary (Pre-inspection data + Inspector details)
CREATE TABLE IF NOT EXISTS rm_inspection_summary (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL UNIQUE,
    total_heats_offered INT,
    total_qty_offered_mt DECIMAL(12,4),
    number_of_bundles INT,
    number_of_erc INT,
    product_model VARCHAR(20),
    po_no VARCHAR(50),
    po_date DATE,
    vendor_name VARCHAR(200),
    place_of_inspection VARCHAR(200),
    source_of_raw_material VARCHAR(100),
    finished_by VARCHAR(100),
    finished_at DATETIME,
    inspection_date DATE,
    shift_of_inspection VARCHAR(20),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_rm_sum_call_no (inspection_call_no)
);

-- 2. RM Heat Final Result (Per-heat final status and pre-inspection data)
CREATE TABLE IF NOT EXISTS rm_heat_final_result (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    heat_index INT,
    heat_no VARCHAR(50) NOT NULL,
    tc_no VARCHAR(50),
    tc_date DATE,
    manufacturer_name VARCHAR(200),
    invoice_number VARCHAR(50),
    invoice_date DATE,
    sub_po_number VARCHAR(50),
    sub_po_date DATE,
    sub_po_qty DECIMAL(12,4),
    total_value_of_po VARCHAR(50),
    tc_quantity DECIMAL(12,4),
    offered_qty DECIMAL(12,4),
    color_code VARCHAR(50),
    status VARCHAR(20) NOT NULL,
    weight_offered_mt DECIMAL(12,4),
    weight_accepted_mt DECIMAL(12,4),
    weight_rejected_mt DECIMAL(12,4),
    calibration_status VARCHAR(20),
    visual_status VARCHAR(20),
    dimensional_status VARCHAR(20),
    material_test_status VARCHAR(20),
    packing_status VARCHAR(20),
    remarks TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_rm_heat_call_no (inspection_call_no),
    INDEX idx_rm_heat_heat_no (heat_no),
    INDEX idx_rm_heat_status (status)
);

-- 3. RM Visual Inspection (Defects per heat)
CREATE TABLE IF NOT EXISTS rm_visual_inspection (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    heat_no VARCHAR(50) NOT NULL,
    heat_index INT,
    defect_name VARCHAR(100) NOT NULL,
    is_selected BOOLEAN DEFAULT FALSE,
    defect_length_mm DECIMAL(10,2),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_rm_visual_call_no (inspection_call_no),
    INDEX idx_rm_visual_heat_no (heat_no)
);

-- 4. RM Dimensional Check (20 samples per heat)
CREATE TABLE IF NOT EXISTS rm_dimensional_check (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    heat_no VARCHAR(50) NOT NULL,
    heat_index INT,
    sample_number INT NOT NULL,
    diameter DECIMAL(10,4),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_rm_dim_call_no (inspection_call_no),
    INDEX idx_rm_dim_heat_no (heat_no)
);

-- 5. RM Material Testing (2 samples per heat)
CREATE TABLE IF NOT EXISTS rm_material_testing (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    heat_no VARCHAR(50) NOT NULL,
    heat_index INT,
    sample_number INT NOT NULL,
    carbon_percent DECIMAL(6,4),
    silicon_percent DECIMAL(6,4),
    manganese_percent DECIMAL(6,4),
    phosphorus_percent DECIMAL(6,4),
    sulphur_percent DECIMAL(6,4),
    grain_size DECIMAL(4,1),
    hardness DECIMAL(6,2),
    decarb DECIMAL(6,4),
    inclusion_a DECIMAL(4,2),
    inclusion_b DECIMAL(4,2),
    inclusion_c DECIMAL(4,2),
    inclusion_d DECIMAL(4,2),
    remarks TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_rm_mat_call_no (inspection_call_no),
    INDEX idx_rm_mat_heat_no (heat_no)
);

-- 6. RM Packing & Storage (Single record per inspection)
CREATE TABLE IF NOT EXISTS rm_packing_storage (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL UNIQUE,
    bundling_secure VARCHAR(10),
    tags_attached VARCHAR(10),
    labels_correct VARCHAR(10),
    protection_adequate VARCHAR(10),
    storage_condition VARCHAR(10),
    moisture_protection VARCHAR(10),
    stacking_proper VARCHAR(10),
    remarks TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_rm_pack_call_no (inspection_call_no)
);

-- 7. RM Calibration Documents (RDSO approval + ladle values per heat)
CREATE TABLE IF NOT EXISTS rm_calibration_documents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    heat_no VARCHAR(50) NOT NULL,
    heat_index INT,
    rdso_approval_id VARCHAR(50),
    rdso_valid_from DATE,
    rdso_valid_to DATE,
    gauges_available BOOLEAN DEFAULT FALSE,
    ladle_carbon_percent DECIMAL(6,4),
    ladle_silicon_percent DECIMAL(6,4),
    ladle_manganese_percent DECIMAL(6,4),
    ladle_phosphorus_percent DECIMAL(6,4),
    ladle_sulphur_percent DECIMAL(6,4),
    vendor_verified BOOLEAN DEFAULT FALSE,
    verified_by VARCHAR(100),
    verified_at DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_rm_cal_call_no (inspection_call_no),
    INDEX idx_rm_cal_heat_no (heat_no)
);

