package com.sarthi.entity.CricsPos;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "po_cancellation_detail")
@Data
public class PoCancellationDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "header_id")
    private PoCancellationHeader header;

    private String rly;
    private String cakey;

    private String slno;
    private String plNo;
    private String poSr;

    private BigDecimal poBalQty;
    private BigDecimal cancQty;

    private String status;
    private String demStatus;
}

