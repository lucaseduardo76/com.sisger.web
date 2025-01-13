package com.sisger.demo.section.application.controller;


import com.sisger.demo.section.domain.Section;
import com.sisger.demo.section.domain.dto.RequestDeleteSectionDTO;
import com.sisger.demo.section.domain.dto.RequestSectionDTO;
import com.sisger.demo.section.domain.dto.RequestUpdateSectionDTO;
import com.sisger.demo.section.domain.dto.ResponseSectionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("section")
public interface SectionControllerInterface {

    @GetMapping(value = "/find-all")
    public ResponseEntity<List<ResponseSectionDTO>> findAllSectionsByCompany(
            @RequestHeader(name = "Authorization", required = true) String token);

    @PostMapping(value = "/create")
    public ResponseEntity<Section> createSection(
            @RequestHeader(name = "Authorization", required = true) String token,
            @RequestBody RequestSectionDTO requestSectionDTO);

    @PutMapping(value = "/update")
    public ResponseEntity<Section> updateSection(@RequestHeader(name = "Authorization", required = true) String token,
                                                 @RequestBody  RequestUpdateSectionDTO requestupdateSectionDTO);

    @DeleteMapping(value = "/delete")
    public ResponseEntity<HttpStatus> deleteSection(@RequestHeader(name = "Authorization", required = true) String token,
                                                    @RequestBody RequestDeleteSectionDTO requestDeleteSectionDTO);
}
