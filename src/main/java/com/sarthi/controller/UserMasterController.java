package com.sarthi.controller;

import com.sarthi.dto.LoginRequestDto;
import com.sarthi.dto.LoginResponseDto;
import com.sarthi.dto.UserDto;
import com.sarthi.dto.WorkflowDtos.userRequestDto;
import com.sarthi.service.UserService;
import com.sarthi.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserMasterController {

    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody userRequestDto userRequestDto) {
        UserDto user = userService.createUser(userRequestDto);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto res = userService.login(loginRequestDto);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(res), HttpStatus.OK);
    }

}
