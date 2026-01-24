# Data Fetching Fix - Dimensional Inspection & Application Deflection Test

## Problem
Data was not fetching from the GET APIs because the child samples were not being loaded with the parent entities.

## Root Cause
The `@OneToMany` relationships in both entities were using **lazy loading** (default behavior):
```java
@OneToMany(mappedBy = "finalDimensionalInspection", cascade = CascadeType.ALL, orphanRemoval = true)
private List<FinalDimensionalInspectionSample> samples;
```

When the API response was being serialized to JSON, the Hibernate session was already closed, causing a `LazyInitializationException` or returning null samples.

## Solution
Changed both entities to use **EAGER loading** so samples are loaded immediately with the parent:

### 1. FinalDimensionalInspection.java (Line 99)
```java
@OneToMany(mappedBy = "finalDimensionalInspection", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
private List<FinalDimensionalInspectionSample> samples;
```

### 2. FinalApplicationDeflection.java (Line 99)
```java
@OneToMany(mappedBy = "finalApplicationDeflection", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
private List<FinalApplicationDeflectionSample> samples;
```

## Impact

### Before Fix
- GET `/api/final-inspection/submodules/dimensional-inspection/call/{callNo}` returned parent data but samples were null
- GET `/api/final-inspection/submodules/application-deflection/call/{callNo}` returned parent data but samples were null
- Frontend received empty samples array

### After Fix
- GET APIs now return complete parent data WITH nested samples
- Frontend receives full data structure with all samples
- Data displays correctly in the form

## API Response Example

### Dimensional Inspection
```json
{
  "responseData": [
    {
      "id": 1,
      "inspectionCallNo": "EP-01090004",
      "lotNo": "lot1",
      "heatNo": "T844929",
      "sampleSize": 200,
      "status": "PENDING",
      "remarks": "...",
      "samples": [
        {
          "id": 1,
          "samplingNo": 1,
          "goGaugeFailed": 1,
          "noGoGaugeFailed": 1,
          "flatnessFailed": 6,
          "createdAt": "2026-01-23T10:30:00",
          "createdBy": "user123"
        }
      ]
    }
  ]
}
```

## Files Modified
1. `FinalDimensionalInspection.java` - Added `fetch = FetchType.EAGER`
2. `FinalApplicationDeflection.java` - Added `fetch = FetchType.EAGER`

## Testing
After deploying this fix:
1. Call the GET APIs
2. Verify samples array is populated
3. Frontend should now display data correctly
4. No more null/empty samples in responses

