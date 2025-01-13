package com.sisger.demo.company.application.service;

import com.sisger.demo.company.domain.Company;
import com.sisger.demo.company.domain.dto.RequestCompanyDTO;
import com.sisger.demo.company.domain.dto.ResponseCompanyDTO;
import com.sisger.demo.company.infra.repository.CompanyRepository;
import com.sisger.demo.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Log4j2
public class CompanyService implements CompanyServiceInteface{

    private final CompanyRepository companyRepository;

    public ResponseCompanyDTO findByIdToRequest(String id) {
        log.info("[inicia] CompanyService - findByIdToRequest");
        var company = companyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        log.info("[fim] CompanyService - findByIdToRequest");
        return ResponseCompanyDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .cnpj(company.getCnpj())
                .sections(company.getSection())
                .build();
    }

    public Company findById(String id) {
        return companyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public ResponseCompanyDTO save(RequestCompanyDTO requestcompanyDTO, User user) {
        log.info("[inicia] CompanyService - save");

        Company company = Company.builder()
                .name(requestcompanyDTO.getRazaoSocial())
                .cnpj(requestcompanyDTO.getCnpj())
                .mainAccount(user)
                .build();

        var companyResult = companyRepository.save(company);
        log.info("[fim] CompanyService - save");
        return ResponseCompanyDTO.builder()
                .id(companyResult.getId())
                .name(companyResult.getName())
                .cnpj(companyResult.getCnpj())
                .build();
    }

}
