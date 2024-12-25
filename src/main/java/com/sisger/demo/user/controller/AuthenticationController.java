package com.sisger.demo.user.controller;


import com.sisger.demo.authorization.domain.AuthenticationDTO;
import com.sisger.demo.authorization.domain.LoginResponseDTO;
import com.sisger.demo.user.domain.RegisterDTO;
import com.sisger.demo.user.domain.User;
import com.sisger.demo.user.repository.UserRepository;
import com.sisger.demo.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
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

    @Override
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){

        var token = userService.login(data);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

   @Override
    public ResponseEntity<User> register(@RequestBody @Valid RegisterDTO data){
        if(this.userRepository.findByEmail(data.getEmail()) != null) return ResponseEntity.badRequest().build();
        User user = userService.create(data);

        URI uri  = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(uri).body(user);

    }


}
