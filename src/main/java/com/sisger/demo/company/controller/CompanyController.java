package com.sisger.demo.company.controller;


import com.sisger.demo.company.domain.Company;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("company")
public class CompanyController {

    public ResponseEntity<Company> findAll(){

    }


}
