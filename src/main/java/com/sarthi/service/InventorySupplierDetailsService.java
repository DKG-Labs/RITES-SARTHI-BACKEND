package com.sarthi.service;

import com.sarthi.dto.UnitAddressDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventorySupplierDetailsService {

    public List<String> getSuppliersByRawMaterial(String product);

    public List<UnitAddressDto>  getUnitsByCompany(String companyName);

    public List<String> getAddressByUnit(String unitName);


}
