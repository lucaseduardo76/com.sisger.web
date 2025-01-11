package com.sisger.demo.section.domain.dto;


import com.sisger.demo.company.domain.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestSectionDTO {
    private String companyId;
}
