package com.sisger.demo.user.application.service;

import com.sisger.demo.authorization.domain.AuthenticationDTO;
import com.sisger.demo.company.domain.Company;
import com.sisger.demo.user.domain.User;
import com.sisger.demo.user.domain.dto.*;

import java.util.List;


public interface UserServiceInterface {
    public ResponseUserDTO createMainAccount(RegisterDTO data);

    public String login(AuthenticationDTO data);

    public void setCompanyToMain(User user, String companyId);

    public void changePassword(User user, String oldPassword, String newPassword);

    public List<ResponseUserDTO> findAllByCompany(Company company);

    public ResponseUserDTO createRegularUser(RequestUserDTO requestUserDTO, User user);

    public void update(RequestUpdateUserDTO requestUpdateUser, User user);

    public void delete(RequestDeleteUserDTO requestDeleteUserDTO, User user);

    public ResponseUserDTO buildUserResponse(User user);
}
