package com.sisger.demo.company.service;

import com.sisger.demo.company.dto.RequestCompanyDTO;
import com.sisger.demo.company.dto.ResponseCompanyDTO;
import com.sisger.demo.user.domain.User;

public interface CompanyServiceInteface {

    ResponseCompanyDTO save(RequestCompanyDTO request, User user);

}
