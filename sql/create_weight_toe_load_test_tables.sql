-- ============================================================================
-- SQL Script: Create Weight Test and Toe Load Test Tables
-- Two-Table Design for Final Product Inspection
-- ============================================================================

-- ============================================================================
-- WEIGHT TEST TABLES
-- ============================================================================

-- Parent table: Stores one row per inspection session
CREATE TABLE final_weight_test (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    inspection_call_no VARCHAR(50) NOT NULL,
    lot_no VARCHAR(100) NOT NULL,
    heat_no VARCHAR(100) NOT NULL,
    qty_no INT,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    rejected INT,
    remarks TEXT,
    created_by VARCHAR(100) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_by VARCHAR(100) NOT NULL,
    updated_at DATETIME NOT NULL,
    
    -- Unique constraint: one inspection session per call+lot+heat
    UNIQUE KEY idx_final_weight_unique (inspection_call_no, lot_no, heat_no),
    
    -- Indexes for fast queries
    KEY idx_final_weight_call_no (inspection_call_no),
    KEY idx_final_weight_lot_no (lot_no),
    KEY idx_final_weight_heat_no (heat_no)
);

-- Child table: Stores all individual sample values
CREATE TABLE final_weight_test_sample (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    final_weight_test_id BIGINT NOT NULL,
    sampling_no INT NOT NULL,
    sample_no INT NOT NULL,
    sample_value DECIMAL(10, 3) NOT NULL,
    is_rejected BOOLEAN NOT NULL,
    created_at DATETIME NOT NULL,
    
    -- Foreign key to parent table
    CONSTRAINT fk_fwts_parent FOREIGN KEY (final_weight_test_id) 
        REFERENCES final_weight_test(id) ON DELETE CASCADE,
    
    -- Unique constraint: one sample per parent+sampling+sample_no
    UNIQUE KEY idx_fwts_unique (final_weight_test_id, sampling_no, sample_no),
    
    -- Indexes for fast queries
    KEY idx_fwts_parent_id (final_weight_test_id),
    KEY idx_fwts_sampling_no (sampling_no)
);

-- ============================================================================
-- TOE LOAD TEST TABLES
-- ============================================================================

-- Parent table: Stores one row per inspection session
CREATE TABLE final_toe_load_test (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    inspection_call_no VARCHAR(50) NOT NULL,
    lot_no VARCHAR(100) NOT NULL,
    heat_no VARCHAR(100) NOT NULL,
    qty_no INT,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    rejected INT,
    remarks TEXT,
    created_by VARCHAR(100) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_by VARCHAR(100) NOT NULL,
    updated_at DATETIME NOT NULL,
    
    -- Unique constraint: one inspection session per call+lot+heat
    UNIQUE KEY idx_final_toe_unique (inspection_call_no, lot_no, heat_no),
    
    -- Indexes for fast queries
    KEY idx_final_toe_call_no (inspection_call_no),
    KEY idx_final_toe_lot_no (lot_no),
    KEY idx_final_toe_heat_no (heat_no)
);

-- Child table: Stores all individual sample values
CREATE TABLE final_toe_load_test_sample (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    final_toe_load_test_id BIGINT NOT NULL,
    sampling_no INT NOT NULL,
    sample_no INT NOT NULL,
    sample_value DECIMAL(10, 2) NOT NULL,
    is_rejected BOOLEAN NOT NULL,
    created_at DATETIME NOT NULL,
    
    -- Foreign key to parent table
    CONSTRAINT fk_ftlts_parent FOREIGN KEY (final_toe_load_test_id) 
        REFERENCES final_toe_load_test(id) ON DELETE CASCADE,
    
    -- Unique constraint: one sample per parent+sampling+sample_no
    UNIQUE KEY idx_ftlts_unique (final_toe_load_test_id, sampling_no, sample_no),
    
    -- Indexes for fast queries
    KEY idx_ftlts_parent_id (final_toe_load_test_id),
    KEY idx_ftlts_sampling_no (sampling_no)
);

-- ============================================================================
-- END OF SCRIPT
-- ============================================================================

