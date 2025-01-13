package com.sisger.demo.section.domain.dto;


import com.sisger.demo.company.domain.Company;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestSectionDTO {
    @NotEmpty
    private String companyId;
    @NotEmpty
    private String name;
    @NotEmpty
    private String passwordAuthorization;

}
