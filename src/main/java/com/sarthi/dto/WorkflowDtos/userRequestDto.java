package com.sarthi.dto.WorkflowDtos;

import lombok.Data;

import java.util.List;


@Data
public class userRequestDto {

    private String userName;
    private String password;
    private List<String> roleNames;
    //  private String roleName;
    private String email;
    private String mobileNumber;
    private String employeeId;
    private String createdBy;

    private String clusterName;
    private String regionName;
    private Integer priority;  //if role secondary ie  this mandetory


    private List<Integer> ieUserIds;   // IE users mapped under Process IE


}

