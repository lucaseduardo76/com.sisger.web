package com.sisger.demo.section.application.controller;


import com.sisger.demo.section.domain.Section;
import com.sisger.demo.section.domain.dto.RequestSectionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("section")
public interface SectionControllerInterface {

    @GetMapping(value = "/findAll")
    public ResponseEntity<List<Section>> findAllSectionsByCompany(
            @RequestHeader(name = "Authorization", required = true) String token);

    @PostMapping(value = "/create-section")
    public ResponseEntity<Section> createSection(
            @RequestHeader(name = "Authorization", required = true) String token, RequestSectionDTO requestSectionDTO);



}
