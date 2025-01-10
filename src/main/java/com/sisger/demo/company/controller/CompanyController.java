package com.sisger.demo.company.controller;


import com.sisger.demo.company.domain.Company;
import com.sisger.demo.company.dto.RequestCompanyDTO;
import com.sisger.demo.company.dto.ResponseCompanyDTO;
import com.sisger.demo.company.service.CompanyService;
import com.sisger.demo.exception.UnauthorizedException;
import com.sisger.demo.infra.security.TokenService;
import com.sisger.demo.user.domain.User;
import com.sisger.demo.user.service.UserService;
import com.sisger.demo.util.AuthorityChecker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
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
            String token, String idMainAcc, RequestCompanyDTO requestCompanyDTO) {

        log.info("[inicia]  CompanyController - createCompany");

        var user = tokenService.getUserByToken(token);
        var message = verifyAuthentication(user, idMainAcc);
        if(message != null)
            throw new UnauthorizedException(message);

        var companyResponse = companyService.save(requestCompanyDTO, user);


        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(companyResponse.getId())
                .toUri();

        log.info("[Fim]  CompanyController - createCompany  ");
        return ResponseEntity.created(uri).body(companyResponse);

    }
    private String verifyAuthentication(User user, String idMainAcc) {
        log.info("[inicia]  CompanyController - verifyAuthentication");
        if (user == null) {
            return "Usuario não encontrado";
        }
        else if(!user.getId().equals(idMainAcc)) {
            return "Id informado incorreto";
        }
        else if(!AuthorityChecker.hasMainAuthority(user)) {
            return "Usuario não tem Autorização necessária";
        }
        log.info("[Fim]  CompanyController - verifyAuthentication");
        return null;
    }


}


