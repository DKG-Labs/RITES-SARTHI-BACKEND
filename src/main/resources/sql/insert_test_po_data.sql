-- =====================================================
-- INSERT TEST PO DATA FOR VENDOR DASHBOARD
-- =====================================================
-- This script inserts test PO header and item data
-- for testing the Vendor Dashboard PO Assigned API
-- NOTE: This script checks if data exists before inserting
-- =====================================================

USE rites_erc_inspection;

-- Disable safe mode temporarily
SET SQL_SAFE_UPDATES = 0;
SET FOREIGN_KEY_CHECKS = 0;

-- =====================================================
-- DELETE EXISTING TEST DATA (if any)
-- =====================================================
-- Only delete the specific test POs we're about to insert
DELETE FROM po_item WHERE po_header_id IN (
    SELECT id FROM po_header WHERE po_key IN ('PO-TEST-2025-001', 'PO-TEST-2025-002', 'PO-TEST-2025-003')
);
DELETE FROM po_header WHERE po_key IN ('PO-TEST-2025-001', 'PO-TEST-2025-002', 'PO-TEST-2025-003');

-- =====================================================
-- INSERT PO HEADER DATA
-- =====================================================

-- PO Header 1 - Vendor Code: VENDOR001
INSERT INTO po_header (
    po_key, po_no, l5_po_no, rly_cd, rly_short_name,
    purchaser_code, purchaser_detail, stock_non_stock, rly_non_rly, po_or_letter,
    vendor_code, vendor_details, firm_details, inspecting_agency, po_status,
    po_date, received_date, cris_timestamp, user_id, source_system
) VALUES (
    'PO-TEST-2025-001', 'PO-TEST-2025-001', 'L5-PO-TEST-2025-001', 'CR', 'Central Railway',
    'PURCH-001', 'Central Railway Procurement Division', 'STOCK', 'RLY', 'PO',
    'VENDOR001', 'ABC Steel Industries Pvt Ltd, Mumbai', 'ABC Steel Industries', 'RITES', 'ACTIVE',
    '2025-11-01 00:00:00', '2025-11-02 00:00:00', '2025-11-01 00:00:00', 'admin', 'CRIS'
);

SET @po_header_1_id = LAST_INSERT_ID();

-- PO Header 2 - Vendor Code: VENDOR001
INSERT INTO po_header (
    po_key, po_no, l5_po_no, rly_cd, rly_short_name,
    purchaser_code, purchaser_detail, stock_non_stock, rly_non_rly, po_or_letter,
    vendor_code, vendor_details, firm_details, inspecting_agency, po_status,
    po_date, received_date, cris_timestamp, user_id, source_system
) VALUES (
    'PO-TEST-2025-002', 'PO-TEST-2025-002', 'L5-PO-TEST-2025-002', 'WR', 'Western Railway',
    'PURCH-002', 'Western Railway Procurement Division', 'STOCK', 'RLY', 'PO',
    'VENDOR001', 'ABC Steel Industries Pvt Ltd, Mumbai', 'ABC Steel Industries', 'RITES', 'ACTIVE',
    '2025-11-15 00:00:00', '2025-11-16 00:00:00', '2025-11-15 00:00:00', 'admin', 'CRIS'
);

SET @po_header_2_id = LAST_INSERT_ID();

-- PO Header 3 - Vendor Code: VENDOR002 (different vendor)
INSERT INTO po_header (
    po_key, po_no, l5_po_no, rly_cd, rly_short_name,
    purchaser_code, purchaser_detail, stock_non_stock, rly_non_rly, po_or_letter,
    vendor_code, vendor_details, firm_details, inspecting_agency, po_status,
    po_date, received_date, cris_timestamp, user_id, source_system
) VALUES (
    'PO-TEST-2025-003', 'PO-TEST-2025-003', 'L5-PO-TEST-2025-003', 'NR', 'Northern Railway',
    'PURCH-003', 'Northern Railway Procurement Division', 'STOCK', 'RLY', 'PO',
    'VENDOR002', 'XYZ Manufacturing Ltd, Delhi', 'XYZ Manufacturing', 'RITES', 'ACTIVE',
    '2025-12-01 00:00:00', '2025-12-02 00:00:00', '2025-12-01 00:00:00', 'admin', 'CRIS'
);

SET @po_header_3_id = LAST_INSERT_ID();

-- =====================================================
-- INSERT PO ITEM DATA
-- =====================================================

