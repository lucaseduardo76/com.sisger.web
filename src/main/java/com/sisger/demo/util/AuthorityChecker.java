package com.sisger.demo.util;

import com.sisger.demo.exception.UnauthorizedException;
import com.sisger.demo.user.domain.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;

@Log4j2
public class AuthorityChecker {

    public static boolean hasMainAuthority(User user) {
        log.info("[inicia] AuthorityChecker - hasMainAuthority");
        for (GrantedAuthority authority : user.getAuthorities()) {
            if ("ROLE_MAIN".equals(authority.getAuthority())) {
                log.info("[fim - true] AuthorityChecker - hasMainAuthority");
                return true;
            }
        }
        log.info("[fim - false] AuthorityChecker - hasMainAuthority");
        return false;
    }

    public static boolean hasManagerAuthority(User user) {
        log.info("[inicia] AuthorityChecker - hasManagerAuthority");
        for (GrantedAuthority authority : user.getAuthorities()) {
            if ("ROLE_MANAGER".equals(authority.getAuthority())) {
                log.info("[fim - true] AuthorityChecker - hasManagerAuthority");
                return true;
            }
        }
        log.info("[fim - false] AuthorityChecker - hasManagerAuthority");
        return false;
    }

    public static void requireMainAuthority(User user) {
        log.info("[inicia] AuthorityChecker - requireMainAuthority");
        if (!hasMainAuthority(user)) {
            log.info("[fim] AuthorityChecker - requireMainAuthority (no authority)");
            throw new UnauthorizedException("Usuário não possui autoridade MAIN.");
        }
        log.info("[fim] AuthorityChecker - requireMainAuthority (has authority)");
    }

    public static void requireManagerAuthority(User user) {
        log.info("[inicia] AuthorityChecker - requireManagerAuthority");
        if (!hasManagerAuthority(user)) {
            log.info("[fim] AuthorityChecker - requireManagerAuthority (no authority)");
            throw new UnauthorizedException("Usuário não possui autoridade MANAGER, consulte o administrador.");
        }
        log.info("[fim] AuthorityChecker - requireManagerAuthority (has authority)");
    }
}