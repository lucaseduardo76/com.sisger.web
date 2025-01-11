package com.sisger.demo.user.application.service;

import com.sisger.demo.authorization.domain.AuthenticationDTO;
import com.sisger.demo.company.application.service.CompanyService;
import com.sisger.demo.company.domain.Company;
import com.sisger.demo.exception.BadRequestException;
import com.sisger.demo.exception.CpfAlreadyExistsException;
import com.sisger.demo.exception.EmailAlreadyExistsException;
import com.sisger.demo.exception.UnauthorizedException;
import com.sisger.demo.infra.security.TokenService;
import com.sisger.demo.user.domain.Role;
import com.sisger.demo.user.domain.dto.RegisterDTO;
import com.sisger.demo.user.domain.User;
import com.sisger.demo.user.domain.dto.RequestUserDTO;
import com.sisger.demo.user.infra.repository.UserRepository;
import com.sisger.demo.util.AuthorityChecker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService implements UserServiceInterface {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final CompanyService companyService;
    private final PasswordEncoder passwordEncoder;

    public User create(RegisterDTO data){
        log.info("[inicia]  UserService - create");
        validateEmail(data.getEmail());
        String encryptedPassword = passwordEncoder.encode(data.getPassword());

        User newUser = User.builder()
                .email(data.getEmail())
                .name(data.getName())
                .role(Role.MAIN)
                .password(encryptedPassword)
                .build();

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

    @Override
    public List<User> findAllByCompany(Company company) {
        log.info("[inicia]  UserService - findAllByCompany");
        if(company == null) throw new BadRequestException("Company is null");

        List<User> usersByCompany = userRepository.findAllByCompany(company);
        usersByCompany.removeIf(user -> Role.MAIN.equals(user.getRole()));
        usersByCompany.sort(Comparator.comparing(user -> user.getName().toLowerCase()));

        for(User user : usersByCompany){
            System.out.println(user.getCompany().getRazaoSocial());
        }

        log.info("[fim]  UserService - findAllByCompany");

        return usersByCompany;
    }

    @Override
    public User createRegularUser(RequestUserDTO requestUserDTO) {
        log.info("[inicia]  UserService - createRegularUser");

        validateEmail(requestUserDTO.getEmail());
        validateCpf(requestUserDTO.getCpf());

        User user = User.builder()
                .email(requestUserDTO.getEmail())
                .name(requestUserDTO.getName())
                .password(passwordEncoder.encode(requestUserDTO.getPassword()))
                .cpf(requestUserDTO.getCpf())
                .role(requestUserDTO.getRole())
                .build();
        log.info("[fim]  UserService - createRegularUser");

        return userRepository.save(user);
    }


    private boolean validatePassword(String passwordReq, String passwordUser){
        return !passwordEncoder.matches(passwordReq, passwordUser);
    }

    private void validateEmail(String email){
        if(this.userRepository.findByEmail(email) != null)
            throw new EmailAlreadyExistsException("Email "+ email +" já existe na base de dados");
    }

    private void validateCpf(String cpf){
        if(this.userRepository.findByCpf(cpf) != null)
            throw new CpfAlreadyExistsException("Cpf "+ cpf +" já existe na base de dados");
    }

}