-- Items for PO-TEST-2025-001 (Vendor VENDOR001)
INSERT INTO po_item (
    po_header_id, rly, case_no, item_sr_no, pl_no, item_desc,
    consignee_cd, imms_consignee_cd, imms_consignee_name, consignee_detail,
    qty, qty_cancelled, uom_cd, uom,
    rate, basic_value, sales_tax_percent, sales_tax,
    discount_type, discount_percent, discount, value,
    ot_charge_type, ot_charge_percent, other_charges,
    delivery_date, extended_delivery_date, cris_timestamp,
    allocation, user_id, source_system
) VALUES
(
    @po_header_1_id, 'CR', 'PO-TEST-2025-001', '001', 'PL-001', 'ERC MK-III Clips - Type A',
    'CONS-001', 'IMMS-CONS-001', 'RITES Northern Region', 'RITES, Northern Region Office',
    5000, 0, 'NOS', 'Nos',
    150.00, 750000.00, 18.00, 135000.00,
    'PERCENT', 0.00, 0.00, 885000.00,
    'FREIGHT', 5.00, 44250.00,
    '2025-12-31 00:00:00', NULL, '2025-11-01 00:00:00',
    'ALLOCATED', 'admin', 'CRIS'
),
(
    @po_header_1_id, 'CR', 'PO-TEST-2025-001', '002', 'PL-002', 'ERC MK-III Clips - Type B',
    'CONS-002', 'IMMS-CONS-002', 'RITES Western Region', 'RITES, Western Region Office',
    3000, 0, 'NOS', 'Nos',
    150.00, 450000.00, 18.00, 81000.00,
    'PERCENT', 0.00, 0.00, 531000.00,
    'FREIGHT', 5.00, 26550.00,
    '2025-12-31 00:00:00', NULL, '2025-11-01 00:00:00',
    'ALLOCATED', 'admin', 'CRIS'
);

-- Items for PO-TEST-2025-002 (Vendor VENDOR001)
INSERT INTO po_item (
    po_header_id, rly, case_no, item_sr_no, pl_no, item_desc,
    consignee_cd, imms_consignee_cd, imms_consignee_name, consignee_detail,
    qty, qty_cancelled, uom_cd, uom,
    rate, basic_value, sales_tax_percent, sales_tax,
    discount_type, discount_percent, discount, value,
    ot_charge_type, ot_charge_percent, other_charges,
    delivery_date, extended_delivery_date, cris_timestamp,
    allocation, user_id, source_system
) VALUES
(
    @po_header_2_id, 'WR', 'PO-TEST-2025-002', '001', 'PL-003', 'Steel Round Bars - 25mm',
    'CONS-003', 'IMMS-CONS-003', 'RITES Central Region', 'RITES, Central Region Office',
    2000, 0, 'NOS', 'Nos',
    200.00, 400000.00, 18.00, 72000.00,
    'PERCENT', 0.00, 0.00, 472000.00,
    'FREIGHT', 5.00, 23600.00,
    '2026-01-31 00:00:00', NULL, '2025-11-15 00:00:00',
    'ALLOCATED', 'admin', 'CRIS'
);

-- Items for PO-TEST-2025-003 (Vendor VENDOR002)
INSERT INTO po_item (
    po_header_id, rly, case_no, item_sr_no, pl_no, item_desc,
    consignee_cd, imms_consignee_cd, imms_consignee_name, consignee_detail,
    qty, qty_cancelled, uom_cd, uom,
    rate, basic_value, sales_tax_percent, sales_tax,
    discount_type, discount_percent, discount, value,
    ot_charge_type, ot_charge_percent, other_charges,
    delivery_date, extended_delivery_date, cris_timestamp,
    allocation, user_id, source_system
) VALUES
(
    @po_header_3_id, 'NR', 'PO-TEST-2025-003', '001', 'PL-004', 'Fasteners - M12 Bolts',
    'CONS-004', 'IMMS-CONS-004', 'RITES Southern Region', 'RITES, Southern Region Office',
    10000, 0, 'NOS', 'Nos',
    50.00, 500000.00, 18.00, 90000.00,
    'PERCENT', 0.00, 0.00, 590000.00,
    'FREIGHT', 5.00, 29500.00,
    '2026-02-28 00:00:00', NULL, '2025-12-01 00:00:00',
    'ALLOCATED', 'admin', 'CRIS'
);

-- Re-enable safe mode
SET FOREIGN_KEY_CHECKS = 1;
SET SQL_SAFE_UPDATES = 1;

-- =====================================================
-- VERIFICATION QUERIES
-- =====================================================
SELECT 'PO Headers Inserted:' AS Info, COUNT(*) AS Count FROM po_header WHERE vendor_code IN ('VENDOR001', 'VENDOR002');
SELECT 'PO Items Inserted:' AS Info, COUNT(*) AS Count FROM po_item WHERE po_header_id IN (@po_header_1_id, @po_header_2_id, @po_header_3_id);

SELECT 'Test Data Summary:' AS Info;
SELECT
    ph.vendor_code,
    ph.po_no,
    ph.firm_details,
    COUNT(pi.id) AS item_count,
    SUM(pi.qty) AS total_qty
FROM po_header ph
LEFT JOIN po_item pi ON ph.id = pi.po_header_id
WHERE ph.vendor_code IN ('VENDOR001', 'VENDOR002')
GROUP BY ph.vendor_code, ph.po_no, ph.firm_details;

SELECT '=== USE THIS VENDOR CODE IN FRONTEND ===' AS Info;
SELECT 'VENDOR001' AS 'Vendor Code to Use in VendorDashboardPage.js';

