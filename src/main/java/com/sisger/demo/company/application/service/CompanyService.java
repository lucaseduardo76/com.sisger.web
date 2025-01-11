package com.sisger.demo.company.application.service;

import com.sisger.demo.company.domain.Company;
import com.sisger.demo.company.domain.dto.RequestCompanyDTO;
import com.sisger.demo.company.domain.dto.ResponseCompanyDTO;
import com.sisger.demo.company.infra.repository.CompanyRepository;
import com.sisger.demo.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CompanyService implements CompanyServiceInteface{

    private final CompanyRepository companyRepository;

    public Company findById(String id) {
        return companyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public ResponseCompanyDTO save(RequestCompanyDTO requestcompanyDTO, User user) {


        Company company = Company.builder()
                .razaoSocial(requestcompanyDTO.getRazaoSocial())
                .cnpj(requestcompanyDTO.getCnpj())
                .mainAccount(user)
                .build();

        var result = companyRepository.save(company);

        return ResponseCompanyDTO.builder()
                .id(result.getId())
                .razaoSocial(result.getRazaoSocial())
                .cnpj(result.getCnpj())
                .cpf(result.getCpf())
                .build();
    }

}
