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
    @NotEmpty(message = "companyId cannot be Empty")
    private String companyId;
    @NotEmpty(message = "name cannot be Empty")
    private String name;
    @NotEmpty(message = "passwordAuthorization cannot be Empty")
    private String passwordAuthorization;

}
