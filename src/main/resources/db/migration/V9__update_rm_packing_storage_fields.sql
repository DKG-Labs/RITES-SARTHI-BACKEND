-- Migration to update packing and storage fields
ALTER TABLE rm_packing_storage 
DROP COLUMN bundling_secure,
DROP COLUMN tags_attached,
DROP COLUMN labels_correct,
DROP COLUMN protection_adequate,
DROP COLUMN storage_condition,
DROP COLUMN moisture_protection,
DROP COLUMN stacking_proper;

ALTER TABLE rm_packing_storage 
ADD COLUMN stored_heat_wise VARCHAR(10),
ADD COLUMN supplied_in_bundles VARCHAR(10),
ADD COLUMN heat_number_ends VARCHAR(10),
ADD COLUMN packing_strip_width VARCHAR(10),
ADD COLUMN bundle_tied_locations VARCHAR(10),
ADD COLUMN id_tag_bundle VARCHAR(10),
ADD COLUMN metal_tag_info VARCHAR(10),
ADD COLUMN shift VARCHAR(20);
