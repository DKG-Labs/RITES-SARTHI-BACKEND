-- Add Testing & Finishing fields to process_line_final_result table
ALTER TABLE process_line_final_result
ADD COLUMN testing_finishing_manufactured INT,
ADD COLUMN testing_finishing_accepted INT,
ADD COLUMN testing_finishing_rejected INT,
ADD COLUMN testing_finishing_status VARCHAR(20);
