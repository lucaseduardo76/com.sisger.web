package com.sisger.demo.company.controller;

import com.sisger.demo.company.domain.Company;
import com.sisger.demo.company.dto.RequestCompanyDTO;
import com.sisger.demo.company.dto.ResponseCompanyDTO;
import com.sisger.demo.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("company")
public interface CompanyInterface {

    @GetMapping(value = "/employee")
    public ResponseEntity<User> findAllEmployeeByCompany(
            @RequestHeader(name = "Authorization", required = true) String token, String id);
    @GetMapping(value = "/employee/section")
    public ResponseEntity<User> findAllEmployeeBySection(
            @RequestHeader(name = "Authorization", required = true) String token, String id);

    @PostMapping(value = "/create")
    public ResponseEntity<ResponseCompanyDTO> createCompany(
            @RequestHeader(name = "Authorization", required = true) String token, @RequestParam String idMainAcc,
            @RequestBody RequestCompanyDTO requestCompanyDTO);

}
