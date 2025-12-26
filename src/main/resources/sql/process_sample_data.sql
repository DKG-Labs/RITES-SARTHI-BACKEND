-- =====================================================
-- SAMPLE DATA FOR ERC PROCESS INSPECTION CALLS
-- Run this script to add Heat TC mappings for Process calls
-- =====================================================

-- First, update the ERC Process calls with missing fields
UPDATE vendor_inspection_request
SET 
    purchasing_authority = 'Manager, Procurement',
    bpo = 'BPO-001',
    delivery_period = '45 days',
    inspection_fees_payment_details = 'Advance payment received'
WHERE type_of_call = 'ERC Process' AND purchasing_authority IS NULL;

-- =====================================================
-- ADD RM_HEAT_TC_MAPPING FOR ERC PROCESS CALLS (IDs 6-10)
-- =====================================================

-- Heat mappings for ERC Process Call ID=6 (PO-2025-1004 - Stellar Steel Manufacturing)
INSERT INTO rm_heat_tc_mapping (
    inspection_request_id, heat_number, tc_number, tc_date, manufacturer,
    invoice_no, invoice_date, sub_po_number, sub_po_date, sub_po_qty, sub_po_total_value,
    tc_qty, tc_qty_remaining, offered_qty, created_at
) VALUES
(6, 'HT-2025-010', 'TC-50010', '2025-11-18', 'Steel Works Ltd', 
 'INV-2025-6001', '2025-11-19', 'SUB-PO-2025-601', '2025-11-15', '2000 Kg', '₹208000.00',
 '2000 Kg', '500 Kg', '2.000', NOW()),
(6, 'HT-2025-011', 'TC-50011', '2025-11-19', 'Steel Works Ltd',
 'INV-2025-6002', '2025-11-20', 'SUB-PO-2025-601', '2025-11-15', '2500 Kg', '₹260000.00',
 '2500 Kg', '800 Kg', '2.400', NOW()),
(6, 'HT-2025-012', 'TC-50012', '2025-11-20', 'Premium Materials Inc',
 'INV-2025-6003', '2025-11-21', 'SUB-PO-2025-602', '2025-11-16', '2300 Kg', '₹239200.00',
 '2300 Kg', '600 Kg', '2.400', NOW());

-- Heat mappings for ERC Process Call ID=7 (PO-2025-1005 - Modern Forge Industries)
INSERT INTO rm_heat_tc_mapping (
    inspection_request_id, heat_number, tc_number, tc_date, manufacturer,
    invoice_no, invoice_date, sub_po_number, sub_po_date, sub_po_qty, sub_po_total_value,
    tc_qty, tc_qty_remaining, offered_qty, created_at
) VALUES
(7, 'HT-2025-013', 'TC-50013', '2025-11-20', 'SAIL Bhilai Steel',
 'INV-2025-7001', '2025-11-21', 'SUB-PO-2025-701', '2025-11-18', '2800 Kg', '₹291200.00',
 '2800 Kg', '700 Kg', '2.600', NOW()),
(7, 'HT-2025-014', 'TC-50014', '2025-11-21', 'SAIL Bhilai Steel',
 'INV-2025-7002', '2025-11-22', 'SUB-PO-2025-701', '2025-11-18', '2400 Kg', '₹249600.00',
 '2400 Kg', '500 Kg', '2.600', NOW());

-- Heat mappings for ERC Process Call ID=8 (PO-2025-1006 - Southern Steel Works)
INSERT INTO rm_heat_tc_mapping (
    inspection_request_id, heat_number, tc_number, tc_date, manufacturer,
    invoice_no, invoice_date, sub_po_number, sub_po_date, sub_po_qty, sub_po_total_value,
    tc_qty, tc_qty_remaining, offered_qty, created_at
) VALUES
(8, 'HT-2025-015', 'TC-50015', '2025-11-22', 'Tata Steel Ltd',
 'INV-2025-8001', '2025-11-23', 'SUB-PO-2025-801', '2025-11-20', '2200 Kg', '₹228800.00',
 '2200 Kg', '400 Kg', '2.100', NOW()),
