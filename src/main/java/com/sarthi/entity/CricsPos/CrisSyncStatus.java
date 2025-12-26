package com.sarthi.entity.CricsPos;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "cris_sync_status")
@Data
public class CrisSyncStatus {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        // PO / MA / AMENDMENT
        @Column(name = "ref_type", nullable = false)
        private String refType;

        // POKEY / MAKEY
        @Column(name = "ref_key", nullable = false)
        private String refKey;

        @Column(name = "rly")
        private String rly;

        // FETCHED / SAVED / FAILED
        @Column(name = "status")
        private String status;

        @Column(name = "error_message", length = 2000)
        private String errorMessage;

        @Column(name = "fetched_at")
        private LocalDateTime fetchedAt;

        @Column(name = "processed_at")
        private LocalDateTime processedAt;

}
