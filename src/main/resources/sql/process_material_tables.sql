-- =====================================================
-- Process Material Inspection Module - Database Tables
-- Database: MySQL
-- =====================================================

-- 1. Calibration & Documents Table
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

-- 2. Static Periodic Check Table
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

-- 3. Oil Tank Counter Table
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

-- 4. Shearing Data Table (8-Hour Grid)
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
    INDEX idx_proc_shear_po_no (po_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5. Turning Data Table (8-Hour Grid)
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
    INDEX idx_proc_turn_po_no (po_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 6. MPI Data Table (8-Hour Grid)
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
    INDEX idx_proc_mpi_po_no (po_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 7. Forging Data Table (8-Hour Grid)
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
    INDEX idx_proc_forg_po_no (po_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 8. Quenching Data Table (8-Hour Grid)
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
    INDEX idx_proc_quench_po_no (po_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 9. Tempering Data Table (8-Hour Grid)
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
    INDEX idx_proc_temper_po_no (po_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 10. Final Check Data Table (8-Hour Grid)
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
    INDEX idx_proc_final_po_no (po_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 11. Summary Report Table
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
    INDEX idx_proc_summary_po_no (po_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

