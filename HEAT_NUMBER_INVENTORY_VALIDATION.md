# Heat Number Inventory Validation Implementation

## Overview
This document describes the implementation of inventory validation logic for heat numbers in the Vendor Dashboard's "Raw Material Raising Call" section. The system validates heat number availability while maintaining complete historical records in the database.

## Problem Statement
Previously, vendors could select any heat number when raising inspection calls, including those with exhausted inventory. This led to:
- Attempts to raise calls for unavailable materials
- Data inconsistency issues
- Wasted time for both vendors and inspectors

## Solution Architecture

### Key Design Principle: Database Retains All Records
**IMPORTANT:** All heat numbers (available + exhausted) are permanently retained in the database for:
- **Audit Trail:** Complete history of all materials received
- **Compliance:** Regulatory requirements for record keeping
- **Reporting:** Historical analytics and trend analysis
- **Data Integrity:** No data loss, complete traceability

### Filtering Strategy: Service Layer, Not Database Layer
The filtering of available vs exhausted heat numbers happens in the **service layer**, not at the database level:

1. **Repository Layer:** Fetches ALL heat numbers from database (no filtering)
2. **Service Layer:** 
   - Marks each heat number as available/unavailable based on business rules
   - Filters results based on method requirements
3. **Controller Layer:** Returns appropriate data based on use case

### Availability Determination
A heat number is marked as **available** when:
1. `tcQtyRemaining > 0` (has remaining quantity)
2. `inventory_status = FRESH_PO` (not under inspection or consumed)

A heat number is marked as **unavailable** when:
- `tcQtyRemaining <= 0` (exhausted)
- `inventory_status ∈ {UNDER_INSPECTION, ACCEPTED, REJECTED}` (allocated/consumed)

**Exhausted heat numbers remain in the database but are filtered out from vendor dropdowns.**

## Architecture Components

### 1. DTO Layer
**File:** `src/main/java/com/sarthi/dto/AvailableHeatNumberDto.java`

**Key Fields:**
- `heatNumber`, `tcNumber` - Identifiers
- `tcQtyRemaining` - Remaining quantity (BigDecimal)
- `isAvailable` - Computed field indicating availability status
- `status` - Inventory status (FRESH_PO, UNDER_INSPECTION, etc.)
- Material information (rawMaterial, gradeSpecification)

**Important:** The `isAvailable` field is computed in the service layer based on:
- Remaining quantity > 0
- Inventory status = FRESH_PO

### 2. Repository Layer
**Files:**
- `src/main/java/com/sarthi/repository/RmHeatTcMappingRepository.java`
- `src/main/java/com/sarthi/repository/InventoryEntryRepository.java`

**Key Method:**
```java
@Query("SELECT h FROM RmHeatTcMapping h " +
       "WHERE h.inspectionRequest.vendorCode = :vendorCode " +
       "ORDER BY h.createdAt DESC")
List<RmHeatTcMapping> findAllByVendorCode(@Param("vendorCode") String vendorCode);
```

**Design Decision:** 
- NO filtering at database level
- Returns ALL heat numbers (available + exhausted)
- Ensures complete data retention for audit trail

### 3. Service Layer
**Files:**
- `src/main/java/com/sarthi/service/VendorHeatNumberService.java` (Interface)
- `src/main/java/com/sarthi/service/Impl/VendorHeatNumberServiceImpl.java` (Implementation)

**Key Methods:**

#### `getAvailableHeatNumbers(String vendorCode)`
```java
// 1. Fetch ALL heat numbers from database
List<RmHeatTcMapping> allMappings = repository.findAllByVendorCode(vendorCode);

// 2. Convert to DTOs and mark availability
List<AvailableHeatNumberDto> allDtos = allMappings.stream()
    .map(this::mapToDto)  // Sets isAvailable field
    .collect(Collectors.toList());

// 3. Filter to return only available ones
return allDtos.stream()
    .filter(AvailableHeatNumberDto::isAvailable)
    .collect(Collectors.toList());
```

**Purpose:** Returns only available heat numbers for vendor dropdown

