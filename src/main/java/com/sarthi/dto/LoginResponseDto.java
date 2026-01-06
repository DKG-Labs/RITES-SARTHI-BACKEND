package com.sarthi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {

    private Integer userId;
    private String userName;
    private String roleName;
    private String token;

    private String rio;
}
