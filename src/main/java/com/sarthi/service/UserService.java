package com.sarthi.service;

import com.sarthi.dto.LoginRequestDto;
import com.sarthi.dto.LoginResponseDto;
import com.sarthi.dto.UserDto;
import com.sarthi.dto.WorkflowDtos.userRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public UserDto createUser(userRequestDto userDto);
    public LoginResponseDto login(LoginRequestDto loginRequestDto);
}
