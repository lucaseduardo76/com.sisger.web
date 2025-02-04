package com.sisger.demo.user.application.controller;


import com.sisger.demo.authorization.domain.AuthenticationDTO;
import com.sisger.demo.authorization.domain.LoginResponseDTO;
import com.sisger.demo.company.application.service.CompanyService;
import com.sisger.demo.user.domain.dto.RegisterDTO;
import com.sisger.demo.user.domain.User;
import com.sisger.demo.user.domain.dto.ResponseUserDTO;
import com.sisger.demo.user.infra.repository.UserRepository;
import com.sisger.demo.user.application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Log4j2
public class AuthenticationController implements AuthenticationInterface{

    private final UserRepository userRepository;
    private final UserService userService;
    private final CompanyService companyService;

    @Override
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data){

        var token = userService.login(data);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }


    @Override
    public ResponseEntity<ResponseUserDTO> register(@RequestBody @Valid RegisterDTO registerDTO){

       log.info("[inicia] AuthenticationController - register");
       var user = userService.createMainAccount(registerDTO);
        URI uri  = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        log.info("[fim] AuthenticationController - register");
        return ResponseEntity.created(uri).body(user);

    }


}
