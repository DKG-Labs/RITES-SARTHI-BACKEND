package com.sarthi.controller;

import com.sarthi.dto.LoginRequestDto;
import com.sarthi.dto.LoginResponseDto;
import com.sarthi.dto.vendorDtos.PoResponseDto;
import com.sarthi.dto.vendorDtos.VendorPoHeaderResponseDto;
import com.sarthi.service.VendorPoService;
import com.sarthi.service.vendorService;
import com.sarthi.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor")
@CrossOrigin(origins = "*")
public class VendorController {

    @Autowired
    private VendorPoService vService;

//     @GetMapping("/poData")
// <<<<<<< Updated upstream
//     public ResponseEntity<Object> login(@RequestParam String vendorCode) {
//         List<VendorPoHeaderResponseDto> res = vService.getPoListByVendorCode(vendorCode);
//         return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(res), HttpStatus.OK);
//     }
// =======
    public ResponseEntity<Object> getPoData(@RequestParam String vendorCode) {
        List<VendorPoHeaderResponseDto> res = vService.getPoListByVendorCode(vendorCode);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(res), HttpStatus.OK);
    }

    @GetMapping("/po-assigned")
    public ResponseEntity<Object> getPoAssigned(@RequestParam(required = false) String vendorId) {
        // If vendorId is not provided, return empty list
        if (vendorId == null || vendorId.isEmpty()) {
            return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(List.of()), HttpStatus.OK);
        }
        List<VendorPoHeaderResponseDto> res = vService.getPoListByVendorCode(vendorId);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(res), HttpStatus.OK);
    }
// >>>>>>> Stashed changes

}
