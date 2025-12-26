-- =====================================================
-- PROCESS MATERIAL INSPECTION - COMPLETE DATABASE SCHEMA
-- Database: MySQL
-- Created: 2025-12-24
-- Description: Contains all table schemas for Process Material Inspection module
-- =====================================================

-- =====================================================
-- DROP EXISTING TABLES (if recreating)
-- =====================================================
-- DROP TABLE IF EXISTS process_production_line;
-- DROP TABLE IF EXISTS process_inspection_request;
-- DROP TABLE IF EXISTS process_calibration_documents;
-- DROP TABLE IF EXISTS process_static_periodic_check;
-- DROP TABLE IF EXISTS process_oil_tank_counter;
-- DROP TABLE IF EXISTS process_shearing_data;
-- DROP TABLE IF EXISTS process_turning_data;
-- DROP TABLE IF EXISTS process_mpi_data;
-- DROP TABLE IF EXISTS process_forging_data;
-- DROP TABLE IF EXISTS process_quenching_data;
-- DROP TABLE IF EXISTS process_tempering_data;
-- DROP TABLE IF EXISTS process_final_check_data;
-- DROP TABLE IF EXISTS process_summary_report;

-- =====================================================
-- TABLE 1: process_inspection_request
-- Main table for Process Material inspection requests
-- =====================================================
CREATE TABLE IF NOT EXISTS process_inspection_request (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    -- Inspection Call Information
    call_no VARCHAR(50) NOT NULL UNIQUE,
    call_date DATE NOT NULL,
    desired_inspection_date DATE,
    shift_of_inspection VARCHAR(20) COMMENT 'A, B, C, General',
    date_of_inspection DATE,

    -- PO Information
    po_no VARCHAR(50) NOT NULL,
    po_serial_no VARCHAR(50),
    po_date DATE,
    po_description VARCHAR(255),
    po_qty INT,
    po_unit VARCHAR(20) DEFAULT 'NOS',
    amendment_no VARCHAR(50),
    amendment_date DATE,

    -- Sub PO / Raw Material Reference
    sub_po_no VARCHAR(50),
    sub_po_date DATE,
    rm_ic_numbers VARCHAR(500) COMMENT 'Comma separated Raw Material IC numbers',

    -- Quantities
    call_qty INT,
    offered_qty INT,
    qty_already_inspected_rm INT DEFAULT 0,
    qty_already_inspected_process INT DEFAULT 0,
    qty_already_inspected_final INT DEFAULT 0,

    -- Company Information
    company_id INT,
    company_name VARCHAR(200),
    cin VARCHAR(50),
    vendor_contact_name VARCHAR(100),
    vendor_contact_phone VARCHAR(20),

    -- Unit / Manufacturer Information
    unit_id INT,
    unit_name VARCHAR(100),
    unit_address VARCHAR(500),
    unit_gstin VARCHAR(20),
    unit_contact_person VARCHAR(100),
    unit_role VARCHAR(50),
    place_of_inspection VARCHAR(500),

    -- Contractor / Manufacturer Details
    contractor_name VARCHAR(200),
    manufacturer_name VARCHAR(200),

    -- Product Details
    product_type VARCHAR(50) DEFAULT 'ERC Process',
    product_name VARCHAR(200),
    stage_of_inspection VARCHAR(50) DEFAULT 'Process Material Inspection',

    -- Chemical Composition (from Raw Material)
    rm_chemical_carbon DECIMAL(6,4),
    rm_chemical_manganese DECIMAL(6,4),
    rm_chemical_silicon DECIMAL(6,4),
    rm_chemical_sulphur DECIMAL(6,4),
    rm_chemical_phosphorus DECIMAL(6,4),
    rm_chemical_chromium DECIMAL(6,4),

    -- Heat Numbers (comma separated)
    rm_heat_numbers VARCHAR(500),
    rm_total_offered_qty_mt DECIMAL(10,3),
    rm_offered_qty_erc INT,

    -- Verification Flags
    section_a_verified BOOLEAN DEFAULT FALSE,
    section_b_verified BOOLEAN DEFAULT FALSE,
    section_c_verified BOOLEAN DEFAULT FALSE,
    section_d_verified BOOLEAN DEFAULT FALSE,
    cm_approval BOOLEAN DEFAULT FALSE,

    -- Status
    status VARCHAR(30) DEFAULT 'PENDING' COMMENT 'PENDING, INITIATED, IN_PROGRESS, COMPLETED, WITHHELD, CANCELLED',
    action_type VARCHAR(20) COMMENT 'WITHHELD, CANCELLED',
    action_reason VARCHAR(100),
    action_remarks TEXT,
    action_date DATETIME,

    -- Rate and Payment
    rate DECIMAL(15,2),
    delivery_period VARCHAR(50),
    billing_status VARCHAR(50),
    ic_issued BOOLEAN DEFAULT FALSE,
    ic_date DATE,

    -- Remarks
    remarks TEXT,

    -- Audit Fields
    created_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    initiated_by VARCHAR(100),
    initiated_at DATETIME,

    -- Indexes
    INDEX idx_process_call_no (call_no),
    INDEX idx_process_po_no (po_no),
    INDEX idx_process_status (status),
    INDEX idx_process_date (call_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- TABLE 2: process_production_line
-- Production line details for Process inspections
-- =====================================================
CREATE TABLE IF NOT EXISTS process_production_line (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    -- Reference to process inspection request
    process_inspection_id BIGINT NOT NULL,

    -- Line Details
    line_number INT NOT NULL,
    inspection_call_no VARCHAR(50) NOT NULL,
    po_number VARCHAR(50) NOT NULL,
    po_date DATE,

    -- Raw Material References
    rm_ic_number VARCHAR(100) COMMENT 'Raw Material IC Number for this line',
    product_type VARCHAR(50) DEFAULT 'ERC Process',

    -- Lot and Heat Information
    lot_number VARCHAR(50) NOT NULL,
    heat_number VARCHAR(50) NOT NULL,
    offered_qty INT,

    -- TC (Test Certificate) Details
    tc_number VARCHAR(50),
    tc_date DATE,
    manufacturer VARCHAR(200),

    -- Stage Results (auto-populated from sub-modules)
    shearing_manufactured INT DEFAULT 0,
    shearing_accepted INT DEFAULT 0,
    shearing_rejected INT DEFAULT 0,

    turning_manufactured INT DEFAULT 0,
    turning_accepted INT DEFAULT 0,
    turning_rejected INT DEFAULT 0,

    mpi_manufactured INT DEFAULT 0,
    mpi_accepted INT DEFAULT 0,
    mpi_rejected INT DEFAULT 0,

    forging_manufactured INT DEFAULT 0,
    forging_accepted INT DEFAULT 0,
    forging_rejected INT DEFAULT 0,

    tempering_manufactured INT DEFAULT 0,
    tempering_accepted INT DEFAULT 0,
    tempering_rejected INT DEFAULT 0,

    dimensions_manufactured INT DEFAULT 0,
    dimensions_accepted INT DEFAULT 0,
    dimensions_rejected INT DEFAULT 0,

    hardness_manufactured INT DEFAULT 0,
    hardness_accepted INT DEFAULT 0,
    hardness_rejected INT DEFAULT 0,

    toe_load_manufactured INT DEFAULT 0,
    toe_load_accepted INT DEFAULT 0,
    toe_load_rejected INT DEFAULT 0,

    visual_manufactured INT DEFAULT 0,
    visual_accepted INT DEFAULT 0,
    visual_rejected INT DEFAULT 0,

    -- Final Results
    total_manufactured INT DEFAULT 0,
    total_accepted INT DEFAULT 0,
    total_rejected INT DEFAULT 0,

    -- Status and Remarks
    status VARCHAR(20) DEFAULT 'PENDING' COMMENT 'PENDING, IN_PROGRESS, ACCEPTED, REJECTED',
    inspection_result VARCHAR(20) DEFAULT 'PENDING' COMMENT 'PENDING, ACCEPTED, REJECTED',
    remarks TEXT,

    -- Audit Fields
    created_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- Foreign Key
    CONSTRAINT fk_process_inspection FOREIGN KEY (process_inspection_id)
        REFERENCES process_inspection_request(id) ON DELETE CASCADE,

    -- Indexes
    INDEX idx_line_process_id (process_inspection_id),
    INDEX idx_line_number (line_number),
    INDEX idx_line_lot (lot_number),
    INDEX idx_line_heat (heat_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- TABLE 3: process_calibration_documents
-- Calibration & Documents verification table
-- =====================================================
CREATE TABLE IF NOT EXISTS process_calibration_documents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    po_no VARCHAR(50) NOT NULL,
    line_no VARCHAR(20) NOT NULL,
    instrument_name VARCHAR(100),
    instrument_id VARCHAR(50),
    calibration_status VARCHAR(20),
    calibration_valid_from DATE,
    calibration_valid_to DATE,
    is_verified BOOLEAN DEFAULT FALSE,
    remarks VARCHAR(500),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    INDEX idx_proc_cal_call_no (inspection_call_no),
    INDEX idx_proc_cal_po_no (po_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- TABLE 4: process_static_periodic_check
-- Static Periodic Check table for equipment verification
-- =====================================================
CREATE TABLE IF NOT EXISTS process_static_periodic_check (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    po_no VARCHAR(50) NOT NULL,
    line_no VARCHAR(20) NOT NULL,
    shearing_press_check BOOLEAN DEFAULT FALSE,
    forging_press_check BOOLEAN DEFAULT FALSE,
    reheating_furnace_check BOOLEAN DEFAULT FALSE,
    quenching_time_check BOOLEAN DEFAULT FALSE,
    all_checks_passed BOOLEAN DEFAULT FALSE,
    remarks VARCHAR(500),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    INDEX idx_proc_static_call_no (inspection_call_no),
    INDEX idx_proc_static_po_no (po_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- TABLE 5: process_oil_tank_counter
-- Oil Tank Counter tracking table
-- =====================================================
CREATE TABLE IF NOT EXISTS process_oil_tank_counter (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    po_no VARCHAR(50) NOT NULL,
    line_no VARCHAR(20) NOT NULL,
    oil_tank_counter INT DEFAULT 0,
    cleaning_done BOOLEAN DEFAULT FALSE,
    cleaning_done_at DATETIME,
    is_locked BOOLEAN DEFAULT FALSE,
    counter_status VARCHAR(20),
    remarks VARCHAR(500),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    INDEX idx_proc_oil_call_no (inspection_call_no),
    INDEX idx_proc_oil_po_no (po_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- =====================================================
-- TABLE 6: process_shearing_data
-- Shearing Section data for 8-Hour Grid
-- =====================================================
CREATE TABLE IF NOT EXISTS process_shearing_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    po_no VARCHAR(50) NOT NULL,
    line_no VARCHAR(20) NOT NULL,
    shift VARCHAR(5),
    hour_index INT,
    hour_label VARCHAR(30),
    no_production BOOLEAN DEFAULT FALSE,
    lot_no VARCHAR(50),
    length_cut_bar_1 DECIMAL(10,2),
    length_cut_bar_2 DECIMAL(10,2),
    length_cut_bar_3 DECIMAL(10,2),
    sharp_edges_1 BOOLEAN DEFAULT FALSE,
    sharp_edges_2 BOOLEAN DEFAULT FALSE,
    sharp_edges_3 BOOLEAN DEFAULT FALSE,
    rejected_qty_1 INT,
    rejected_qty_2 INT,
    remarks VARCHAR(500),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    INDEX idx_proc_shear_call_no (inspection_call_no),
    INDEX idx_proc_shear_po_no (po_no),
    INDEX idx_proc_shear_line_no (line_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- TABLE 7: process_turning_data
-- Turning Section data for 8-Hour Grid
-- =====================================================
CREATE TABLE IF NOT EXISTS process_turning_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    po_no VARCHAR(50) NOT NULL,
    line_no VARCHAR(20) NOT NULL,
    shift VARCHAR(5),
    hour_index INT,
    hour_label VARCHAR(30),
    no_production BOOLEAN DEFAULT FALSE,
    lot_no VARCHAR(50),
    straight_length_1 DECIMAL(10,2),
    straight_length_2 DECIMAL(10,2),
    straight_length_3 DECIMAL(10,2),
    taper_length_1 DECIMAL(10,2),
    taper_length_2 DECIMAL(10,2),
    taper_length_3 DECIMAL(10,2),
    dia_1 DECIMAL(10,2),
    dia_2 DECIMAL(10,2),
    dia_3 DECIMAL(10,2),
    accepted_qty INT,
    rejected_qty_1 INT,
    rejected_qty_2 INT,
    remarks VARCHAR(500),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    INDEX idx_proc_turn_call_no (inspection_call_no),
    INDEX idx_proc_turn_po_no (po_no),
    INDEX idx_proc_turn_line_no (line_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- TABLE 8: process_mpi_data
-- MPI (Magnetic Particle Inspection) Section data for 8-Hour Grid
-- =====================================================
CREATE TABLE IF NOT EXISTS process_mpi_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    po_no VARCHAR(50) NOT NULL,
    line_no VARCHAR(20) NOT NULL,
    shift VARCHAR(5),
    hour_index INT,
    hour_label VARCHAR(30),
    no_production BOOLEAN DEFAULT FALSE,
    lot_no VARCHAR(50),
    test_result_1 VARCHAR(50),
    test_result_2 VARCHAR(50),
    test_result_3 VARCHAR(50),
    rejected_qty_1 INT,
    rejected_qty_2 INT,
    remarks VARCHAR(500),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    INDEX idx_proc_mpi_call_no (inspection_call_no),
    INDEX idx_proc_mpi_po_no (po_no),
    INDEX idx_proc_mpi_line_no (line_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- TABLE 9: process_forging_data
-- Forging Section data for 8-Hour Grid
-- =====================================================
CREATE TABLE IF NOT EXISTS process_forging_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    po_no VARCHAR(50) NOT NULL,
    line_no VARCHAR(20) NOT NULL,
    shift VARCHAR(5),
    hour_index INT,
    hour_label VARCHAR(30),
    no_production BOOLEAN DEFAULT FALSE,
    lot_no VARCHAR(50),
    forging_temp_1 DECIMAL(10,2),
    forging_temp_2 DECIMAL(10,2),
    forging_temp_3 DECIMAL(10,2),
    accepted_qty INT,
    rejected_qty INT,
    remarks VARCHAR(500),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    INDEX idx_proc_forg_call_no (inspection_call_no),
    INDEX idx_proc_forg_po_no (po_no),
    INDEX idx_proc_forg_line_no (line_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



-- =====================================================
-- TABLE 10: process_quenching_data
-- Quenching Section data for 8-Hour Grid
-- =====================================================
CREATE TABLE IF NOT EXISTS process_quenching_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    po_no VARCHAR(50) NOT NULL,
    line_no VARCHAR(20) NOT NULL,
    shift VARCHAR(5),
    hour_index INT,
    hour_label VARCHAR(30),
    no_production BOOLEAN DEFAULT FALSE,
    lot_no VARCHAR(50),
    quenching_temperature DECIMAL(10,2),
    quenching_duration DECIMAL(10,2),
    quenching_hardness_1 DECIMAL(10,2),
    quenching_hardness_2 DECIMAL(10,2),
    rejected_qty INT,
    remarks VARCHAR(500),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    INDEX idx_proc_quench_call_no (inspection_call_no),
    INDEX idx_proc_quench_po_no (po_no),
    INDEX idx_proc_quench_line_no (line_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- TABLE 11: process_tempering_data
-- Tempering Section data for 8-Hour Grid
-- =====================================================
CREATE TABLE IF NOT EXISTS process_tempering_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    po_no VARCHAR(50) NOT NULL,
    line_no VARCHAR(20) NOT NULL,
    shift VARCHAR(5),
    hour_index INT,
    hour_label VARCHAR(30),
    no_production BOOLEAN DEFAULT FALSE,
    lot_no VARCHAR(50),
    tempering_temperature DECIMAL(10,2),
    tempering_duration DECIMAL(10,2),
    accepted_qty INT,
    rejected_qty INT,
    remarks VARCHAR(500),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    INDEX idx_proc_temper_call_no (inspection_call_no),
    INDEX idx_proc_temper_po_no (po_no),
    INDEX idx_proc_temper_line_no (line_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- TABLE 12: process_final_check_data
-- Final Check Section data for 8-Hour Grid
-- =====================================================
CREATE TABLE IF NOT EXISTS process_final_check_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    po_no VARCHAR(50) NOT NULL,
    line_no VARCHAR(20) NOT NULL,
    shift VARCHAR(5),
    hour_index INT,
    hour_label VARCHAR(30),
    no_production BOOLEAN DEFAULT FALSE,
    lot_no VARCHAR(50),
    visual_check_1 VARCHAR(50),
    visual_check_2 VARCHAR(50),
    dimension_check_1 VARCHAR(50),
    dimension_check_2 VARCHAR(50),
    hardness_check_1 VARCHAR(50),
    hardness_check_2 VARCHAR(50),
    rejected_no_1 INT,
    rejected_no_2 INT,
    rejected_no_3 INT,
    remarks VARCHAR(500),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    INDEX idx_proc_final_call_no (inspection_call_no),
    INDEX idx_proc_final_po_no (po_no),
    INDEX idx_proc_final_line_no (line_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- TABLE 13: process_summary_report
-- Summary Report for Process Inspection
-- =====================================================
CREATE TABLE IF NOT EXISTS process_summary_report (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    po_no VARCHAR(50) NOT NULL,
    line_no VARCHAR(20) NOT NULL,
    heat_no VARCHAR(50),
    lot_no VARCHAR(50),
    accepted_rejected VARCHAR(20),
    weight_of_material DECIMAL(10,2),
    heat_remarks VARCHAR(500),
    static_checks_passed BOOLEAN DEFAULT FALSE,
    oil_tank_counter_value INT,
    oil_tank_status VARCHAR(20),
    calibration_verified BOOLEAN DEFAULT FALSE,
    ie_remarks VARCHAR(1000),
    final_status VARCHAR(30),
    inspection_completed BOOLEAN DEFAULT FALSE,
    inspection_completed_at DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    INDEX idx_proc_summary_call_no (inspection_call_no),
    INDEX idx_proc_summary_po_no (po_no),
    INDEX idx_proc_summary_line_no (line_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- END OF PROCESS MATERIAL INSPECTION SCHEMA
-- Total Tables: 13
-- 1. process_inspection_request - Main inspection request
-- 2. process_production_line - Production line details
-- 3. process_calibration_documents - Calibration verification
-- 4. process_static_periodic_check - Equipment checks
-- 5. process_oil_tank_counter - Oil tank tracking
-- 6. process_shearing_data - Shearing 8-hour grid
-- 7. process_turning_data - Turning 8-hour grid
-- 8. process_mpi_data - MPI 8-hour grid
-- 9. process_forging_data - Forging 8-hour grid
-- 10. process_quenching_data - Quenching 8-hour grid
-- 11. process_tempering_data - Tempering 8-hour grid
-- 12. process_final_check_data - Final check 8-hour grid
-- 13. process_summary_report - Summary report
-- =====================================================