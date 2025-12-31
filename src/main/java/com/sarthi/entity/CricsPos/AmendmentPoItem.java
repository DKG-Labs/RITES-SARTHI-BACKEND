package com.sarthi.entity.CricsPos;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "amendment_po_item")
@Data
public class AmendmentPoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "header_id")
    private AmendmentPoHeader header;

    private String rly;
    private String plNo;
    private String itemSrno;

    @Column(columnDefinition = "TEXT")
    private String itemDesc;

    private String consigneeCd;
    private String immsConsigneeCd;
    private String immsConsigneeName;
    private String consigneeDetail;

    private BigDecimal qty;
    private BigDecimal qtyCancelled;
    private BigDecimal rate;

    private String uomCd;
    private String uom;

    private BigDecimal basicValue;
    private BigDecimal salesTaxPer;
    private BigDecimal salesTax;

    private String discountType;
    private BigDecimal discountPer;
    private BigDecimal discount;

    private BigDecimal otherCharges;
    private BigDecimal value;

    private LocalDateTime deliveryDate;
    private LocalDateTime extDeliveryDate;

    private String allocation;
    private String billPayOff;
    private String billPayOffDesc;
    private String billPassOff;

}






