package com.sisger.demo.company.service;

import com.sisger.demo.company.domain.Company;
import com.sisger.demo.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;


    public Company findById(String id) {
        return companyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
