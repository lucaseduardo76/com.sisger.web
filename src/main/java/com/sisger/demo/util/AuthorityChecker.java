package com.sisger.demo.util;

import com.sisger.demo.exception.UnauthorizedException;
import com.sisger.demo.user.domain.User;
import org.springframework.security.core.GrantedAuthority;

public class AuthorityChecker {

    public static boolean hasMainAuthority(User user) {
        for (GrantedAuthority authority : user.getAuthorities()) {
            if ("ROLE_MAIN".equals(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasManagerAuthority(User user) {
        for (GrantedAuthority authority : user.getAuthorities()) {
            if ("ROLE_MANAGER".equals(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    public static void requireMainAuthority(User user) {
        if (!hasMainAuthority(user)) {
            throw new UnauthorizedException("Usuário não possui autoridade MAIN.");
        }
    }

    public static void requireManagerAuthority(User user) {
        if (!hasManagerAuthority(user)) {
            throw new UnauthorizedException("Usuário não possui autoridade MANAGER, consulte o administrador.");
        }
    }
}