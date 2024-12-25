package com.sisger.demo.authorization.controller;


import com.sisger.demo.authorization.domain.AuthenticationDTO;
import com.sisger.demo.authorization.domain.LoginResponseDTO;
import com.sisger.demo.infra.security.TokenService;
import com.sisger.demo.user.domain.RegisterDTO;
import com.sisger.demo.user.domain.User;
import com.sisger.demo.user.repository.UserRepository;
import com.sisger.demo.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Log4j2
public class AuthenticationController {



    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){

        if(this.userRepository.findByEmail(data.getEmail()) != null) return ResponseEntity.badRequest().build();
        userService.create(data);
        return ResponseEntity.ok().build();

    }


}
