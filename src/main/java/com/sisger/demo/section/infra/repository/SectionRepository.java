package com.sisger.demo.section.infra.repository;

import com.sisger.demo.company.domain.Company;
import com.sisger.demo.section.domain.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, String> {

    List<Section> findAllByCompany(Company company);
}
