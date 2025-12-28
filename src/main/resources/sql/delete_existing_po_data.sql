-- =====================================================
-- DELETE EXISTING PO DATA (OPTIONAL)
-- =====================================================
-- WARNING: This will delete ALL PO data from the database
-- Only run this if you want to start fresh with test data
-- =====================================================

USE rites_erc_inspection;

-- Disable safe mode temporarily
SET SQL_SAFE_UPDATES = 0;
SET FOREIGN_KEY_CHECKS = 0;

-- =====================================================
-- BACKUP EXISTING DATA (Optional - uncomment to backup)
-- =====================================================
-- CREATE TABLE po_header_backup AS SELECT * FROM po_header;
-- CREATE TABLE po_item_backup AS SELECT * FROM po_item;

-- =====================================================
-- DELETE ALL PO DATA
-- =====================================================
DELETE FROM po_item;
DELETE FROM po_header;

-- Reset auto-increment (optional)
ALTER TABLE po_item AUTO_INCREMENT = 1;
ALTER TABLE po_header AUTO_INCREMENT = 1;

-- Re-enable safe mode
SET FOREIGN_KEY_CHECKS = 1;
SET SQL_SAFE_UPDATES = 1;

-- =====================================================
-- VERIFY DELETION
-- =====================================================
SELECT 'PO Headers Remaining:' AS Info, COUNT(*) AS Count FROM po_header;
SELECT 'PO Items Remaining:' AS Info, COUNT(*) AS Count FROM po_item;

SELECT 'All PO data has been deleted. You can now run insert_test_po_data.sql' AS Status;

