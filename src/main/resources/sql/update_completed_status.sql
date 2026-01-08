-- =====================================================
-- Update status to COMPLETED for finished inspections
-- =====================================================
-- This script updates the status of inspection calls that have
-- been finished but don't have the COMPLETED status yet
-- =====================================================

-- Update specific call that was finished
UPDATE inspection_calls 
SET status = 'COMPLETED' 
WHERE ic_number = 'RM-IC-1767772023499';

-- Verify the update
SELECT ic_number, status, created_at, updated_at 
FROM inspection_calls 
WHERE ic_number = 'RM-IC-1767772023499';

