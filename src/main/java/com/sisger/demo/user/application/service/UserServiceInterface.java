package com.sisger.demo.user.application.service;

import com.sisger.demo.authorization.domain.AuthenticationDTO;
import com.sisger.demo.company.domain.Company;
import com.sisger.demo.user.domain.User;
import com.sisger.demo.user.domain.dto.RegisterDTO;
import com.sisger.demo.user.domain.dto.RequestDeleteUserDTO;
import com.sisger.demo.user.domain.dto.RequestUpdateUserDTO;
import com.sisger.demo.user.domain.dto.RequestUserDTO;

import java.util.List;


public interface UserServiceInterface {
    public User create(RegisterDTO data);

    public String login(AuthenticationDTO data);

    public User findById(String id);

    public void setCompanyToMain(User user, String companyId);

    public void changePassword(User user, String oldPassword, String newPassword);

    public List<User> findAllByCompany(Company company);

    public User createRegularUser(RequestUserDTO requestUserDTO, User user);

    public void update(RequestUpdateUserDTO requestUpdateUser, User user);

    public void delete(RequestDeleteUserDTO requestDeleteUserDTO, User user);
}