#### `getAllHeatNumbers(String vendorCode)`
```java
// 1. Fetch ALL heat numbers from database
List<RmHeatTcMapping> allMappings = repository.findAllByVendorCode(vendorCode);

// 2. Convert to DTOs and mark availability
// 3. Return complete list (no filtering)
return allMappings.stream()
    .map(this::mapToDto)  // Sets isAvailable field
    .collect(Collectors.toList());
```

**Purpose:** Returns all heat numbers (available + exhausted) for reporting

#### `mapToDto(RmHeatTcMapping mapping)`
Converts entity to DTO and determines availability:
1. Parses `tcQtyRemaining` to BigDecimal
2. Sets `isAvailable = true` if quantity > 0
3. Enriches with inventory data
4. May override `isAvailable = false` if inventory status is not FRESH_PO

**Key Point:** This method does NOT filter; it only marks availability status.

### 4. Controller Layer
**File:** `src/main/java/com/sarthi/controller/VendorController.java`

**Endpoints:**

#### 1. GET `/api/vendor/available-heat-numbers/{vendorCode}`
- Returns only available heat numbers (isAvailable = true)
- Used for vendor dropdown in "Raw Material Raising Call" section
- Exhausted heat numbers are filtered out but remain in database

#### 2. GET `/api/vendor/all-heat-numbers/{vendorCode}`
- Returns all heat numbers (available + exhausted)
- Each DTO has isAvailable field indicating status
- Used for reporting, analytics, and historical data

#### 3. GET `/api/vendor/check-heat-availability?heatNumber=X&tcNumber=Y`
- Checks if specific heat number is available
- Returns boolean availability status

## Data Flow

```
Database (ALL records)
    ↓
Repository Layer (fetch ALL)
    ↓
Service Layer (mark availability)
    ↓
    ├─→ getAvailableHeatNumbers() → Filter isAvailable=true → Vendor Dropdown
    └─→ getAllHeatNumbers() → No filtering → Reports/Analytics
```

## Benefits of This Approach

1. **Data Integrity:** No data loss, complete historical records
2. **Audit Trail:** All heat numbers retained for compliance
3. **Flexibility:** Same data source for different use cases
4. **Maintainability:** Business logic in service layer, not scattered
5. **Reporting:** Complete data available for analytics
6. **Compliance:** Meets regulatory requirements for record keeping

## API Documentation

### 1. Get Available Heat Numbers
**Endpoint:** `GET /api/vendor/available-heat-numbers/{vendorCode}`

**Description:** Returns only heat numbers with available inventory (isAvailable = true)

**Response Example:**
```json
{
  "status": {
    "statusCode": 200,
    "message": "Success"
  },
  "data": [
    {
      "id": 1,
      "heatNumber": "HT-2025-010",
      "tcNumber": "TC-50010",
      "tcDate": "2025-11-18",
      "manufacturer": "Steel Works Ltd",
      "tcQuantity": 2000.0,
      "tcQtyRemaining": 500.0,
      "offeredQty": 1500.0,
      "rawMaterial": "TMT Bars",
      "gradeSpecification": "Fe 500D",
      "status": "FRESH_PO",
      "isAvailable": true
    }
  ]
}
```

**Note:** Exhausted heat numbers (isAvailable = false) are NOT included in this response but remain in the database.

### 2. Get All Heat Numbers
**Endpoint:** `GET /api/vendor/all-heat-numbers/{vendorCode}`

**Description:** Returns all heat numbers including exhausted ones with availability status

**Response Example:**
```json
{
  "status": {
    "statusCode": 200,
    "message": "Success"
  },
  "data": [
    {
      "id": 1,
      "heatNumber": "HT-2025-010",
      "tcNumber": "TC-50010",
      "tcQtyRemaining": 500.0,
      "status": "FRESH_PO",
      "isAvailable": true
    },
    {
      "id": 2,
      "heatNumber": "HT-2025-005",
      "tcNumber": "TC-50005",
      "tcQtyRemaining": 0.0,
      "status": "ACCEPTED",
      "isAvailable": false
    }
  ]
}
```

**Note:** This endpoint returns both available and exhausted heat numbers. Use the `isAvailable` field to distinguish.

### 3. Check Heat Availability
**Endpoint:** `GET /api/vendor/check-heat-availability?heatNumber={heatNumber}&tcNumber={tcNumber}`

