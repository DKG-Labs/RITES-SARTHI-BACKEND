package com.sarthi.service.Impl;

import com.sarthi.dto.UnitAddressDto;
import com.sarthi.repository.InventorySupplierDetailsRepository;
import com.sarthi.service.InventorySupplierDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventorySupplierDetailsServiceImpl implements InventorySupplierDetailsService {
    @Autowired
   private InventorySupplierDetailsRepository inventorySupplierDetailsRepository;

    @Override
    public List<String> getSuppliersByRawMaterial(String product) {
        return inventorySupplierDetailsRepository.findCompanyNamesByProduct(product);
    }

    @Override
    public List<UnitAddressDto> getUnitsByCompany(String companyName) {
        return inventorySupplierDetailsRepository
                .findUnitsWithAddressByCompany(companyName);
    }


    @Override
    public List<String> getAddressByUnit(String unitName) {
        return inventorySupplierDetailsRepository.findAddressByUnit(unitName);
    }
}
