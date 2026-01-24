-- ============================================================
-- Migration: Create Final Hardness Test Tables
-- Date: 2026-01-21
-- Description: Creates two-table design for Final Hardness Test
--   - Parent table: final_hardness_test (one row per inspection session)
--   - Child table: final_hardness_test_sample (all sample readings)
-- ============================================================

-- ============================================================
-- 1) Parent Table: final_hardness_test
-- ============================================================
CREATE TABLE  final_hardness_test (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    
    -- Identification
    inspection_call_no VARCHAR(50) NOT NULL,
    lot_no VARCHAR(100) NOT NULL,
    heat_no VARCHAR(100) NOT NULL,
    qty_no INT,
    
    -- Overall Result
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    rejected INT DEFAULT 0,
    remarks TEXT,
    
    -- Audit Fields
    created_by VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- Unique constraint: one inspection session per (call, lot, heat)
    UNIQUE KEY uk_final_hard_unique (inspection_call_no, lot_no, heat_no),
    
    -- Indexes for fast queries
    INDEX idx_final_hard_call_no (inspection_call_no),
    INDEX idx_final_hard_lot_no (lot_no),
    INDEX idx_final_hard_heat_no (heat_no),
    INDEX idx_final_hard_status (status)
) 

-- ============================================================
-- 2) Child Table: final_hardness_test_sample
-- ============================================================
CREATE TABLE  final_hardness_test_sample (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    
    -- Foreign Key
    final_hardness_test_id BIGINT NOT NULL,
    
    -- Sample Identification
    sampling_no INT NOT NULL,
    sample_no INT NOT NULL,
    
    -- Sample Data
    sample_value DOUBLE NOT NULL,
    is_rejected BOOLEAN NOT NULL DEFAULT FALSE,
    
    -- Audit
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    -- Foreign Key Constraint
    CONSTRAINT fk_fhts_parent FOREIGN KEY (final_hardness_test_id) 
        REFERENCES final_hardness_test(id) ON DELETE CASCADE,
    
    -- Unique constraint: one sample per (test, sampling, sample_no)
    UNIQUE KEY uk_fhts_unique (final_hardness_test_id, sampling_no, sample_no),
    
    -- Indexes
    INDEX idx_fhts_parent_id (final_hardness_test_id),
    INDEX idx_fhts_sampling_no (sampling_no),
    INDEX idx_fhts_is_rejected (is_rejected)
) 

-- ============================================================
-- Verification Queries (for testing)
-- ============================================================
-- SELECT * FROM final_hardness_test;
-- SELECT * FROM final_hardness_test_sample;
-- SELECT COUNT(*) as rejected_count FROM final_hardness_test_sample WHERE is_rejected = TRUE;

