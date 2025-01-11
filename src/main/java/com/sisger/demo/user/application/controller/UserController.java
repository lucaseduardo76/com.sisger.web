package com.sisger.demo.user.application.controller;

import com.sisger.demo.exception.BadRequestException;
import com.sisger.demo.exception.EmailAlreadyExistsException;
import com.sisger.demo.exception.InternalServerErrorException;
import com.sisger.demo.exception.UnauthorizedException;
import com.sisger.demo.infra.security.TokenService;
import com.sisger.demo.user.domain.Role;
import com.sisger.demo.user.domain.User;
import com.sisger.demo.user.domain.dto.ChangePasswordDTO;
import com.sisger.demo.user.application.service.UserService;
import com.sisger.demo.user.domain.dto.RequestUserDTO;
import com.sisger.demo.util.AuthorityChecker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Log4j2
public class UserController implements UserControllerInterface{

    private final TokenService tokenService;
    private final UserService userService;

    @Override
    public void changePassword(String token, ChangePasswordDTO changePasswordDTO) {
        log.info("[inicia]  UserController - changePassword");

        userService.changePassword(validateToken(token),
                changePasswordDTO.getOldPassword(),
                changePasswordDTO.getNewPassword());

        log.info("[fim]  UserController - changePassword");

    }

    @Override
    public ResponseEntity<List<User>> findAll(String token) {
        log.info("[inicia]  UserController - findAll");

        var user = validateToken(token);
        AuthorityChecker.requireManagerAuthority(user);
        List<User> userList = userService.findAllByCompany(user.getCompany());

        log.info("[fim]  UserController - findAll");

        return ResponseEntity.ok().body(userList);
    }

    @Override
    public ResponseEntity<User> create(String token, RequestUserDTO requestUserDTO) {
        log.info("[inicia]  UserController - create");
        var user = validateToken(token);

        AuthorityChecker.requireManagerAuthority(user);

        if(requestUserDTO.getRole().equals(Role.MAIN))
            throw new BadRequestException(
                    "Autoridade não pode ser MAIN, por favor refaça a solicitação com role MANAGER ou menor");

        var newEmployee = userService.createRegularUser(requestUserDTO);
        if(user.getCompany() != null)
            newEmployee.setCompany(user.getCompany());
        else
            throw new InternalServerErrorException("Erro interno, por favor contate o suporte");

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newEmployee.getId())
                .toUri();
        log.info("[fim]  UserController - create");

        return ResponseEntity.created(location).body(newEmployee);

    }

    private User validateToken(String token) {
        log.info("[inicia]  UserController - validateToken");
        var user = tokenService.getUserByToken(token);
        if(user == null) throw new UnauthorizedException("Invalid token");
        log.info("[fim]  UserController - validateToken");
        return user;
    }

}
