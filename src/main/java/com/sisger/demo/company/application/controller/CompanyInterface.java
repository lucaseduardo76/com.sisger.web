package com.sisger.demo.company.application.controller;

import com.sisger.demo.company.domain.Company;
import com.sisger.demo.company.domain.dto.RequestCompanyDTO;
import com.sisger.demo.company.domain.dto.ResponseCompanyDTO;
import com.sisger.demo.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("company")
public interface CompanyInterface {

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCompanyDTO> findById(
            @RequestHeader(name = "Authorization", required = true) String token, @PathVariable String id);
}
