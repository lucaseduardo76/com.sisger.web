package com.sisger.demo.company.infra.repository;

import com.sisger.demo.company.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {
}
