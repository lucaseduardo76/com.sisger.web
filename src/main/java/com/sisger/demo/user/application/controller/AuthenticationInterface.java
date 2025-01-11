package com.sisger.demo.user.application.controller;

import com.sisger.demo.authorization.domain.AuthenticationDTO;
import com.sisger.demo.user.domain.dto.RegisterDTO;
import com.sisger.demo.user.domain.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public interface AuthenticationInterface {

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data);

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@RequestBody @Valid RegisterDTO data);

}
