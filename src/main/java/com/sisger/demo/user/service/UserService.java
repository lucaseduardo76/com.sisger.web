package com.sisger.demo.user.service;

import com.sisger.demo.authorization.domain.AuthenticationDTO;
import com.sisger.demo.company.service.CompanyService;
import com.sisger.demo.exception.UnauthorizedException;
import com.sisger.demo.infra.security.TokenService;
import com.sisger.demo.user.domain.dto.RegisterDTO;
import com.sisger.demo.user.domain.User;
import com.sisger.demo.user.repository.UserRepository;
import com.sisger.demo.util.AuthorityChecker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final CompanyService companyService;
    private final PasswordEncoder passwordEncoder;

    public User create(RegisterDTO data){
        log.info("[inicia]  UserService - create");
        String encryptedPassword = passwordEncoder.encode(data.getPassword());
        User newUser;
        if(data.getCompanyId() != null) {
            newUser = User.builder()
                    .email(data.getEmail())
                    .role(data.getRole())
                    .cpf(data.getCpf())
                    .name(data.getName())
                    .company(companyService.findById(data.getCompanyId()))
                    .password(encryptedPassword)
                    .build();
        }else{
            newUser = User.builder()
                    .email(data.getEmail())
                    .role(data.getRole())
                    .cpf(data.getCpf())
                    .name(data.getName())
                    .password(encryptedPassword)
                    .build();


            AuthorityChecker.requireMainAuthority(newUser);

        }
        log.info("[fim]  UserService - create");
         return userRepository.save(newUser);
    }

    public String login(AuthenticationDTO data){
        log.info("[inicia]  UserService - login");
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        log.info("[fim]  UserService - login");
        return tokenService.generateToken((User) auth.getPrincipal());

    }

    public User findById(String id){
        return userRepository.findById(id).orElse(null);
    }

    public void setCompanyToMain(User user, String companyId){
        log.info("[inicia]  UserService - setCompanyToMain");
        user.setCompany(companyService.findById(companyId));
        userRepository.save(user);
        log.info("[fim]  UserService - setCompanyToMain");
    }

    public void changePassword(User user, String oldPassword, String newPassword){
        log.info("[inicia]  UserService - changePassword");
        if(validatePassword(oldPassword, user.getPassword())) {
            throw new UnauthorizedException("Invalid password");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        log.info("[fim]  UserService - changePassword");
    }


    private boolean validatePassword(String passwordReq, String passwordUser){
        return !passwordEncoder.matches(passwordReq, passwordUser);
    }

}
