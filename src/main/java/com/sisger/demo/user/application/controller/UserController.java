package com.sisger.demo.user.application.controller;

import com.sisger.demo.exception.UnauthorizedException;
import com.sisger.demo.infra.security.TokenService;
import com.sisger.demo.user.domain.dto.ChangePasswordDTO;
import com.sisger.demo.user.application.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Log4j2
public class UserController implements UserControllerInterface{

    private final TokenService tokenService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void changePassword(String token, ChangePasswordDTO changePasswordDTO) {
        log.info("[inicia]  UserController - changePassword");

        var user = tokenService.getUserByToken(token);
        if(user == null) throw new UnauthorizedException("Invalid token");

        userService.changePassword(user, changePasswordDTO.getOldPassword(), changePasswordDTO.getNewPassword());

        log.info("[fim]  UserController - changePassword");

    }

}
