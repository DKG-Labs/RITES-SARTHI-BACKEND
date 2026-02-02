package com.sarthi.controller;

import com.sarthi.dto.UnitAddressDto;
import com.sarthi.service.InventorySupplierDetailsService;
import com.sarthi.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class InventorySupplierController {


    @Autowired
    private InventorySupplierDetailsService inventorySupplierDetailsService;

    //  Raw Material → Supplier (Company) Names
    @GetMapping("/by-product/{product}")
    public ResponseEntity<Object> getSuppliersByProduct(
            @PathVariable String product) {

        List<String> companyNames =
                inventorySupplierDetailsService.getSuppliersByRawMaterial(product);

        return new ResponseEntity<>(
                ResponseBuilder.getSuccessResponse(companyNames),
                HttpStatus.OK
        );
    }


    //  Company → Unit Names
    @GetMapping("/by-company")
    public ResponseEntity<Object> getUnitsByCompany(
            @RequestParam String companyName) {

        List<UnitAddressDto>  unitNames =
                inventorySupplierDetailsService.getUnitsByCompany(companyName);

        return new ResponseEntity<>(
                ResponseBuilder.getSuccessResponse(unitNames),
                HttpStatus.OK
        );
    }



    //  Unit → Address
    @GetMapping("/by-unit/{unitName}")
    public ResponseEntity<Object> getAddressByUnit(
            @PathVariable String unitName) {

        List<String> addresses =
                inventorySupplierDetailsService.getAddressByUnit(unitName);

        return new ResponseEntity<>(
                ResponseBuilder.getSuccessResponse(addresses),
                HttpStatus.OK
        );
    }


}
