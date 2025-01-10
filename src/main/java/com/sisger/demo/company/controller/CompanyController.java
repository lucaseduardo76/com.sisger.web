package com.sisger.demo.company.controller;


import com.sisger.demo.company.domain.Company;
import com.sisger.demo.company.dto.RequestCompanyDTO;
import com.sisger.demo.company.dto.ResponseCompanyDTO;
import com.sisger.demo.company.service.CompanyService;
import com.sisger.demo.infra.security.TokenService;
import com.sisger.demo.user.domain.User;
import com.sisger.demo.user.service.UserService;
import com.sisger.demo.util.AuthorityChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("company")
@RequiredArgsConstructor
public class CompanyController implements CompanyInterface{

    private final CompanyService companyService;
    private final TokenService tokenService;

    @Override
    public ResponseEntity<User> findAllEmployeeByCompany(String token, String id) {
        return null;
    }

    @Override
    public ResponseEntity<User> findAllEmployeeBySection(String token, String id) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseCompanyDTO> createCompany(
            String token, RequestCompanyDTO requestCompanyDTO, String idMainAcc) {

        var user = validateToken(token);

        if (user == null)
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        else
            if(user.getId().equals(idMainAcc)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        else
            if(!AuthorityChecker.hasMainAuthority(user))
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        var companyResponse = companyService.save(requestCompanyDTO);

            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(companyResponse.getId())
                    .toUri();

        return ResponseEntity.created(uri).body(companyResponse);

    }

    private User validateToken(String token) {
        return tokenService.getUserByToken(token);
    }


}


