package com.sisger.demo.company.application.service;

import com.sisger.demo.company.domain.Company;
import com.sisger.demo.company.domain.dto.RequestCompanyDTO;
import com.sisger.demo.company.domain.dto.ResponseCompanyChildDTO;
import com.sisger.demo.company.domain.dto.ResponseCompanyDTO;
import com.sisger.demo.user.domain.User;

public interface CompanyServiceInteface {
    Company findById(String id);
    ResponseCompanyChildDTO save(RequestCompanyDTO request, User user);

}
