package com.sisger.demo.user.service;

import com.sisger.demo.authorization.domain.AuthenticationDTO;
import com.sisger.demo.company.service.CompanyService;
import com.sisger.demo.infra.security.TokenService;
import com.sisger.demo.user.domain.RegisterDTO;
import com.sisger.demo.user.domain.User;
import com.sisger.demo.user.repository.UserRepository;
import com.sisger.demo.util.AuthorityChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final CompanyService companyService;

    public User create(RegisterDTO data){
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
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

            System.out.println("Aqui cheguei");
            AuthorityChecker.requireMainAuthority(newUser);

        }

         return userRepository.save(newUser);
    }

    public String login(AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        return tokenService.generateToken((User) auth.getPrincipal());

    }


}
