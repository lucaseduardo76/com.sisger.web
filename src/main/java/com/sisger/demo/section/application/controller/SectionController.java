package com.sisger.demo.section.application.controller;

import com.sisger.demo.infra.security.TokenService;
import com.sisger.demo.section.application.service.SectionService;
import com.sisger.demo.section.domain.Section;
import com.sisger.demo.section.domain.dto.RequestSectionDTO;
import com.sisger.demo.section.domain.dto.RequestUpdateSectionDTO;
import com.sisger.demo.section.domain.dto.ResponseSectionDTO;
import com.sisger.demo.section.infra.repository.SectionRepository;
import com.sisger.demo.util.AuthorityChecker;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("section")
@AllArgsConstructor
@Log4j2
public class SectionController implements SectionControllerInterface {

    private final SectionService sectionService;
    private final TokenService tokenService;


    @Override
    public ResponseEntity<List<ResponseSectionDTO>> findAllSectionsByCompany(String token) {
        log.info("[inicia] SectionController - findAllSectionsByCompany");

        var user = tokenService.getUserByToken(token);
        var sectionList = sectionService.findAllSections(user);

        log.info("[fim] SectionController - findAllSectionsByCompany");

        return ResponseEntity.ok().body(sectionList);
    }

    @Override
    public ResponseEntity<Section> createSection(String token, RequestSectionDTO requestSectionDTO) {
        log.info("[inicia] SectionController - createSection");
        var user = tokenService.getUserByToken(token);
        AuthorityChecker.requireManagerAuthority(user);

        Section section = sectionService.create(requestSectionDTO, user);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(section.getId())
                .toUri();


        log.info("[fim] SectionController - createSection");
        return ResponseEntity.created(uri).body(section);

    }

    @Override
    public ResponseEntity<Section> updateSection(String token, RequestUpdateSectionDTO requestupdateSectionDTO) {
        log.info("[inicia] SectionController - updateSection");

        var user = tokenService.getUserByToken(token);
        AuthorityChecker.requireManagerAuthority(user);
        log.info("[fim] SectionController - updateSection");
        return ResponseEntity.ok().body(sectionService.update(requestupdateSectionDTO, user));


    }
}
