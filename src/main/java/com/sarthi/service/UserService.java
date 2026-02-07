package com.sarthi.service;

import com.sarthi.dto.*;
import com.sarthi.dto.WorkflowDtos.userRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public UserDto createUser(userRequestDto userDto);
    public LoginResponseDto login(LoginRequestDto loginRequestDto);

    public LoginResponseDto loginBasedOnType(LoginRequestBasedTypeDto loginDto);


    public Object mapProcessIe(Long userId,
                               ProcessIeMappingRequestDto dto,
                               String createdBy);

    public Object setupIe(Long userId, IeSetupRequestDto dto);

    public UserDto createUserAndRole(userRequestDto userDto);
}