(8, 'HT-2025-016', 'TC-50016', '2025-11-23', 'Tata Steel Ltd',
 'INV-2025-8002', '2025-11-24', 'SUB-PO-2025-801', '2025-11-20', '2100 Kg', '₹218400.00',
 '2100 Kg', '300 Kg', '2.000', NOW()),
(8, 'HT-2025-017', 'TC-50017', '2025-11-24', 'Jindal Steel Works',
 'INV-2025-8003', '2025-11-25', 'SUB-PO-2025-802', '2025-11-21', '1900 Kg', '₹197600.00',
 '1900 Kg', '200 Kg', '2.000', NOW());

-- Heat mappings for ERC Process Call ID=9 (PO-2025-1007 - Eastern Metal Products)
INSERT INTO rm_heat_tc_mapping (
    inspection_request_id, heat_number, tc_number, tc_date, manufacturer,
    invoice_no, invoice_date, sub_po_number, sub_po_date, sub_po_qty, sub_po_total_value,
    tc_qty, tc_qty_remaining, offered_qty, created_at
) VALUES
(9, 'HT-2025-018', 'TC-50018', '2025-11-25', 'Essar Steel Ltd',
 'INV-2025-9001', '2025-11-26', 'SUB-PO-2025-901', '2025-11-22', '2500 Kg', '₹260000.00',
 '2500 Kg', '600 Kg', '2.300', NOW()),
(9, 'HT-2025-019', 'TC-50019', '2025-11-26', 'Essar Steel Ltd',
 'INV-2025-9002', '2025-11-27', 'SUB-PO-2025-901', '2025-11-22', '2000 Kg', '₹208000.00',
 '2000 Kg', '400 Kg', '2.200', NOW());

-- Heat mappings for ERC Process Call ID=10 (PO-2025-1008 - Western Forge Pvt Ltd)
INSERT INTO rm_heat_tc_mapping (
    inspection_request_id, heat_number, tc_number, tc_date, manufacturer,
    invoice_no, invoice_date, sub_po_number, sub_po_date, sub_po_qty, sub_po_total_value,
    tc_qty, tc_qty_remaining, offered_qty, created_at
) VALUES
(10, 'HT-2025-020', 'TC-50020', '2025-11-26', 'JSW Steel Ltd',
 'INV-2025-1001', '2025-11-27', 'SUB-PO-2025-1001', '2025-11-24', '2200 Kg', '₹228800.00',
 '2200 Kg', '500 Kg', '2.100', NOW()),
(10, 'HT-2025-021', 'TC-50021', '2025-11-27', 'JSW Steel Ltd',
 'INV-2025-1002', '2025-11-28', 'SUB-PO-2025-1001', '2025-11-24', '2100 Kg', '₹218400.00',
 '2100 Kg', '400 Kg', '2.050', NOW()),
(10, 'HT-2025-022', 'TC-50022', '2025-11-28', 'RINL Vizag Steel',
 'INV-2025-1003', '2025-11-29', 'SUB-PO-2025-1002', '2025-11-25', '2000 Kg', '₹208000.00',
 '2000 Kg', '300 Kg', '2.050', NOW()),
(10, 'HT-2025-023', 'TC-50023', '2025-11-29', 'RINL Vizag Steel',
 'INV-2025-1004', '2025-11-30', 'SUB-PO-2025-1002', '2025-11-25', '1900 Kg', '₹197600.00',
 '1900 Kg', '200 Kg', '2.000', NOW());

-- =====================================================
-- VERIFICATION QUERIES
-- =====================================================
-- SELECT vir.id, vir.po_no, vir.type_of_call, COUNT(rhtm.id) as heat_count
-- FROM vendor_inspection_request vir
-- LEFT JOIN rm_heat_tc_mapping rhtm ON vir.id = rhtm.inspection_request_id
-- WHERE vir.type_of_call = 'ERC Process'
-- GROUP BY vir.id, vir.po_no, vir.type_of_call;

