package com.sarthi.controller;

import com.sarthi.dto.LoginRequestDto;
import com.sarthi.dto.LoginResponseDto;
import com.sarthi.dto.vendorDtos.PoResponseDto;
import com.sarthi.service.vendorService;
import com.sarthi.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {

//    @Autowired
//    private vendorService vService;

//    @GetMapping("/poData")
//    public ResponseEntity<Object> login(@RequestParam String vendorCode) {
//        List<PoResponseDto> res = vService.getPosByVendor(vendorCode);
//        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(res), HttpStatus.OK);
//    }

}
