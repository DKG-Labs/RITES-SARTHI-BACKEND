# Final Hardness Test Data Model

## Overview

The Final Hardness Test module uses a **two-table design** to support:
- Multiple sampling rounds (1st sampling, 2nd sampling, etc.)
- Dynamic sample counts per round
- Pause and resume functionality
- Comprehensive audit trails

## Table Design

### 1. Parent Table: `final_hardness_test`

**Purpose**: Stores ONE row per inspection session for a given Lot + Heat + Inspection Call.

**Columns**:

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | Unique identifier |
| inspection_call_no | VARCHAR(50) | NOT NULL, UNIQUE (with lot_no, heat_no) | External workflow call number |
| lot_no | VARCHAR(100) | NOT NULL, UNIQUE (with call_no, heat_no) | Lot number under inspection |
| heat_no | VARCHAR(100) | NOT NULL, UNIQUE (with call_no, lot_no) | Heat number under inspection |
| qty_no | INT | | Quantity offered for this lot + heat |
| status | VARCHAR(50) | NOT NULL, DEFAULT 'PENDING' | Overall result: OK, NOT_OK, PENDING |
| rejected | INT | DEFAULT 0 | Total rejected samples across ALL samplings |
| remarks | TEXT | | Final inspector remarks |
| created_by | VARCHAR(100) | NOT NULL, IMMUTABLE | User who created the record (set once) |
| created_at | TIMESTAMP | NOT NULL, IMMUTABLE | When record was first created |
| updated_by | VARCHAR(100) | NOT NULL | User who last updated the record |
| updated_at | TIMESTAMP | NOT NULL | When record was last updated |

**Unique Constraint**: `(inspection_call_no, lot_no, heat_no)` - ensures one inspection session per combination.

**Indexes**:
- `idx_final_hard_call_no` on `inspection_call_no`
- `idx_final_hard_lot_no` on `lot_no`
- `idx_final_hard_heat_no` on `heat_no`
- `idx_final_hard_status` on `status`

### 2. Child Table: `final_hardness_test_sample`

**Purpose**: Stores EVERY individual sample value entered in the UI.

**Columns**:

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | Unique identifier |
| final_hardness_test_id | BIGINT | FK, NOT NULL | References parent inspection session |
| sampling_no | INT | NOT NULL | Sampling attempt: 1, 2, 3, ... |
| sample_no | INT | NOT NULL | Sequence within sampling: 1, 2, 3, ... |
| sample_value | DOUBLE | NOT NULL | Actual hardness value |
| is_rejected | BOOLEAN | NOT NULL, DEFAULT FALSE | Whether sample failed criteria |
| created_at | TIMESTAMP | NOT NULL | When sample was recorded |

**Unique Constraint**: `(final_hardness_test_id, sampling_no, sample_no)` - ensures no duplicate samples.

**Foreign Key**: `final_hardness_test_id` → `final_hardness_test.id` (CASCADE DELETE)

**Indexes**:
- `idx_fhts_parent_id` on `final_hardness_test_id`
- `idx_fhts_sampling_no` on `sampling_no`
- `idx_fhts_is_rejected` on `is_rejected`

## Audit Rules

### On FIRST Save (Inspection Start)

```
INSERT INTO final_hardness_test (
    inspection_call_no, lot_no, heat_no, qty_no,
    status, remarks,
    created_by, created_at, updated_by, updated_at
) VALUES (
    'EP-01090004', 'lot2', 'T844929', 81,
    'PENDING', NULL,
    'inspector_user', NOW(), 'inspector_user', NOW()
);
```

### On SUBSEQUENT Saves (Pause/Resume/Add Samples)

```
UPDATE final_hardness_test
SET updated_by = 'inspector_user',
    updated_at = NOW(),
    remarks = 'Paused after 1st sampling',
    status = 'PENDING'
WHERE id = 1;
-- created_by and created_at are NEVER changed
```

## API Endpoints

### Save or Update Hardness Test

**POST** `/api/final-inspection/hardness-test`

