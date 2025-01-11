package com.sisger.demo.company.application.controller;


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
    private final UserService userService;

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
            String token, RequestCompanyDTO requestCompanyDTO) {

        log.info("[inicia]  CompanyController - createCompany");

        var user = tokenService.getUserByToken(token);
        var message = verifyAuthentication(user);
        if(message != null)
            throw new UnauthorizedException(message);


        var companyResponse = companyService.save(requestCompanyDTO, user);
        userService.setCompanyToMain(user, companyResponse.getId());

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(companyResponse.getId())
                .toUri();

        log.info("[Fim]  CompanyController - createCompany  ");
        return ResponseEntity.created(uri).body(companyResponse);

    }
    private String verifyAuthentication(User user) {
        log.info("[inicia]  CompanyController - verifyAuthentication");
        if (user == null)
            return "Usuario não encontrado";
        else if(!AuthorityChecker.hasMainAuthority(user))
            return "Usuario não tem Autorização necessária";

        log.info("[Fim]  CompanyController - verifyAuthentication");
        return null;
    }


}


