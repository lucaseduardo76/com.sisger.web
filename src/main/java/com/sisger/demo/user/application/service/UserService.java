package com.sisger.demo.user.application.service;

import com.sisger.demo.authorization.domain.AuthenticationDTO;
import com.sisger.demo.company.application.service.CompanyService;
import com.sisger.demo.company.domain.Company;
import com.sisger.demo.company.domain.dto.ResponseCompanyChildDTO;
import com.sisger.demo.company.domain.dto.ResponseCompanyDTO;
import com.sisger.demo.exception.*;
import com.sisger.demo.infra.security.TokenService;
import com.sisger.demo.task.application.service.TaskService;
import com.sisger.demo.user.domain.Role;
import com.sisger.demo.user.domain.dto.*;
import com.sisger.demo.user.domain.User;
import com.sisger.demo.user.infra.repository.UserRepository;
import com.sisger.demo.util.PasswordHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private final TaskService taskService;

    @Transactional
    public ResponseUserDTO createMainAccount(RegisterDTO data){
        log.info("[inicia]  UserService - create");
        validateEmail(data.getEmail());
        String encryptedPassword = passwordEncoder.encode(data.getPassword());

        User newUser =  userRepository.save(User.builder()
                .email(data.getEmail())
                .name(data.getName())
                .role(Role.MAIN)
                .password(encryptedPassword)
                .build());



        var companyResponse = companyService.save(data.getCompany(), newUser);
        this.setCompanyToMain(newUser, companyResponse.getId());
        log.info("[fim]  UserService - create");

         return buildUserResponse(newUser);
    }

    public String login(AuthenticationDTO data){
        log.info("[inicia]  UserService - login");
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        log.info("[fim]  UserService - login");
        return tokenService.generateToken((User) auth.getPrincipal());

    }

    public User findById(String id){
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User not found, verify the id")
        );
    }

    public void setCompanyToMain(User user, String companyId){
        log.info("[inicia]  UserService - setCompanyToMain");
        user.setCompany(companyService.findById(companyId));
        userRepository.save(user);
        log.info("[fim]  UserService - setCompanyToMain");
    }

    public void changePassword(User user, String oldPassword, String newPassword){
        log.info("[inicia]  UserService - changePassword");
        validatePassword(oldPassword, user.getPassword());
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        log.info("[fim]  UserService - changePassword");
    }

    @Override
    public List<ResponseUserDTO> findAllByCompany(Company company) {
        log.info("[inicia]  UserService - findAllByCompany");
        if(company == null) throw new BadRequestException("Company is null");

        List<ResponseUserDTO> usersByCompany = new ArrayList<>(userRepository.findAllByCompany(company).stream()
                .map(this::buildUserResponse)
                .toList());

        usersByCompany.removeIf(user -> Role.MAIN.equals(user.getRole()));
        usersByCompany.sort(Comparator.comparing(user -> user.getName().toLowerCase()));

        log.info("[fim]  UserService - findAllByCompany");

        return usersByCompany;
    }

    @Override
    public ResponseUserDTO createRegularUser(RequestUserDTO requestUserDTO, User manager) {
        log.info("[inicia]  UserService - createRegularUser");

        validateEmail(requestUserDTO.getEmail());
        validateCpf(requestUserDTO.getCpf());

        User newUser = User.builder()
                .email(requestUserDTO.getEmail())
                .name(requestUserDTO.getName())
                .password(passwordEncoder.encode(requestUserDTO.getPassword()))
                .cpf(requestUserDTO.getCpf())
                .role(requestUserDTO.getRole())
                .company(manager.getCompany())
                .build();


        log.info("[fim]  UserService - createRegularUser");

        return buildUserResponse(userRepository.save(newUser));
    }

    @Override
    public void update(RequestUpdateUserDTO requestUpdateUser, User manager) {
        log.info("[inicia]  UserService - update");

        if(requestUpdateUser.getId() == null)
            throw new BadRequestException("Id is null");

        User userToUpdate = userRepository.findById(requestUpdateUser.getId()).orElseThrow(
                () -> new BadRequestException("User not found, verify the id"));

        validateCompany(manager, userToUpdate);

        validatePassword(requestUpdateUser.getPasswordAuthorization(), manager.getPassword());

        if(!requestUpdateUser.getEmail().equals(userToUpdate.getEmail()))
            validateEmail(requestUpdateUser.getEmail());

        if(!requestUpdateUser.getCpf().equals(userToUpdate.getCpf()))
            validateCpf(requestUpdateUser.getCpf());

        if(requestUpdateUser.getRole().equals(Role.MAIN))
            throw new UnauthorizedException("You can't turn this user to MAIN, check the documentation");

        var userUpdated = User.builder()
                .email(requestUpdateUser.getEmail())
                .cpf(requestUpdateUser.getCpf())
                .role(requestUpdateUser.getRole())
                .name(requestUpdateUser.getName())
                .company(userToUpdate.getCompany())
                .id(userToUpdate.getId())
                .password(userToUpdate.getPassword())
                .build();

        userRepository.save(userUpdated);
        log.info("[fim]  UserService - update");
    }

    @Override
    public void delete(RequestDeleteUserDTO requestDeleteUserDTO, User manager) {
        log.info("[inicia]  UserService - delete");
        var userToDelete = userRepository.findById(requestDeleteUserDTO.getId()).orElseThrow(
                () -> new BadRequestException("User not found, verify the id"));

        validateCompany(manager, userToDelete);

        validatePassword(requestDeleteUserDTO.getPasswordAuthorization(), manager.getPassword());
        userRepository.deleteById(requestDeleteUserDTO.getId());
        log.info("[fim]  UserService - delete");
    }

    @Override
    public ResponseUserDTO buildUserResponse(User user) {
        log.info("[inicia]  UserService - buildUserRequest");
        ResponseUserDTO usersByCompany =ResponseUserDTO.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .cpf(user.getCpf())
                        .role(user.getRole())
                        .company(buildResponseCompany(companyService.findByIdToRequest(user.getCompany().getId())))
                        .task(taskService.findAllTasksByUser(user.getId()))
                        .build();
        log.info("[fim]  UserService - buildUserRequest");

        return usersByCompany;
    }

    private void validatePassword(String requestPassword, String managerPassword){
        PasswordHandler.validatePassword(requestPassword, managerPassword, passwordEncoder);
    }

    private void validateCompany(User manager, User employee){
        if(!manager.getCompany().equals(employee.getCompany()))
            throw new UnauthorizedException("This user is from a different company");
    }

    private void validateEmail(String email){
        log.info("[inicia]  UserService - validateEmail");
        if(this.userRepository.findByEmail(email) != null)
            throw new EmailAlreadyExistsException("Email "+ email +" já existe na base de dados");
        log.info("[fim]  UserService - validateEmail");
    }

    private void validateCpf(String cpf){
        log.info("[inicia]  UserService - validateCpf");
        if(this.userRepository.findByCpf(cpf) != null)
            throw new CpfAlreadyExistsException("Cpf "+ cpf +" já existe na base de dados");
        log.info("[fim]  UserService - validateCpf");
    }

    private ResponseCompanyChildDTO buildResponseCompany(ResponseCompanyDTO companyResponse){
        return ResponseCompanyChildDTO.builder()
                .id(companyResponse.getId())
                .name(companyResponse.getName())
                .build();
    }

}
