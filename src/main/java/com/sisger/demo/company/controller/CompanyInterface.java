package com.sisger.demo.company.controller;

import com.sisger.demo.company.domain.Company;
import com.sisger.demo.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("company")
public interface CompanyInterface {

    @GetMapping
    public ResponseEntity<User> findAllEmployeeByCompany(
            @RequestHeader(name = "Authorization", required = true) String token, String id);
    @GetMapping
    public ResponseEntity<User> findAllEmployeeBySection(
            @RequestHeader(name = "Authorization", required = true) String token, String id);

    @PostMapping
    public ResponseEntity<Company> createCompany(
            @RequestHeader(name = "Authorization", required = true) String token, String id);

}
