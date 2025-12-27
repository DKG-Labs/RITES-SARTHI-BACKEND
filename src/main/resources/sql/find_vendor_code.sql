-- =====================================================
-- FIND VENDOR CODE - Quick Script
-- =====================================================
-- Run this to find what vendor codes exist in your database
-- Copy the vendor_code and use it in the frontend
-- =====================================================

USE rites_erc_inspection;

-- Show all vendor codes with PO count
SELECT 
    '=== VENDOR CODES IN DATABASE ===' AS Info;

SELECT 
    vendor_code AS 'Vendor Code',
    vendor_details AS 'Vendor Name',
    COUNT(*) AS 'Number of POs'
FROM po_header
GROUP BY vendor_code, vendor_details
ORDER BY vendor_code;

-- Show first vendor's PO details
SELECT 
    '=== FIRST VENDOR PO DETAILS ===' AS Info;

SELECT 
    ph.vendor_code,
    ph.po_no,
    ph.po_date,
    ph.firm_details,
    COUNT(pi.id) AS item_count
FROM po_header ph
LEFT JOIN po_item pi ON ph.id = pi.po_header_id
GROUP BY ph.vendor_code, ph.po_no, ph.po_date, ph.firm_details
ORDER BY ph.vendor_code, ph.po_no
LIMIT 5;

-- Show the vendor code to use in frontend
SELECT 
    '=== COPY THIS VENDOR CODE ===' AS Info;

SELECT 
    CONCAT('Use this in VendorDashboardPage.js line 133: ', vendor_code) AS 'Instructions'
FROM po_header
ORDER BY vendor_code
LIMIT 1;

