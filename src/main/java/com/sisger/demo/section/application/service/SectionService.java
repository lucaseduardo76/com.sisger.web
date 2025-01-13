package com.sisger.demo.section.application.service;

import com.sisger.demo.company.application.service.CompanyService;
import com.sisger.demo.section.domain.Section;
import com.sisger.demo.section.domain.dto.RequestDeleteSectionDTO;
import com.sisger.demo.section.domain.dto.RequestSectionDTO;
import com.sisger.demo.section.domain.dto.RequestUpdateSectionDTO;
import com.sisger.demo.section.infra.repository.SectionRepository;
import com.sisger.demo.user.domain.User;
import com.sisger.demo.util.AuthorityChecker;
import com.sisger.demo.util.PasswordHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class SectionService implements SectionServiceInterface{

    private final SectionRepository sectionRepository;
    private final CompanyService companyService;
    private final PasswordEncoder passwordEncoder;

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
    public Section create(RequestSectionDTO requestSectionDTO, User manager) {

        log.info("[inicia] SectionService - create");

        validatePassword(requestSectionDTO.getPasswordAuthorization(), manager.getPassword());

        Section section = Section.builder()
                .name(requestSectionDTO.getName())
                .company(companyService.findById(requestSectionDTO.getCompanyId()))
                .build();
        log.info("[fim] SectionService - create");
        return sectionRepository.save(section);
    }

    @Override
    public void delete(RequestDeleteSectionDTO requestDeleteSectionDTO, User manager) {

    }

    @Override
    public void update(RequestUpdateSectionDTO requestUpdateSectionDTO, User manager) {

    }

    @Override
    public Section findById(String id) {
        return null;
    }

    private void validatePassword(String requestPassword, String managerPassword){
        PasswordHandler.validatePassword(requestPassword, managerPassword, passwordEncoder);
    }

}
