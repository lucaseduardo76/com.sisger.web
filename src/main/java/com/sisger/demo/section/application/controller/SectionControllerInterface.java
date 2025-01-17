package com.sisger.demo.section.application.controller;


import com.sisger.demo.section.domain.Section;
import com.sisger.demo.section.domain.dto.RequestDeleteSectionDTO;
import com.sisger.demo.section.domain.dto.RequestSectionDTO;
import com.sisger.demo.section.domain.dto.RequestUpdateSectionDTO;
import com.sisger.demo.section.domain.dto.ResponseSectionDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("section")
public interface SectionControllerInterface {

    @GetMapping(value = "/find-all")
    ResponseEntity<List<ResponseSectionDTO>> findAllSectionsByCompany(
            @RequestHeader(name = "Authorization", required = true) String token);

    @PostMapping(value = "/create")
    ResponseEntity<Section> createSection(
            @RequestHeader(name = "Authorization", required = true) String token,
            @RequestBody @Valid RequestSectionDTO requestSectionDTO);

    @PutMapping(value = "/update")
    ResponseEntity<Section> updateSection(@RequestHeader(name = "Authorization", required = true) String token,
                                                 @RequestBody @Valid RequestUpdateSectionDTO requestupdateSectionDTO);

    @DeleteMapping(value = "/delete")
    ResponseEntity<HttpStatus> deleteSection(@RequestHeader(name = "Authorization", required = true) String token,
                                                    @RequestBody RequestDeleteSectionDTO requestDeleteSectionDTO);
}
