package com.sisger.demo.company.application.controller;


import com.sisger.demo.company.domain.Company;
import com.sisger.demo.company.domain.dto.RequestCompanyDTO;
import com.sisger.demo.company.domain.dto.ResponseCompanyDTO;
import com.sisger.demo.company.application.service.CompanyService;
import com.sisger.demo.exception.UnauthorizedException;
import com.sisger.demo.infra.security.TokenService;
import com.sisger.demo.user.domain.User;
import com.sisger.demo.user.application.service.UserService;
import com.sisger.demo.util.AuthorityChecker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("company")
@RequiredArgsConstructor
@Log4j2
public class CompanyController implements CompanyInterface{

    private final CompanyService companyService;
    private final TokenService tokenService;

    @Override
    public ResponseEntity<ResponseCompanyDTO> findById(String token, String id) {
        log.info("[inicia] CompanyController - findById");
        var user = tokenService.getUserByToken(token);

        var company = companyService.findByIdToRequest(id);

        log.info("[fim] CompanyController - findById");

        return ResponseEntity.ok().body(company);
    }
}