**Response Example:**
```json
{
  "status": {
    "statusCode": 200,
    "message": "Success"
  },
  "data": {
    "heatNumber": "HT-2025-010",
    "tcNumber": "TC-50010",
    "isAvailable": true
  }
}
```

## Frontend Integration

### Example: Fetching Available Heat Numbers for Dropdown
```javascript
const fetchAvailableHeatNumbers = async (vendorCode) => {
  try {
    const response = await fetch(`/api/vendor/available-heat-numbers/${vendorCode}`);
    const result = await response.json();

    if (result.data && result.data.length > 0) {
      // Populate dropdown with available heat numbers only
      setHeatNumbers(result.data);
    } else {
      // Show message: "No heat numbers with available inventory"
      // Note: Exhausted heat numbers still exist in database for audit trail
      showNoInventoryMessage();
    }
  } catch (error) {
    console.error('Error fetching heat numbers:', error);
  }
};
```

### Example: Fetching All Heat Numbers for Reporting
```javascript
const fetchAllHeatNumbers = async (vendorCode) => {
  try {
    const response = await fetch(`/api/vendor/all-heat-numbers/${vendorCode}`);
    const result = await response.json();

    // Separate available and exhausted for display
    const available = result.data.filter(h => h.isAvailable);
    const exhausted = result.data.filter(h => !h.isAvailable);

    console.log(`Total: ${result.data.length}, Available: ${available.length}, Exhausted: ${exhausted.length}`);

    // Display in reporting dashboard with status indicators
    displayInventoryReport(available, exhausted);
  } catch (error) {
    console.error('Error fetching heat numbers:', error);
  }
};
```

## Database Schema

### Tables Involved

1. **rm_heat_tc_mapping**
   - Stores ALL heat numbers (available + exhausted)
   - `tc_qty_remaining` - Remaining quantity (String format)
   - Records are NEVER deleted, only updated

2. **inventory_entries**
   - `status` - ENUM('FRESH_PO', 'UNDER_INSPECTION', 'ACCEPTED', 'REJECTED')
   - `tc_quantity` - Total TC quantity
   - Records are NEVER deleted, only status updated

3. **rm_heat_quantities**
   - `qtyLeft` - Remaining quantity after inspection
   - `qtyAccepted` - Accepted quantity
   - `qtyRejected` - Rejected quantity

**Important:** No records are deleted from these tables. Status and quantities are updated to reflect current state while preserving historical data.

## Testing Scenarios

1. **Vendor with only available heat numbers**
   - `getAvailableHeatNumbers()` returns all heat numbers
   - `getAllHeatNumbers()` returns same list with isAvailable=true

2. **Vendor with only exhausted heat numbers**
   - `getAvailableHeatNumbers()` returns empty list
   - `getAllHeatNumbers()` returns all heat numbers with isAvailable=false
   - Database still contains all records

3. **Vendor with mixed inventory**
   - `getAvailableHeatNumbers()` returns only available ones
   - `getAllHeatNumbers()` returns complete list with proper isAvailable flags

4. **Heat number becomes exhausted**
   - Record remains in database
   - `tcQtyRemaining` updated to "0"
   - `isAvailable` computed as false
   - Filtered out from vendor dropdown but visible in reports

## Maintenance and Operations

### Monitoring
- Log messages indicate total vs available heat numbers
- Example: "Returning 5 available heat numbers (filtered from 12 total)"

### Data Cleanup
**DO NOT delete exhausted heat numbers from database.**
- Violates audit trail requirements
- Breaks compliance and regulatory requirements
- Loses historical data for reporting

### Performance Considerations
- Fetching all records and filtering in service layer is acceptable for typical vendor inventory sizes
- If performance becomes an issue, consider:
  - Database indexing on `vendorCode` and `tcQtyRemaining`
  - Caching frequently accessed vendor data
  - Pagination for large datasets

## Future Enhancements

1. Add pagination for vendors with large inventories
2. Implement caching with TTL for frequently accessed data
3. Add WebSocket support for real-time inventory updates
4. Create scheduled job to archive very old exhausted records (with proper audit trail)
5. Add bulk availability check endpoint
6. Implement inventory reservation system to prevent race conditions

