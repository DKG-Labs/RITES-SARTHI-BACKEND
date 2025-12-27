$body = Get-Content -Path "test-inspection-call.json" -Raw

try {
    $response = Invoke-RestMethod -Uri "http://localhost:8080/sarthi-backend/api/raw-material/inspectionCall" -Method POST -ContentType "application/json" -Body $body
    Write-Host "SUCCESS! Response:" -ForegroundColor Green
    $response | ConvertTo-Json -Depth 10
} catch {
    Write-Host "ERROR! Status Code: $($_.Exception.Response.StatusCode.value__)" -ForegroundColor Red
    Write-Host "Error Message:" -ForegroundColor Red
    $_.Exception.Message
    if ($_.ErrorDetails.Message) {
        Write-Host "Error Details:" -ForegroundColor Red
        $_.ErrorDetails.Message
    }
}

