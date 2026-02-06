package com.sarthi.dto;

import lombok.Data;

@Data
public class LoginRequestBasedTypeDto {
    private String loginType;
    private String loginId;
    private String password;
}
