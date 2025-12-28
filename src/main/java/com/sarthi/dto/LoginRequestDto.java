package com.sarthi.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private Integer userId;
    private String password;
}
