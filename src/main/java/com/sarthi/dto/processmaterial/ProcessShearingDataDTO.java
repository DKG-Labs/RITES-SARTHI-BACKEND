package com.sarthi.dto.processmaterial;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProcessShearingDataDTO {

    private Long id;
    private String inspectionCallNo;
    private String poNo;
    private String lineNo;
    private String shift;
    private Integer hourIndex;
    private String hourLabel;
    private Boolean noProduction;
    private String lotNo;
    private BigDecimal lengthCutBar1;
    private BigDecimal lengthCutBar2;
    private BigDecimal lengthCutBar3;
    private String improperDia1;
    private String improperDia2;
    private String improperDia3;
    private String sharpEdges1;
    private String sharpEdges2;
    private String sharpEdges3;
    private String crackedEdges1;
    private String crackedEdges2;
    private String crackedEdges3;
    private Integer lengthCutBarRejected;
    private Integer improperDiaRejected;
    private Integer sharpEdgesRejected;
    private Integer crackedEdgesRejected;
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}

