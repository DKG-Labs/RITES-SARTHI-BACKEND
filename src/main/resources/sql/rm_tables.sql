-- ============================================================
-- Raw Material Inspection Tables
-- Run this script to create the 4 RM tables in the database
-- ============================================================

-- 1. Main Inspection Calls table
CREATE TABLE IF NOT EXISTS inspection_calls (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    call_no VARCHAR(50) NOT NULL UNIQUE,
    call_date DATE,
    desired_inspection_date DATE,
    inspection_type VARCHAR(30),
    status VARCHAR(20) DEFAULT 'PENDING',
    
    -- PO Information
    po_no VARCHAR(50),
    po_date DATE,
    po_serial_no VARCHAR(20),
    po_description TEXT,
    po_qty DECIMAL(15,3),
    po_unit VARCHAR(20),
    
    -- Vendor Information
    vendor_code VARCHAR(30),
    vendor_name VARCHAR(200),
    vendor_address TEXT,
    vendor_contact_name VARCHAR(100),
    vendor_contact_phone VARCHAR(20),
    
    -- Unit/Place Information
    unit_name VARCHAR(200),
    unit_address TEXT,
    place_of_inspection TEXT,
    
    -- Additional Info
    delivery_period VARCHAR(100),
    purchasing_authority VARCHAR(200),
    bpo VARCHAR(200),
    remarks TEXT,
    
    -- Audit
    created_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_ic_call_no (call_no),
    INDEX idx_ic_po_no (po_no),
    INDEX idx_ic_type (inspection_type),
    INDEX idx_ic_status (status)
);

-- 2. RM Inspection Details (1:1 with inspection_calls)
CREATE TABLE IF NOT EXISTS rm_inspection_details (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_id BIGINT NOT NULL UNIQUE,
    
    -- Material Info
    material_name VARCHAR(200),
    grade_specification VARCHAR(100),
    material_standard VARCHAR(100),
    section_size VARCHAR(50),
    
    -- Quantity Info
    total_offered_qty_mt DECIMAL(15,3),
    offered_qty_pieces INT,
    qty_already_inspected DECIMAL(15,3),
    balance_qty DECIMAL(15,3),
    
    -- TC Info
    tc_number VARCHAR(100),
    tc_date DATE,
    tc_issued_by VARCHAR(200),
    
    -- Sub PO Info
    sub_po_number VARCHAR(50),
    sub_po_date DATE,
    
    -- Audit
    created_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (inspection_call_id) REFERENCES inspection_calls(id),
    INDEX idx_rmd_call_id (inspection_call_id)
);

-- 3. RM Heat Quantity (1:N with inspection_calls)
CREATE TABLE IF NOT EXISTS rm_heat_quantities (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_id BIGINT NOT NULL,
    
    -- Heat Info
    heat_no VARCHAR(50),
    ladle_no VARCHAR(50),
    cast_date DATE,
    heat_tc_number VARCHAR(100),
    
    -- Quantity Info
    offered_qty_mt DECIMAL(15,3),
    offered_qty_pieces INT,
    accepted_qty_mt DECIMAL(15,3),
    accepted_qty_pieces INT,
    rejected_qty_mt DECIMAL(15,3),
    rejected_qty_pieces INT,
    
    -- Status
    inspection_status VARCHAR(20) DEFAULT 'PENDING',
    remarks TEXT,
    
    -- Audit
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (inspection_call_id) REFERENCES inspection_calls(id),
    INDEX idx_hq_call_id (inspection_call_id),
    INDEX idx_hq_heat_no (heat_no)
);

-- 4. RM Chemical Analysis (1:N with rm_heat_quantities)
CREATE TABLE IF NOT EXISTS rm_chemical_analysis (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rm_heat_quantity_id BIGINT NOT NULL,
    
    -- Element Info
    element_name VARCHAR(20) NOT NULL,
    specified_min DECIMAL(10,4),
    specified_max DECIMAL(10,4),
    actual_value DECIMAL(10,4),
    result VARCHAR(20),
    
    -- Audit
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (rm_heat_quantity_id) REFERENCES rm_heat_quantities(id),
    INDEX idx_ca_heat_id (rm_heat_quantity_id),
    INDEX idx_ca_element (element_name)
);

-- Insert sample data for testing
INSERT INTO inspection_calls (call_no, call_date, desired_inspection_date, inspection_type, status, po_no, po_date, vendor_name, vendor_address, place_of_inspection, purchasing_authority, bpo)
VALUES 
('CALL-RM-001', '2025-12-20', '2025-12-25', 'RAW_MATERIAL', 'PENDING', 'PO-2025-1001', '2025-12-01', 'Steel India Pvt Ltd', 'Mumbai, Maharashtra', 'Factory Premises', 'Central Railways', 'COFMOW');

INSERT INTO rm_inspection_details (inspection_call_id, material_name, grade_specification, material_standard, section_size, total_offered_qty_mt, offered_qty_pieces)
VALUES (1, 'Rail Steel Blooms', 'R260 Grade', 'IRS-T-12-2009', '250x250', 150.500, 50);

INSERT INTO rm_heat_quantities (inspection_call_id, heat_no, ladle_no, cast_date, offered_qty_mt, offered_qty_pieces, inspection_status)
VALUES 
(1, 'H-2025-001', 'L-001', '2025-12-15', 50.250, 17, 'PENDING'),
(1, 'H-2025-002', 'L-002', '2025-12-16', 50.125, 16, 'PENDING'),
(1, 'H-2025-003', 'L-003', '2025-12-17', 50.125, 17, 'PENDING');

