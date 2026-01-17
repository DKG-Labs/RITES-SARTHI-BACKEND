-- Fix Final Inspection Details table to allow NULL for process_ic_id and rm_ic_id
-- This is needed because during testing we may not have actual Process/RM ICs in the database

ALTER TABLE final_inspection_details 
MODIFY COLUMN process_ic_id BIGINT NULL;

ALTER TABLE final_inspection_details 
MODIFY COLUMN rm_ic_id BIGINT NULL;

