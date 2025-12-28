package com.sarthi.dto.WorkflowDtos;

import lombok.Data;

import java.util.Date;

@Data
public class TransitionDto {
    private Integer transitionId;
    private Integer workflowId;
    private String transitionName;

    private Integer currentRoleId;
    private Integer nextRoleId;

    private String currentRoleName;
    private String nextRoleName;

    private Integer transitionOrder;
    private Date createdDate;
    private String workflowName;
    private Integer previousRoleId;
    private String previousRoleName;
    private Integer conditionId;
    private String conditionKey;
    private String conditionValue;
    private String createdBy;
}

