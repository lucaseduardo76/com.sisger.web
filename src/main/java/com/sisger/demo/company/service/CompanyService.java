package com.sisger.demo.company.service;

import com.sisger.demo.company.domain.Company;
import com.sisger.demo.company.dto.RequestCompanyDTO;
import com.sisger.demo.company.dto.ResponseCompanyDTO;
import com.sisger.demo.company.repository.CompanyRepository;
import lombok.Builder;
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

    public ResponseCompanyDTO save(RequestCompanyDTO requestcompanyDTO) {
        Company company = Company.builder()
                .razaoSocial(requestcompanyDTO.getRazaoSocial())
                .cnpj(requestcompanyDTO.getCnpj())
                .cpf(requestcompanyDTO.getCpf())
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
