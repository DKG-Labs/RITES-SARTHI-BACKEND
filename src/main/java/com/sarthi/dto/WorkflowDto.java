package com.sarthi.dto;

import lombok.Data;

import java.util.Date;
@Data
public class WorkflowDto {
    private Integer workflowId;
    private String workflowName;
    private String createdBy;
    private Date createdDate;
}
