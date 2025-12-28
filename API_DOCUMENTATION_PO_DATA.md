# PO Data API Documentation

## Overview
New APIs created to fetch PO data from database tables (`po_header`, `po_item`, `po_ma_header`, `po_ma_detail`) for populating Inspection Initiation Sections A, B, and C.

## Files Created (NO EXISTING CODE MODIFIED)

### 1. DTO Layer
- **File**: `src/main/java/com/sarthi/dto/po/PoDataForSectionsDto.java`
- **Purpose**: Data Transfer Object for PO data
- **Fields Mapped**:
  - Section A: RLY+PO_NO, PO_DATE, PO_QTY, INSP_PLACE, VENDOR_NAME, MA_NO, MA_DATE, PURCHASING_AUTHORITY, BILL_PAYING_OFFICER, PO_COND_SR_NO, COND_TITLE, COND_TEXT
  - Section B: ITEM_DESC, CONSIGNEE, UNIT, DELIVERY_DATE, etc.
  - Section C: PRODUCT_TYPE, ORIG_DP, EXT_DP, etc.

### 2. Service Layer
- **Interface**: `src/main/java/com/sarthi/service/PoDataService.java`
- **Implementation**: `src/main/java/com/sarthi/service/Impl/PoDataServiceImpl.java`
- **Methods**:
  - `getPoDataByPoNumber(String poNo)` - Fetches all PO data by PO Number

### 3. Controller Layer
- **File**: `src/main/java/com/sarthi/controller/PoDataController.java`
- **Base URL**: `/api/po-data`
- **Endpoints**:

## API Endpoints

### 1. Get All Sections Data
```
GET /api/po-data/sections?poNo={poNumber}
```
**Description**: Fetches data for all sections (A, B, C) by PO Number

**Request Parameters**:
- `poNo` (required): PO Number (e.g., "PO123456")

**Response Example**:
```json
{
  "rlyPoNo": "AA195118100297",
  "rlyCd": "AA",
  "poNo": "195118100297",
  "poDate": "15/03/2024",
  "poQty": 1000,
  "inspPlace": "Factory",
  "vendorName": "ABC Industries",
  "vendorCode": "V001",
  "maNo": "MA001",
  "maDate": "20/03/2024",
  "purchasingAuthority": "Manager, Procurement",
  "billPayingOfficer": "BPO-001",
  "poCondSrNo": "1",
  "condTitle": "Quality Standards",
  "condText": "As per IS specifications",
  "itemDesc": "Steel Rails",
  "consignee": "Central Workshop",
  "unit": "MT",
  "deliveryDate": "30/06/2024",
  "maList": [
    {
      "maNo": "MA001",
      "maDate": "20/03/2024",
      "subject": "Price Revision",
      "oldPoValue": 1000000.00,
      "newPoValue": 1100000.00,
      "maType": "PRICE",
      "status": "APPROVED"
    }
  ]
}
```

### 2. Get Section A Data
```
GET /api/po-data/section-a?poNo={poNumber}
```
**Description**: Fetches Section A data (same as above)

### 3. Get Section B Data
```
GET /api/po-data/section-b?poNo={poNumber}
```
**Description**: Fetches Section B data (same as above)

### 4. Get Section C Data
```
GET /api/po-data/section-c?poNo={poNumber}
```
**Description**: Fetches Section C data (same as above)

## Database Tables Used

### 1. po_header
- Fields: poNo, poDate, rlyCd, vendorCode, vendorDetails, purchaserDetail, firmDetails

### 2. po_item
- Fields: itemDesc, qty, uom, deliveryDate, extendedDeliveryDate, consignee, plNo

### 3. po_ma_header
- Fields: maNo, maDate, subject, oldPoValue, newPoValue, maType, status

### 4. po_ma_detail
- Fields: condSlno, maFldDescr, newValue (for condition details)

## Data Mapping Logic

### Section A Fields:
- **RLY + PO_NO**: Concatenation of `rlyCd` + `poNo` from `po_header`
- **PO_DATE**: `poDate` from `po_header` (formatted as dd/MM/yyyy)
- **PO_QTY**: Sum of `qty` from all `po_item` records
- **INSP_PLACE**: Extracted from `firmDetails` in `po_header`
- **VENDOR_NAME**: Extracted from `vendorDetails` in `po_header`
- **MA_NO**: `maNo` from first `po_ma_header` record
- **MA_DATE**: `maDate` from first `po_ma_header` record
- **PURCHASING_AUTHORITY**: `purchaserDetail` from `po_header`
- **BILL_PAYING_OFFICER**: Default value "BPO-001"
- **PO_COND_SR_NO**: `condSlno` from `po_ma_detail`
- **COND_TITLE**: `maFldDescr` from `po_ma_detail`
- **COND_TEXT**: `newValue` from `po_ma_detail`

## Testing

### Using Postman/cURL:
```bash
curl -X GET "http://localhost:8080/api/po-data/sections?poNo=195118100297"
```

### Expected Response Codes:
- `200 OK`: Data found and returned
- `404 NOT FOUND`: PO not found for given PO Number
- `400 BAD REQUEST`: PO Number parameter missing
- `500 INTERNAL SERVER ERROR`: Server error

## Notes
- All existing code remains unchanged
- Uses existing repositories (`PoHeaderRepository`, `PoMaHeaderRepository`)
- Date format: dd/MM/yyyy
- Default values used where data is not available
- MA list contains all amendments for the PO

