-- =====================================================
-- Simplified rm_heat_final_result Table Schema
-- =====================================================
-- This table stores only the final inspection results
-- for each heat in a Raw Material inspection call.
-- 
-- Contains only 13 essential fields:
-- 1. inspection_call_no
-- 2. heat_no
-- 3. weight_offered_mt
-- 4. weight_accepted_mt
-- 5. weight_rejected_mt
-- 6. calibration_status
-- 7. visual_status
-- 8. dimensional_status
-- 9. material_test_status
-- 10. packing_status
-- 11. status (per-heat status)
-- 12. overall_status (overall inspection result)
-- 13. remarks
-- =====================================================

-- Drop existing table if you want to recreate
-- DROP TABLE IF EXISTS rm_heat_final_result;

-- Create simplified table
CREATE TABLE IF NOT EXISTS rm_heat_final_result (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    
    -- 1. Inspection Call Number (Foreign Key reference)
    inspection_call_no VARCHAR(50) NOT NULL,
    
    -- 2. Heat Number
    heat_no VARCHAR(50) NOT NULL,
    
    -- 3-5. Weight Information (in Metric Tons)
    weight_offered_mt DECIMAL(12, 4) DEFAULT 0.000,
    weight_accepted_mt DECIMAL(12, 4) DEFAULT 0.000,
    weight_rejected_mt DECIMAL(12, 4) DEFAULT 0.000,
    
    -- 6-10. Submodule Status (ACCEPTED, REJECTED, PENDING, NOT_APPLICABLE)
    calibration_status VARCHAR(20) DEFAULT 'PENDING',
    visual_status VARCHAR(20) DEFAULT 'PENDING',
    dimensional_status VARCHAR(20) DEFAULT 'PENDING',
    material_test_status VARCHAR(20) DEFAULT 'PENDING',
    packing_status VARCHAR(20) DEFAULT 'PENDING',
    
    -- 11. Per-Heat Status (PENDING, IN_PROGRESS, ACCEPTED, REJECTED)
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    
    -- 12. Overall Status (ACCEPTED, PARTIALLY_ACCEPTED, REJECTED)
    overall_status VARCHAR(20) DEFAULT 'PENDING',
    
    -- 13. Remarks
    remarks TEXT,
    
    -- Audit Fields
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- Indexes for performance
    INDEX idx_rm_heat_call_no (inspection_call_no),
    INDEX idx_rm_heat_heat_no (heat_no),
    INDEX idx_rm_heat_status (status),
    INDEX idx_rm_heat_overall_status (overall_status),
    
    -- Unique constraint to prevent duplicate heat entries per call
    UNIQUE KEY unique_call_heat (inspection_call_no, heat_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- Sample Data for Testing
-- =====================================================
INSERT INTO rm_heat_final_result (
    inspection_call_no,
    heat_no,
    weight_offered_mt,
    weight_accepted_mt,
    weight_rejected_mt,
    calibration_status,
    visual_status,
    dimensional_status,
    material_test_status,
    packing_status,
    status,
    overall_status,
    remarks
) VALUES 
(
    'RM-IC-1767772023499',
    'H-2025-001',
    50.250,
    50.250,
    0.000,
    'ACCEPTED',
    'ACCEPTED',
    'ACCEPTED',
    'ACCEPTED',
    'ACCEPTED',
    'ACCEPTED',
    'ACCEPTED',
    'All tests passed successfully'
),
(
    'RM-IC-1767772023499',
    'H-2025-002',
    50.125,
    45.000,
    5.125,
    'ACCEPTED',
    'REJECTED',
    'ACCEPTED',
    'ACCEPTED',
    'ACCEPTED',
    'PARTIALLY_ACCEPTED',
    'PARTIALLY_ACCEPTED',
    'Visual defects found in 5.125 MT'
),
(
    'RM-IC-1767772023499',
    'H-2025-003',
    50.125,
    0.000,
    50.125,
    'ACCEPTED',
    'REJECTED',
    'REJECTED',
    'REJECTED',
    'NOT_APPLICABLE',
    'REJECTED',
    'REJECTED',
    'Failed dimensional and material tests'
);

-- =====================================================
-- Verification Query
-- =====================================================
SELECT 
    inspection_call_no,
    heat_no,
    weight_offered_mt,
    weight_accepted_mt,
    weight_rejected_mt,
    status,
    overall_status
FROM rm_heat_final_result
ORDER BY inspection_call_no, heat_no;

