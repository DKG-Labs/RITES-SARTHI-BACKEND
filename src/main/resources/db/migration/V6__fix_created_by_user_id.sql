-- =====================================================
-- Migration V6: Fix createdBy field to use actual user ID
-- =====================================================
-- 
-- Problem: The createdBy field in inspection_call table is storing "system" 
-- instead of the actual IE user ID who initiated the inspection.
--
-- Solution: Update createdBy in inspection_call to match the createdBy 
-- from the workflow_transition table (IE_SCHEDULED or INITIATE_INSPECTION status)
-- =====================================================

-- Step 1: Update inspection_call.createdBy from workflow_transition
-- Match by ic_number (requestId in workflow) and use the IE user who scheduled/initiated
UPDATE inspection_call ic
INNER JOIN (
    SELECT 
        wt.request_id,
        wt.created_by as ie_user_id
    FROM workflow_transition wt
    WHERE wt.status IN ('IE_SCHEDULED', 'INSPECTION_IN_PROGRESS', 'INSPECTION_COMPLETE_CONFIRM')
      AND wt.created_by IS NOT NULL
      AND wt.created_by != 0
    ORDER BY wt.workflow_transition_id DESC
) wt_data ON ic.ic_number = wt_data.request_id
SET ic.created_by = wt_data.ie_user_id
WHERE ic.created_by = 'system' OR ic.created_by IS NULL OR ic.created_by = '0';

-- Step 2: Update inspection_call.updated_by to match created_by
UPDATE inspection_call
SET updated_by = created_by
WHERE updated_by = 'system' OR updated_by IS NULL OR updated_by = '0';

-- Step 3: Verify the update
SELECT 
    ic.ic_number,
    ic.created_by as inspection_call_created_by,
    ic.updated_by as inspection_call_updated_by,
    wt.created_by as workflow_created_by,
    wt.status as workflow_status
FROM inspection_call ic
LEFT JOIN workflow_transition wt ON ic.ic_number = wt.request_id
WHERE wt.status IN ('IE_SCHEDULED', 'INSPECTION_IN_PROGRESS', 'INSPECTION_COMPLETE_CONFIRM')
ORDER BY ic.id DESC
LIMIT 10;

