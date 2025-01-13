package com.sisger.demo.section.application.service;

import com.sisger.demo.company.application.service.CompanyService;
import com.sisger.demo.company.domain.dto.ResponseCompanyChildDTO;
import com.sisger.demo.exception.BadRequestException;
import com.sisger.demo.section.domain.Section;
import com.sisger.demo.section.domain.dto.RequestDeleteSectionDTO;
import com.sisger.demo.section.domain.dto.RequestSectionDTO;
import com.sisger.demo.section.domain.dto.RequestUpdateSectionDTO;
import com.sisger.demo.section.domain.dto.ResponseSectionDTO;
import com.sisger.demo.section.infra.repository.SectionRepository;
import com.sisger.demo.user.domain.User;
import com.sisger.demo.util.AuthorityChecker;
import com.sisger.demo.util.PasswordHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Collections.sort;

@Service
@RequiredArgsConstructor
@Log4j2
public class SectionService implements SectionServiceInterface{

    private final SectionRepository sectionRepository;
    private final CompanyService companyService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<ResponseSectionDTO> findAllSections(User user) {
        log.info("[inicia] SectionService - findAllSections");
        AuthorityChecker.requireManagerAuthority(user);
        var company = user.getCompany();
        var responseCompany = ResponseCompanyChildDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .build();

        List<ResponseSectionDTO> sectionList = new ArrayList<>(sectionRepository.findAllByCompany(company).stream().map(
                section -> ResponseSectionDTO.builder()
                        .id(section.getId())
                        .company(responseCompany)
                        .name(section.getName())
                        .build()
                ).toList());

        sectionList.sort(Comparator.comparing(
                responseSectionDTO -> responseSectionDTO.getName().toLowerCase()));
        log.info("[fim] SectionService - findAllSections");
        return sectionList;
    }

    @Override
    public Section create(RequestSectionDTO requestSectionDTO, User manager) {

        log.info("[inicia] SectionService - create");

        PasswordHandler.validatePassword(
                requestSectionDTO.getPasswordAuthorization(), manager.getPassword(),passwordEncoder);

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
    public Section update(RequestUpdateSectionDTO requestUpdateSectionDTO, User manager) {
            log.info("[inicia] SectionService - update");
            PasswordHandler.validatePassword(
                    requestUpdateSectionDTO.getPasswordAuthorization(), manager.getPassword(), passwordEncoder);
            var sectionToUpdate = this.findById(requestUpdateSectionDTO.getId());

            if( sectionToUpdate == null) throw new BadRequestException("Section not found");

            Section updatedSection = Section.builder()
                    .name(requestUpdateSectionDTO.getName())
                    .id(sectionToUpdate.getId())
                    .company(sectionToUpdate.getCompany())
                    .employees(sectionToUpdate.getEmployees())
                    .build();

            log.info("[fim] SectionService - update");
            return sectionRepository.save(updatedSection);

    }

    @Override
    public Section findById(String id) {
        return sectionRepository.findById(id).orElse(null);
    }



}
