package com.sisger.demo.user.application.controller;


import com.sisger.demo.user.domain.User;
import com.sisger.demo.user.domain.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public interface UserControllerInterface {

    @PatchMapping(value = "change-password")
    ResponseEntity<HttpStatus>  changePassword(@RequestHeader(name = "Authorization", required = true) String token,
                               @RequestBody @Valid ChangePasswordDTO changePasswordDTO);


    @GetMapping(value = "find-all")
    ResponseEntity<List<ResponseUserDTO>> findAll(@RequestHeader(name = "Authorization", required = true) String token);

    @GetMapping(value = "find-user-logged")
    ResponseEntity<ResponseUserDTO> findUserLogged(@RequestHeader(name = "Authorization", required = true) String token);

    @PostMapping(value = "new-user")
    ResponseEntity<ResponseUserDTO> create(
            @RequestHeader(name = "Authorization", required = true) String token,
            @RequestBody @Valid RequestUserDTO requestUserDTO);

    @PutMapping(value = "update-user")
    ResponseEntity<HttpStatus>  update(
            @RequestHeader(name = "Authorization", required = true) String token,
            @RequestBody @Valid RequestUpdateUserDTO requestUpdateUser);

    @DeleteMapping(value = "delete")
    ResponseEntity<HttpStatus>  delete(@RequestHeader(name = "Authorization", required = true) String token,
                                             @RequestBody RequestDeleteUserDTO requestDeleteUserDTO);
}
