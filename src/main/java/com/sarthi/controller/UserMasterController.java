package com.sarthi.controller;

import com.sarthi.dto.*;
import com.sarthi.dto.WorkflowDtos.userRequestDto;
import com.sarthi.service.UserService;
import com.sarthi.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/loginBasedOnType")
    public ResponseEntity<Object> loginBasedOnType(@RequestBody LoginRequestBasedTypeDto loginRequestBasedTypeDto) {
        LoginResponseDto res = userService.loginBasedOnType(loginRequestBasedTypeDto);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(res), HttpStatus.OK);
    }


    @PostMapping("/api/OnlyRoleBasedCreation")
    public ResponseEntity<Object> createUserAndRole(@RequestBody userRequestDto userRequestDto) {
        UserDto user = userService.createUserAndRole(userRequestDto);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(user), HttpStatus.OK);
    }

    @PostMapping("/api/IeMapping")
    public ResponseEntity<Object> creatMapingIe(@RequestParam Long userId, @RequestBody IeSetupRequestDto dto) {

        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse( userService.setupIe(userId,dto)), HttpStatus.OK);
    }

    @PostMapping("/api/processIeMapping")
    public ResponseEntity<Object> createMappingProcessIe(@RequestBody ProcessIeMappingRequestDto dto, @RequestParam Long userId,
                                                         @RequestParam String createdBy) {

        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(userService.mapProcessIe(userId, dto, createdBy)), HttpStatus.OK);
    }




}
