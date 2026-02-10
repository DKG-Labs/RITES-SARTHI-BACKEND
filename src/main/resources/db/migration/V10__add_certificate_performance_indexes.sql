-- PERFORMANCE INDEXES FOR FINAL MATERIAL CERTIFICATE API
-- Target Tables: inspection_calls, final_inspection_details, final_inspection_lot_details, po_header, po_item, main_po_information

-- 1. Inspection Calls
-- Used for fetching IC by number and counting installments for a PO
CREATE INDEX idx_ic_number ON inspection_calls(ic_number);
CREATE INDEX idx_ic_po_no ON inspection_calls(po_no);

-- 2. Final Inspection Details
-- Used for fetching details by IC ID
CREATE INDEX idx_fid_ic_id ON final_inspection_details(ic_id);

-- 3. Final Inspection Lot Details
-- Used for fetching lot details by header ID
CREATE INDEX idx_fild_detail_id ON final_inspection_lot_details(final_detail_id);

-- 4. PO Header
-- Used for fetching contractor and contract details
CREATE INDEX idx_po_header_no ON po_header(po_no);

-- 5. PO Items
-- Used for fetching consignee and bill paying officer
CREATE INDEX idx_po_item_header_id ON po_item(po_header_id);
CREATE INDEX idx_po_item_po_no_serial ON po_item(po_no, item_sr_no);

-- 6. Main PO Information (Section A)
-- Used for fetching Bill Paying Officer and Purchasing Authority
CREATE INDEX idx_main_po_ic_no ON main_po_information(inspection_call_no);
