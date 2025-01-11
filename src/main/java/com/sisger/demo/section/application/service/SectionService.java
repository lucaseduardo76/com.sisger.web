package com.sisger.demo.section.application.service;

import com.sisger.demo.company.application.service.CompanyService;
import com.sisger.demo.company.domain.Company;
import com.sisger.demo.section.domain.Section;
import com.sisger.demo.section.domain.dto.RequestSectionDTO;
import com.sisger.demo.section.infra.repository.SectionRepository;
import com.sisger.demo.user.domain.User;
import com.sisger.demo.user.application.service.UserService;
import com.sisger.demo.util.AuthorityChecker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class SectionService implements SectionServiceInterface{

    private final SectionRepository sectionRepository;
    private final UserService userService;
    private final CompanyService companyService;

    @Override
    public List<Section> findAllSections(User user) {
        log.info("[inicia] SectionService - findAllSections");
        AuthorityChecker.requireManagerAuthority(user);
        var company = user.getCompany();
        log.info("[fim] SectionService - findAllSections");
        return Optional.ofNullable(sectionRepository.findAllByCompany(company))
                .orElse(Collections.emptyList());
    }

    @Override
    public Section create(RequestSectionDTO requestSectionDTO) {

        log.info("[inicia] SectionService - create");
        Section section = Section.builder()
                .company(companyService.findById(requestSectionDTO.getCompanyId()))
                .build();
        log.info("[fim] SectionService - create");
        return sectionRepository.save(section);
    }
}
