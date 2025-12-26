package com.sarthi.service.Impl;

import com.sarthi.dto.vendorDtos.PoItemResponseDto;
import com.sarthi.dto.vendorDtos.PoResponseDto;
import com.sarthi.entity.PoHeader;
import com.sarthi.entity.PoItem;
import com.sarthi.repository.PoHeaderRepository;
import com.sarthi.service.vendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorServiceImpl implements vendorService {


//    @Autowired
//    private PoHeaderRepository poHeaderRepository;

//    public List<PoResponseDto> getPosByVendor(String vendorCode) {
//
//        List<PoHeader> headers =
//                poHeaderRepository.findAllByVendorCodeWithItems(vendorCode);
//
//        return headers.stream().map(this::mapToDto).toList();
//    }
//
//    private PoResponseDto mapToDto(PoHeader h) {
//
//        PoResponseDto dto = new PoResponseDto();
//     //   dto.setPoNumber(h.getPoNumber());
//        dto.setPoDate(h.getPoDate());
//        dto.setStatus(h.getPoStatus());
//        dto.setAmendmentNo(h.getVersion());
//        dto.setAmendmentDate(h.getReopenDate());
//
//        // ---- ITEMS ----
//        List<PoItemResponseDto> itemDtos = h.getItems().stream().map(i -> {
//            PoItemResponseDto idto = new PoItemResponseDto();
//            idto.setPoSerialNo(i.getPoSr());
//            idto.setConsignee(i.getConsignee());
//            idto.setOrderedQuantity(i.getPoQty());
//            idto.setDeliveryPeriod(
//                    i.getDp() != null ? i.getDp().toString() : null
//            );
//            idto.setStatus(i.getStatus());
//            return idto;
//        }).toList();
//
//        dto.setItems(itemDtos);
//
//
//        // ---- HEADER CALCULATIONS ----
//        dto.setPoQuantity(
//                h.getItems().stream()
//                        .map(PoItem::getPoQty)
//                        .filter(q -> q != null)
//                        .mapToInt(Integer::intValue)
//                        .sum()
//        );
//
//        dto.setUnit(
//                h.getItems().stream()
//                        .map(PoItem::getUnit)
//                        .filter(u -> u != null)
//                        .findFirst()
//                        .orElse(null)
//        );
//
//        dto.setDescription(
//                h.getItems().stream()
//                        .map(PoItem::getPlNo)
//                        .filter(d -> d != null)
//                        .findFirst()
//                        .orElse(null)
//        );
//
//        return dto;
//    }

}
