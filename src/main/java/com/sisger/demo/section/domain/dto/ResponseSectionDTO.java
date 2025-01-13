package com.sisger.demo.section.domain.dto;


import com.sisger.demo.company.domain.dto.ResponseCompanyChildDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseSectionDTO {
    private String id;
    private String name;
    private ResponseCompanyChildDTO company;
}
