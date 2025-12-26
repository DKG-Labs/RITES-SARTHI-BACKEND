package com.sarthi.controller;

import com.sarthi.service.Impl.CrisPoAsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sync")
public class CrisPoSyncController {

    @Autowired
    private CrisPoAsyncService asyncService;

    @PostMapping("/po")
    public ResponseEntity<String> syncPo(@RequestParam String date) {

        asyncService.syncPos(date);

        return ResponseEntity.ok(
                "PO sync triggered for date: "
        );
    }

    @PostMapping("/poMa")
    public ResponseEntity<String> syncMa(@RequestParam String date) {

        asyncService.syncMa(date);

        return ResponseEntity.ok(
                "Po Ma  triggered for date: "
        );
    }




}
