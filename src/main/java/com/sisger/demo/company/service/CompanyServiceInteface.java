package com.sisger.demo.company.service;

import com.sisger.demo.company.dto.RequestCompanyDTO;
import com.sisger.demo.company.dto.ResponseCompanyDTO;

public interface CompanyServiceInteface {

    ResponseCompanyDTO save(RequestCompanyDTO request);

}
