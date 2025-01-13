package com.sisger.demo.util;

import com.sisger.demo.exception.UnauthorizedException;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;

@Log4j2
public class PasswordHandler {

    public static void validatePassword(String passwordReq, String passwordUser, PasswordEncoder passwordEncoder){
        log.info("[inicia]  PasswordHandler - validatePassword");
        if(passwordReq == null || passwordUser == null || passwordEncoder == null) {
            throw new UnauthorizedException("Invalid password - null");
        }

        if(!passwordEncoder.matches(passwordReq, passwordUser)) {
            throw new UnauthorizedException("Invalid password");
        }
        log.info("[fim]  PasswordHandler - validatePassword");
    }
}
