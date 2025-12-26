-- =====================================================
-- INSPECTION SECTION TABLES
-- Created: 2025-12-25
-- Description: Tables for storing inspection initiation sections data
-- =====================================================

-- Section A: Main PO Information Table
CREATE TABLE IF NOT EXISTS main_po_information (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL UNIQUE,
    po_no VARCHAR(50),
    po_date DATE,
    vendor_code VARCHAR(50),
    vendor_name VARCHAR(200),
    vendor_address TEXT,
    place_of_inspection VARCHAR(200),
    manufacturer VARCHAR(200),
    consignee_rly VARCHAR(100),
    consignee VARCHAR(200),
    item_description TEXT,
    po_qty DECIMAL(15,3),
    unit VARCHAR(20),
    orig_dp VARCHAR(50),
    ext_dp VARCHAR(50),
    orig_dp_start DATE,
    bpo VARCHAR(200),
    date_of_inspection DATE,
    shift_of_inspection VARCHAR(20),
    offered_qty DECIMAL(15,3),
    status VARCHAR(20) DEFAULT 'pending',
    rejection_remarks TEXT,
    created_by VARCHAR(50),
    created_date DATETIME NOT NULL,
    updated_by VARCHAR(50),
    updated_date DATETIME,
    
    INDEX idx_main_po_call_no (inspection_call_no),
    INDEX idx_main_po_po_no (po_no),
    INDEX idx_main_po_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- Section B: Inspection Call Details Table
CREATE TABLE IF NOT EXISTS inspection_call_details (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL UNIQUE,
    inspection_call_date DATE,
    inspection_desired_date DATE,
    rly_po_no_sr VARCHAR(100),
    item_desc TEXT,
    product_type VARCHAR(50),
    po_qty DECIMAL(15,3),
    unit VARCHAR(20),
    consignee_rly VARCHAR(100),
    consignee VARCHAR(200),
    orig_dp VARCHAR(50),
    ext_dp VARCHAR(50),
    orig_dp_start DATE,
    stage_of_inspection VARCHAR(100),
    call_qty DECIMAL(15,3),
    place_of_inspection VARCHAR(200),
    rm_ic_number VARCHAR(100),
    process_ic_number VARCHAR(100),
    remarks TEXT,
    status VARCHAR(20) DEFAULT 'pending',
    rejection_remarks TEXT,
    main_po_id BIGINT,
    created_by VARCHAR(50),
    created_date DATETIME NOT NULL,
    updated_by VARCHAR(50),
    updated_date DATETIME,
    
    INDEX idx_call_details_call_no (inspection_call_no),
    INDEX idx_call_details_status (status),
    CONSTRAINT fk_call_details_main_po FOREIGN KEY (main_po_id) 
        REFERENCES main_po_information(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- Section C: Sub PO Details Table (Multiple records per inspection call)
CREATE TABLE IF NOT EXISTS sub_po_details (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspection_call_no VARCHAR(50) NOT NULL,
    raw_material_name VARCHAR(200),
    grade_spec VARCHAR(100),
    heat_no VARCHAR(100),
    manufacturer_steel_bars VARCHAR(200),
    tc_no VARCHAR(100),
    tc_date DATE,
    sub_po_no VARCHAR(50),
    sub_po_date DATE,
    invoice_no VARCHAR(100),
    invoice_date DATE,
    sub_po_qty DECIMAL(15,3),
    unit VARCHAR(20),
    place_of_inspection VARCHAR(200),
    status VARCHAR(20) DEFAULT 'pending',
    rejection_remarks TEXT,
    inspection_call_details_id BIGINT,
    created_by VARCHAR(50),
    created_date DATETIME NOT NULL,
    updated_by VARCHAR(50),
    updated_date DATETIME,
    
    INDEX idx_sub_po_call_no (inspection_call_no),
    INDEX idx_sub_po_sub_po_no (sub_po_no),
    INDEX idx_sub_po_heat_no (heat_no),
    CONSTRAINT fk_sub_po_call_details FOREIGN KEY (inspection_call_details_id) 
        REFERENCES inspection_call_details(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

