package com.sisger.demo.section.application.service;

import com.sisger.demo.company.domain.Company;
import com.sisger.demo.section.domain.Section;
import com.sisger.demo.section.domain.dto.RequestSectionDTO;
import com.sisger.demo.user.domain.User;
import com.sisger.demo.util.AuthorityChecker;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface SectionServiceInterface {
    List<Section> findAllSections(User user);
    Section create(RequestSectionDTO requestSectionDTO);
}