**Request**:
```json
{
  "inspectionCallNo": "EP-01090004",
  "lotNo": "lot2",
  "heatNo": "T844929",
  "qtyNo": 81,
  "remarks": "Paused after 1st sampling",
  "samples": [
    { "samplingNo": 1, "sampleNo": 1, "sampleValue": 0.40, "isRejected": true },
    { "samplingNo": 1, "sampleNo": 2, "sampleValue": 0.50, "isRejected": true },
    { "samplingNo": 1, "sampleNo": 3, "sampleValue": 0.47, "isRejected": true }
  ]
}
```

**Response**:
```json
{
  "id": 1,
  "inspectionCallNo": "EP-01090004",
  "lotNo": "lot2",
  "heatNo": "T844929",
  "qtyNo": 81,
  "status": "PENDING",
  "rejected": 3,
  "remarks": "Paused after 1st sampling",
  "createdBy": "inspector_user",
  "createdAt": "2026-01-21T10:00:00",
  "updatedBy": "inspector_user",
  "updatedAt": "2026-01-21T10:05:00",
  "samples": [
    { "id": 1, "samplingNo": 1, "sampleNo": 1, "sampleValue": 0.40, "isRejected": true, "createdAt": "2026-01-21T10:00:00" },
    { "id": 2, "samplingNo": 1, "sampleNo": 2, "sampleValue": 0.50, "isRejected": true, "createdAt": "2026-01-21T10:00:00" },
    { "id": 3, "samplingNo": 1, "sampleNo": 3, "sampleValue": 0.47, "isRejected": true, "createdAt": "2026-01-21T10:00:00" }
  ]
}
```

## Key Design Principles

1. **One Inspection Session Per Combination**: Unique constraint on `(inspection_call_no, lot_no, heat_no)`.
2. **No Fixed Columns**: No `hardness_1st_sample`, `hardness_2nd_sample` columns.
3. **No Separate Sampling Table**: Sampling rounds are identified by `sampling_no` in the child table.
4. **No Per-Round Status**: Overall status lives ONLY in `final_hardness_test.status`.
5. **Immutable Audit Fields**: `created_by` and `created_at` are set once and never changed.
6. **Mutable Audit Fields**: `updated_by` and `updated_at` are updated on every save.
7. **Cascade Delete**: Deleting a parent record automatically deletes all child samples.

## Example Workflows

### Workflow 1: First Sampling (Create)

1. Inspector starts inspection → POST `/api/final-inspection/hardness-test`
2. Backend creates parent row with `status = PENDING`
3. Backend inserts 3 sample rows with `sampling_no = 1`
4. Backend counts rejected samples and updates `rejected = 3`

### Workflow 2: Pause After First Sampling

1. Inspector pauses → POST `/api/final-inspection/hardness-test` (same call/lot/heat)
2. Backend finds existing parent row
3. Backend updates `updated_by`, `updated_at`, `remarks`
4. Backend inserts new sample rows (if any)
5. Backend recalculates `rejected` count

### Workflow 3: Resume and Add Second Sampling

1. Inspector resumes → POST `/api/final-inspection/hardness-test` (same call/lot/heat)
2. Backend finds existing parent row
3. Backend inserts new sample rows with `sampling_no = 2`
4. Backend recalculates `rejected` count
5. Backend may update `status` based on business logic

## Queries

### Get All Samples for an Inspection

```sql
SELECT s.* FROM final_hardness_test_sample s
WHERE s.final_hardness_test_id = 1
ORDER BY s.sampling_no, s.sample_no;
```

### Count Rejected Samples

```sql
SELECT COUNT(*) as rejected_count
FROM final_hardness_test_sample
WHERE final_hardness_test_id = 1 AND is_rejected = TRUE;
```

### Get Latest Sampling Number

```sql
SELECT MAX(sampling_no) as latest_sampling
FROM final_hardness_test_sample
WHERE final_hardness_test_id = 1;
```

### Get All Inspections by Status

```sql
SELECT * FROM final_hardness_test
WHERE status = 'PENDING'
ORDER BY created_at DESC;
```

