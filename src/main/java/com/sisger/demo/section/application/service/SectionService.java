package com.sisger.demo.section.application.service;

import com.sisger.demo.company.application.service.CompanyService;
import com.sisger.demo.company.domain.dto.ResponseCompanyChildDTO;
import com.sisger.demo.exception.BadRequestException;
import com.sisger.demo.exception.UnauthorizedException;
import com.sisger.demo.section.domain.Section;
import com.sisger.demo.section.domain.dto.RequestDeleteSectionDTO;
import com.sisger.demo.section.domain.dto.RequestSectionDTO;
import com.sisger.demo.section.domain.dto.RequestUpdateSectionDTO;
import com.sisger.demo.section.domain.dto.ResponseSectionDTO;
import com.sisger.demo.section.infra.repository.SectionRepository;
import com.sisger.demo.task.application.service.TaskService;
import com.sisger.demo.task.infra.repository.TaskRepository;
import com.sisger.demo.user.domain.User;
import com.sisger.demo.util.AuthorityChecker;
import com.sisger.demo.util.PasswordHandler;
import com.sisger.demo.util.TextHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.Collections.sort;

@Service
@RequiredArgsConstructor
@Log4j2
public class SectionService implements SectionServiceInterface{

    private final SectionRepository sectionRepository;
    private final CompanyService companyService;
    private final PasswordEncoder passwordEncoder;
    private final TaskRepository taskRepository;

    @Override
    public List<ResponseSectionDTO> findAllSections(User user) {
        log.info("[inicia] SectionService - findAllSections");
        AuthorityChecker.requireManagerAuthority(user);
        var companyResponse = companyService.buildResponseCompanyChildDTOFromCompany(user.getCompany());

        List<ResponseSectionDTO> sectionList = new ArrayList<>(
                sectionRepository.findAllByCompany(user.getCompany()).stream().map(
                section -> ResponseSectionDTO.builder()
                        .id(section.getId())
                        .company(companyResponse)
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
                .tasks(new ArrayList<>())
                .build();
        log.info("[fim] SectionService - create");
        return sectionRepository.save(section);
    }

    @Override
    @Transactional
    public void delete(RequestDeleteSectionDTO requestDeleteSectionDTO, User manager) {
        log.info("[inicia] SectionService - delete");

        PasswordHandler.validatePassword(
                requestDeleteSectionDTO.getPasswordAuthorization(), manager.getPassword(), passwordEncoder);

        if(!findById(requestDeleteSectionDTO.getId()).getCompany().getId().equals(manager.getCompany().getId()))
            throw new UnauthorizedException("That Section does not belong to your company");

        taskRepository.deleteAllBySectionId(requestDeleteSectionDTO.getId());

        sectionRepository.deleteById(requestDeleteSectionDTO.getId());
        log.info("[fim] SectionService - delete");
    }

    @Override
    public Section update(RequestUpdateSectionDTO requestUpdateSectionDTO, User manager) {
            log.info("[inicia] SectionService - update");
            PasswordHandler.validatePassword(
                    requestUpdateSectionDTO.getPasswordAuthorization(), manager.getPassword(), passwordEncoder);
            var sectionToUpdate = this.findById(requestUpdateSectionDTO.getId());

            if(!sectionToUpdate.getCompany().getId().equals(manager.getCompany().getId()))
                throw new UnauthorizedException("That Section does not belong to your company");

            sectionToUpdate.setName(requestUpdateSectionDTO.getName());

            log.info("[fim] SectionService - update");
            return sectionRepository.save(sectionToUpdate);

    }



    @Override
    public Section findById(String id) {
        return sectionRepository.findById(id).orElse(null);
    }



}
