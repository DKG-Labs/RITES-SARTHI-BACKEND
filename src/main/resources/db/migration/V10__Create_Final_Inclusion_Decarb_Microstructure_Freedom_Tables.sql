-- ============================================================
-- Migration: Create Final Inspection Submodule Tables
-- Date: 2026-01-22
-- Description: Creates tables for Inclusion Rating, Depth of Decarburization,
--              Microstructure, and Freedom from Defects
-- ============================================================

-- ============================================================
-- 1) DEPTH OF DECARBURIZATION TABLES
-- ============================================================
CREATE TABLE IF NOT EXISTS final_depth_of_decarburization (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    inspection_call_no VARCHAR(50) NOT NULL,
    heat_no VARCHAR(100) NOT NULL,
    sample_size INT NOT NULL,
    qty INT,
    remarks TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    created_by VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_final_decarb_call_no (inspection_call_no),
    INDEX idx_final_decarb_heat_no (heat_no),
    UNIQUE KEY uk_final_decarb (inspection_call_no, heat_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS final_depth_of_decarburization_sample (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    final_depth_of_decarburization_id BIGINT NOT NULL,
    sampling_no INT NOT NULL,
    sample_no INT NOT NULL,
    sample_value DECIMAL(10,4),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    
    FOREIGN KEY (final_depth_of_decarburization_id) REFERENCES final_depth_of_decarburization(id) ON DELETE CASCADE,
    INDEX idx_final_decarb_sample_parent (final_depth_of_decarburization_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- 2) INCLUSION RATING TABLES
-- ============================================================
CREATE TABLE IF NOT EXISTS final_inclusion_rating (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    inspection_call_no VARCHAR(50) NOT NULL,
    lot_no VARCHAR(100) NOT NULL,
    heat_no VARCHAR(100) NOT NULL,
    sample_size INT NOT NULL,
    sampling_type VARCHAR(50),
    remarks TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    created_by VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_final_incl_call_no (inspection_call_no),
    INDEX idx_final_incl_lot_no (lot_no),
    INDEX idx_final_incl_heat_no (heat_no),
    UNIQUE KEY uk_final_incl (inspection_call_no, lot_no, heat_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS final_inclusion_rating_sample (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    final_inclusion_rating_id BIGINT NOT NULL,
    sampling_no INT NOT NULL,
    sample_no INT NOT NULL,
    sample_value_a VARCHAR(50),
    sample_value_b VARCHAR(50),
    sample_value_c VARCHAR(50),
    sample_value_d VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    
    FOREIGN KEY (final_inclusion_rating_id) REFERENCES final_inclusion_rating(id) ON DELETE CASCADE,
    INDEX idx_final_incl_sample_parent (final_inclusion_rating_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- 3) MICROSTRUCTURE TABLES
-- ============================================================
CREATE TABLE IF NOT EXISTS final_microstructure_test (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    inspection_call_no VARCHAR(50) NOT NULL,
    lot_no VARCHAR(100) NOT NULL,
    heat_no VARCHAR(100) NOT NULL,
    sample_size INT NOT NULL,
    qty INT,
    remarks TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    created_by VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_final_micro_call_no (inspection_call_no),
    INDEX idx_final_micro_lot_no (lot_no),
    INDEX idx_final_micro_heat_no (heat_no),
    UNIQUE KEY uk_final_micro (inspection_call_no, lot_no, heat_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS final_microstructure_sample (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    final_microstructure_test_id BIGINT NOT NULL,
    sample_no INT NOT NULL,
    sample_type VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    
    FOREIGN KEY (final_microstructure_test_id) REFERENCES final_microstructure_test(id) ON DELETE CASCADE,
    INDEX idx_final_micro_sample_parent (final_microstructure_test_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- 4) FREEDOM FROM DEFECTS TABLES
-- ============================================================
CREATE TABLE IF NOT EXISTS final_freedom_from_defects_test (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    inspection_call_no VARCHAR(50) NOT NULL,
    lot_no VARCHAR(100) NOT NULL,
    heat_no VARCHAR(100) NOT NULL,
    sample_size INT NOT NULL,
    qty INT,
    remarks TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    
    INDEX idx_final_freedom_call_no (inspection_call_no),
    INDEX idx_final_freedom_lot_no (lot_no),
    INDEX idx_final_freedom_heat_no (heat_no),
    UNIQUE KEY uk_final_freedom (inspection_call_no, lot_no, heat_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS final_freedom_from_defects_sample (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    final_freedom_from_defects_test_id BIGINT NOT NULL,
    sample_no INT NOT NULL,
    sample_type VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    
    FOREIGN KEY (final_freedom_from_defects_test_id) REFERENCES final_freedom_from_defects_test(id) ON DELETE CASCADE,
    INDEX idx_final_freedom_sample_parent (final_freedom_from_defects_test_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

