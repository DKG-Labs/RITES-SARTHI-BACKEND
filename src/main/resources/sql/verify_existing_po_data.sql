-- =====================================================
-- VERIFY EXISTING PO DATA
-- =====================================================
-- This script checks what PO data already exists
-- in the database and shows vendor codes
-- =====================================================

USE rites_erc_inspection;

-- =====================================================
-- CHECK EXISTING PO HEADERS
-- =====================================================
SELECT 
    '=== PO HEADERS ===' AS Info;

SELECT 
    id,
    po_key,
    po_no,
    vendor_code,
    vendor_details,
    firm_details,
    po_date,
    po_status
FROM po_header
ORDER BY vendor_code, po_no;

-- =====================================================
-- CHECK VENDOR CODES
-- =====================================================
SELECT 
    '=== UNIQUE VENDOR CODES ===' AS Info;

SELECT DISTINCT
    vendor_code,
    vendor_details,
    COUNT(*) AS po_count
FROM po_header
GROUP BY vendor_code, vendor_details
ORDER BY vendor_code;

-- =====================================================
-- CHECK PO ITEMS FOR EACH HEADER
-- =====================================================
SELECT 
    '=== PO ITEMS COUNT ===' AS Info;

SELECT 
    ph.vendor_code,
    ph.po_no,
    ph.firm_details,
    COUNT(pi.id) AS item_count,
    SUM(pi.qty) AS total_qty
FROM po_header ph
LEFT JOIN po_item pi ON ph.id = pi.po_header_id
GROUP BY ph.vendor_code, ph.po_no, ph.firm_details
ORDER BY ph.vendor_code, ph.po_no;

-- =====================================================
-- DETAILED VIEW OF FIRST VENDOR'S DATA
-- =====================================================
SELECT 
    '=== DETAILED VIEW (First Vendor) ===' AS Info;

-- Get the first vendor code
SET @first_vendor = (SELECT vendor_code FROM po_header ORDER BY vendor_code LIMIT 1);

SELECT 
    ph.po_no,
    ph.po_date,
    ph.firm_details AS description,
    pi.item_sr_no,
    pi.item_desc,
    pi.qty,
    pi.uom,
    pi.imms_consignee_name AS consignee,
    pi.delivery_date
FROM po_header ph
LEFT JOIN po_item pi ON ph.id = pi.po_header_id
WHERE ph.vendor_code = @first_vendor
ORDER BY ph.po_no, pi.item_sr_no;

-- =====================================================
-- SHOW FIRST VENDOR CODE FOR FRONTEND USE
-- =====================================================
SELECT 
    '=== USE THIS VENDOR CODE IN FRONTEND ===' AS Info;

SELECT 
    vendor_code AS 'Vendor Code to Use',
    vendor_details AS 'Vendor Name',
    COUNT(*) AS 'Number of POs'
FROM po_header
GROUP BY vendor_code, vendor_details
ORDER BY vendor_code
LIMIT 1;

