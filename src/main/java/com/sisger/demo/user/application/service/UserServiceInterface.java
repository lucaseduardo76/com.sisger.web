package com.sisger.demo.user.application.service;

import com.sisger.demo.authorization.domain.AuthenticationDTO;
import com.sisger.demo.company.domain.Company;
import com.sisger.demo.exception.UnauthorizedException;
import com.sisger.demo.user.domain.User;
import com.sisger.demo.user.domain.dto.RegisterDTO;
import com.sisger.demo.user.domain.dto.RequestUserDTO;
import com.sisger.demo.util.AuthorityChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


public interface UserServiceInterface {
    public User create(RegisterDTO data);

    public String login(AuthenticationDTO data);

    public User findById(String id);

    public void setCompanyToMain(User user, String companyId);

    public void changePassword(User user, String oldPassword, String newPassword);

    public List<User> findAllByCompany(Company company);

    public User createRegularUser(RequestUserDTO requestUserDTO);
}
